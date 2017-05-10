package ru.mail.ales2003.deals2017.dao.db.impl.custom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.api.customdao.IContractCommonInfoDao;
import ru.mail.ales2003.deals2017.dao.api.customentities.ContractCommonInfo;
import ru.mail.ales2003.deals2017.dao.api.filters.IContractFilter;
import ru.mail.ales2003.deals2017.dao.db.filters.impl.ContractCommonInfoFilter;
import ru.mail.ales2003.deals2017.dao.db.impl.mapper.ContractCommonInfoMapper;
import ru.mail.ales2003.deals2017.datamodel.Contract;

@Repository
public class ContractCommonInfoDaoImpl implements IContractCommonInfoDao {

	private static Map<String, ArrayList<ContractCommonInfo>> CACHE_ITEM_COMMON_INFO = new HashMap<>();

	private static final Logger LOGGER = LoggerFactory.getLogger(ContractCommonInfoDaoImpl.class);

	@Inject
	private JdbcTemplate jdbcTemplate;

	private String contractClassName = Contract.class.getSimpleName();
	private String filterClassName = ContractCommonInfoFilter.class.getSimpleName();

	// =============READING AREA===============

	@Override
	public ContractCommonInfo getCommonInfo(Integer contractId) {
		if (contractId == null) {
			String errMsg = String.format("Error: as the itemVariantId was sent a null reference.");
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg);
		}

		final String READ_BY_ID_SQL = "select c.id as contract_id, c.created as creation_date,"
				+ " c.contract_status as status, c.pay_form as pay_form, c.pay_status as pay_status, c.total_price as total_amount,"
				+ " customer_group.name as customer_type, c.customer_id as customer_id,"
				+ " customer.last_name as customer_name, customer.company_name as customer_company,"
				+ " customer.manager_id, manager.last_name as manager_name from contract as c"
				+ " left join customer on c.customer_id=customer.id"
				+ " left join manager on customer.manager_id=manager.id"
				+ " left join customer_group on customer.customer_group_id=customer_group.id where c.id = ? ";

		//
		//
		LOGGER.info("[Checking the availability] of data on the request for commoninfo in the cache: {}.",
				"CACHE_ITEM_COMMON_INFO");
		// query to CASHE
		if (CACHE_ITEM_COMMON_INFO.containsKey(READ_BY_ID_SQL + contractId.toString())) {
			List<ContractCommonInfo> contractCommonInfosFromMapList = CACHE_ITEM_COMMON_INFO
					.get(READ_BY_ID_SQL + contractId.toString());
			ContractCommonInfo contractCommonInfoFromMap = contractCommonInfosFromMapList.get(0);
			LOGGER.info("The [DATA] in the cache {} [IS PRESENT] - the [QUERY] to the database is [NOT PERFOMED].",
					"CACHE_ITEM_COMMON_INFO");
			LOGGER.info("Size of cache {} = [{}] entities.", "CACHE_ITEM_COMMON_INFO", CACHE_ITEM_COMMON_INFO.size());
			return contractCommonInfoFromMap;
		}
		LOGGER.info("There is [NO DATA] in the cache {} - the query is [EXECUTED] in the database.",
				"CACHE_ITEM_COMMON_INFO");
		LOGGER.info("Size of cache {} = [{}] entities.", "CACHE_ITEM_COMMON_INFO", CACHE_ITEM_COMMON_INFO.size());
		//
		//
		try {

			ContractCommonInfo contractCommonInfo = jdbcTemplate.queryForObject(READ_BY_ID_SQL,
					new Object[] { contractId }, new ContractCommonInfoMapper());

			// save to CASHE (TO one map for 3 request types - wrapped
			// commonInfo in a list)

			// You need to add logic, what to do if you first ask for an ID that
			// IS NOT in the database and THEN ADDED. In the present case, the
			// cache is saved to a null on a specific id and it will return when
			// asked for this id.

			LOGGER.info("Saving data to the cache {}.", "CACHE_ITEM_COMMON_INFO");

			List<ContractCommonInfo> contractCommonInfolist = new ArrayList<>();
			contractCommonInfolist.add(0, contractCommonInfo);
			CACHE_ITEM_COMMON_INFO.put(READ_BY_ID_SQL + contractId.toString(),
					(ArrayList<ContractCommonInfo>) contractCommonInfolist);
			LOGGER.info("Size of cache {} = [{}] entities.", "CACHE_ITEM_COMMON_INFO", CACHE_ITEM_COMMON_INFO.size());
			//
			//

			LOGGER.info("Return data from the database.");
			return contractCommonInfo;
			//
			//

		} catch (EmptyResultDataAccessException e) {
			String errMsg = String.format(
					"You want to READ common information about the [%s] with id = [%s], but it doesn't exist in the storage.",
					contractClassName, contractId);
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg, e);
		}
	}

	@Override
	public List<ContractCommonInfo> getCommonInfoForAll() {
		final String READ_ALL_SQL = "select c.id as contract_id, c.created as creation_date,"
				+ " c.contract_status as status, c.pay_form as pay_form, c.pay_status as pay_status, c.total_price as total_amount,"
				+ " customer_group.name as customer_type, c.customer_id as customer_id,"
				+ " customer.last_name as customer_name, customer.company_name as customer_company,"
				+ " customer.manager_id, manager.last_name as manager_name from contract as c"
				+ " left join customer on c.customer_id=customer.id"
				+ " left join manager on customer.manager_id=manager.id"
				+ " left join customer_group on customer.customer_group_id=customer_group.id";

		// query to CASHE

		try {
			List<ContractCommonInfo> contractCommonInfos = jdbcTemplate.query(READ_ALL_SQL,
					new ContractCommonInfoMapper());
			LOGGER.debug("[{}] storage returns [{}] entitys with common info.", contractClassName,
					contractCommonInfos.size());

			// save to CASHE

			return contractCommonInfos;

		} catch (EmptyResultDataAccessException e) {
			String errMsg = String.format("Class [%s] storage returns incorrect entity count.", contractClassName);
			LOGGER.error("Error: {}", errMsg);
			throw e;
		}
	}

	@Override
	public List<ContractCommonInfo> getCommonInfoFiltered(IContractFilter filter) {
		IContractFilter givenFilter = new ContractCommonInfoFilter();
		givenFilter = filter;
		givenFilter.filterInitialize();
		LOGGER.debug("[{}] is initialized: [{}].", filterClassName, givenFilter.toString());
		final String SQL_WITH_FILTERING = givenFilter.getFullSqlQuery();

		// query to CASHE

		Object[] paramsArray = givenFilter.getQueryParamsArray();
		try {
			List<ContractCommonInfo> commonInfos = jdbcTemplate.query(SQL_WITH_FILTERING, paramsArray,
					new ContractCommonInfoMapper());

			// save to CASHE

			LOGGER.debug("[{}] storage returns [{}] entitys with common info.", contractClassName, commonInfos.size());
			if (commonInfos.size() == 0) {
				String errMsg = String.format("[%s] entities storage is epty", contractClassName);
				LOGGER.error("Error: {}", errMsg);
				// throw new EmptyResultDataAccessException(1);
				// return null;
				return commonInfos;
			} else {
				return commonInfos;
			}
		} catch (EmptyResultDataAccessException e) {
			String errMsg = String.format("Class [%s]. Storage returns incorrect entity count.", contractClassName);
			LOGGER.error("Error: {}", errMsg);
			throw e;
		}
	}

	public Map<String, ArrayList<ContractCommonInfo>> getCACHEToPersistendSave() {
		return CACHE_ITEM_COMMON_INFO;
	}

	public void SetPersistentSavedCACHE(Map<String, ArrayList<ContractCommonInfo>> cache) {
		CACHE_ITEM_COMMON_INFO = cache;
	}

	@Override
	public void clearCACHE() {
		LOGGER.info("Start clearing {}. Size of cache before = [{}] entities.", "CACHE_ITEM_COMMON_INFO",
				CACHE_ITEM_COMMON_INFO.size());
		CACHE_ITEM_COMMON_INFO.clear();
		LOGGER.info("{} is empty. Size of cache after = [{}] entities.", "CACHE_ITEM_COMMON_INFO",
				CACHE_ITEM_COMMON_INFO.size());
	}

}

package ru.mail.ales2003.deals2017.dao.db.impl.custom;

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

	private static final Map<String, List<ContractCommonInfo>> CACHE_ITEM_COMMON_INFO = new HashMap<>();

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

		// query to CASH

		final String READ_BY_ID_SQL = "select c.id as contract_id, c.created as creation_date,"
				+ " c.contract_status as status, c.pay_form as pay_form, c.pay_status as pay_status, c.total_price as total_amount,"
				+ " customer_group.name as customer_type, c.customer_id as customer_id,"
				+ " customer.last_name as customer_name, customer.company_name as customer_company,"
				+ " customer.manager_id, manager.last_name as manager_name from contract as c"
				+ " left join customer on c.customer_id=customer.id"
				+ " left join manager on customer.manager_id=manager.id"
				+ " left join customer_group on customer.customer_group_id=customer_group.id where c.id = ? ";

		try {

			ContractCommonInfo commonInfo = jdbcTemplate.queryForObject(READ_BY_ID_SQL, new Object[] { contractId },
					new ContractCommonInfoMapper());

			// save to CASH

			return commonInfo;
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

		// query to CASH

		try {
			List<ContractCommonInfo> commonInfos = jdbcTemplate.query(READ_ALL_SQL, new ContractCommonInfoMapper());
			LOGGER.debug("[{}] storage returns [{}] entitys with common info.", contractClassName, commonInfos.size());

			// save to CASH

			return commonInfos;

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

		// query to CASH

		Object[] paramsArray = givenFilter.getQueryParamsArray();
		try {
			List<ContractCommonInfo> commonInfos = jdbcTemplate.query(SQL_WITH_FILTERING, paramsArray,
					new ContractCommonInfoMapper());

			// save to CASH

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
}

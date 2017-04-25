package ru.mail.ales2003.deals2017.dao.db.impl.custom;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.api.customdao.IItemVariantCommonInfoDao;
import ru.mail.ales2003.deals2017.dao.api.customentities.ItemVariantCommonInfo;
import ru.mail.ales2003.deals2017.dao.api.filters.IItemVariantFilter;
import ru.mail.ales2003.deals2017.dao.db.filters.impl.ItemVariantCommonInfoFilter;
import ru.mail.ales2003.deals2017.dao.db.impl.mapper.ItemVariantCommonInfoMapper;
import ru.mail.ales2003.deals2017.datamodel.ItemVariant;

@Repository
public class ItemVariantCommonInfoDaoImpl implements IItemVariantCommonInfoDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemVariantCommonInfoDaoImpl.class);

	@Inject
	private JdbcTemplate jdbcTemplate;

	private String itemVariantClassName = ItemVariant.class.getSimpleName();
	private String commonInfoClassName = ItemVariant.class.getSimpleName();
	private String filterClassName = ItemVariantCommonInfoFilter.class.getSimpleName();

	// =============READING AREA===============

	@Override
	public ItemVariantCommonInfo getCommonInfo(Integer itemVariantId) {
		if (itemVariantId == null) {
			String errMsg = String.format("Error: as the itemVariantId was sent a null reference.");
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg);
		}
		final String READ_BY_ID_SQL = "select v.id as id, i.name as name, i.description as description, v.variant_price as price"
				+ " from item_variant as v left join item as i on i.id=v.item_id where v.id = ?";

		try {

			ItemVariantCommonInfo commonInfo = jdbcTemplate.queryForObject(READ_BY_ID_SQL,
					new Object[] { itemVariantId }, new ItemVariantCommonInfoMapper());
			return commonInfo;
		} catch (EmptyResultDataAccessException e) {
			String errMsg = String.format(
					"You want to READ common information about the [%s] with id = [%s], but it doesn't exist in the storage.",
					itemVariantClassName, itemVariantId);
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg, e);
		}
	}

	@Override
	public List<ItemVariantCommonInfo> getCommonInfoForAll() {
		final String READ_BY_ID_SQL = "select v.id as id, i.name as name, i.description as description, v.variant_price as price"
				+ " from item_variant as v left join item as i on i.id=v.item_id";

		try {
			List<ItemVariantCommonInfo> commonInfos = jdbcTemplate.query(READ_BY_ID_SQL,
					new ItemVariantCommonInfoMapper());
			LOGGER.debug("[{}] storage returns [{}] entitys with common info.", itemVariantClassName,
					commonInfos.size());
			return commonInfos;

		} catch (EmptyResultDataAccessException e) {
			String errMsg = String.format("Class [%s] storage returns incorrect entity count.", itemVariantClassName);
			LOGGER.error("Error: {}", errMsg);
			throw e;
		}
	}

	@Override
	public List<ItemVariantCommonInfo> getCommonInfoFiltered(IItemVariantFilter filter) {
		IItemVariantFilter givenFilter = new ItemVariantCommonInfoFilter();
		givenFilter = filter;
		givenFilter.filterInitialize();
		LOGGER.debug("[{}] is initialized: [{}].", filterClassName, givenFilter.toString());
		final String SQL_WITH_FILTERING = givenFilter.getFullSqlQuery();
		Object[] paramsArray = givenFilter.getQueryParamsArray();
		try {
			List<ItemVariantCommonInfo> commonInfos = jdbcTemplate.query(SQL_WITH_FILTERING, paramsArray,
					new ItemVariantCommonInfoMapper());
			LOGGER.debug("[{}] storage returns [{}] entitys with common info.", itemVariantClassName,
					commonInfos.size());
			if (commonInfos.size() == 0) {
				String errMsg = String.format("[%s] entities storage is epty", itemVariantClassName);
				LOGGER.error("Error: {}", errMsg);
				// throw new EmptyResultDataAccessException(1);
				// return null;
				return commonInfos;
			} else {
				return commonInfos;
			}
		} catch (EmptyResultDataAccessException e) {
			String errMsg = String.format("Class [%s]. Storage returns incorrect entity count.", itemVariantClassName);
			LOGGER.error("Error: {}", errMsg);
			throw e;
		}
	}
}

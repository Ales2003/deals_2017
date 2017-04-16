package ru.mail.ales2003.deals2017.dao.db.impl.custom;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.api.custom.IItemVariantBasicInfoDao;
import ru.mail.ales2003.deals2017.dao.api.custom.entities.ItemVariantBasicInfo;
import ru.mail.ales2003.deals2017.dao.api.filters.IItemVariantFilter;
import ru.mail.ales2003.deals2017.dao.db.filters.impl.ItemVariantBasicInfoFilter;
import ru.mail.ales2003.deals2017.dao.db.impl.mapper.ItemVariantBasicInfoMapper;

@Repository
public class ItemVariantBasicInfoDaoImpl implements IItemVariantBasicInfoDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemVariantBasicInfoDaoImpl.class);

	@Inject
	private JdbcTemplate jdbcTemplate;

	// =============READING AREA===============
	@Override
	public ItemVariantBasicInfo getBasicInfo(Integer itemVariantId) {
		final String READ_BY_ID_SQL = "select v.id as id, i.name as name, i.description as description, v.variant_price as price"
				+ " from item_variant as v left join item as i on i.id=v.item_id where v.id = ?";

		try {
			return jdbcTemplate.queryForObject(READ_BY_ID_SQL, new Object[] { itemVariantId },
					new ItemVariantBasicInfoMapper());
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("Error: item variant with id = " + itemVariantId + " don't exist in storage)", e);
			return null;
		}
	}

	@Override
	public List<ItemVariantBasicInfo> getBasicInfoForEach() {
		final String READ_BY_ID_SQL = "select v.id as id, i.name as name, i.description as description, v.variant_price as price"
				+ " from item_variant as v left join item as i on i.id=v.item_id";

		try {
			List<ItemVariantBasicInfo> basicInfos = jdbcTemplate.query(READ_BY_ID_SQL,
					new ItemVariantBasicInfoMapper());
			return basicInfos;
<<<<<<< HEAD
=======
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("Error: all item variants don't exist in storage)", e);
			return null;
		}
	}

	@Override
	public List<ItemVariantBasicInfo> getFilteredBasicInfo(IItemVariantFilter filter) {
		IItemVariantFilter givenFilter = new ItemVariantBasicInfoFilter();
		givenFilter = filter;
		final String SQL_WITH_FILTERING = givenFilter.getFullSqlQuery();
		Object [] paramsArray = givenFilter.getQueryParamsArray();
		
		try {
			List<ItemVariantBasicInfo> basicInfos = jdbcTemplate.query(SQL_WITH_FILTERING, paramsArray,
					new ItemVariantBasicInfoMapper());
			if (basicInfos.size()==0){
				LOGGER.error("Error: all item 22variants don't exist in storage)");
				throw new EmptyResultDataAccessException(0);
			}
						return basicInfos;
>>>>>>> e37b0e8d462f17858915525dfdb2336e91521f74
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("Error: all item variants don't exist in storage)", e);
			return null;
		}
	}

	@Override
	public List<ItemVariantBasicInfo> getFilteredBasicInfo(IItemVariantFilter filter) {
		IItemVariantFilter givenFilter = new ItemVariantBasicInfoFilter();
		givenFilter = filter;
		final String SQL_WITH_FILTERING = givenFilter.getFullSqlQuery();
		Object[] paramsArray = givenFilter.getQueryParamsArray();

		try {
			List<ItemVariantBasicInfo> basicInfos = jdbcTemplate.query(SQL_WITH_FILTERING, paramsArray,
					new ItemVariantBasicInfoMapper());
			if (basicInfos.size() == 0) {
				LOGGER.error("Error: all item variants don't exist in storage)");
				//throw new EmptyResultDataAccessException(1);
				return null;
			} else {
				return basicInfos;
			}
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("Error: all item variants don't exist in storage)", e);
			return null;
		}
	}
}

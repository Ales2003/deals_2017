package ru.mail.ales2003.deals2017.dao.db.impl.custom;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.api.customdao.IItemVariantDetailDao;
import ru.mail.ales2003.deals2017.dao.api.customentities.ItemVariantDetail;
import ru.mail.ales2003.deals2017.dao.db.impl.mapper.ItemVariantDetailMapper;
import ru.mail.ales2003.deals2017.datamodel.ItemVariant;

@Repository
public class ItemVariantDetailDaoImpl implements IItemVariantDetailDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemVariantDetailDaoImpl.class);

	private String itemVariantClassName = ItemVariant.class.getSimpleName();

	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<ItemVariantDetail> getDetails(Integer itemVariantId) {
		if (itemVariantId == null) {
			String errMsg = String.format("Error: as the itemVariantId was sent a null reference.");
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg);
		}

		final String READ_BY_ID_SQL = "select v.id as item_variant_id, a.attribute as attribute, a.value as value, ct.name as measure "
				+ "from item_variant as v " + "left join character_type_in_item_variant as a "
				+ "on v.id=a.item_variant_id " + "left join character_type as ct " + "on a.character_type_id=ct.id "
				+ "where item_variant_id =? order by a.attribute ";
		try {
			List<ItemVariantDetail> details = jdbcTemplate.query(READ_BY_ID_SQL, new Object[] { itemVariantId },
					new ItemVariantDetailMapper());
			return details;
		} catch (EmptyResultDataAccessException e) {
			String errMsg = String.format("Class [%s] storage returns incorrect entity count.", itemVariantClassName);
			LOGGER.error("Error: {}", errMsg);
			throw e;
		}
	}
}

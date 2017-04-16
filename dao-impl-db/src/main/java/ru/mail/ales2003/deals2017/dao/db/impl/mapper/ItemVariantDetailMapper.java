package ru.mail.ales2003.deals2017.dao.db.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ru.mail.ales2003.deals2017.dao.api.custom.entities.ItemVariantDetail;
import ru.mail.ales2003.deals2017.datamodel.Attribute;
import ru.mail.ales2003.deals2017.datamodel.Measure;

public class ItemVariantDetailMapper implements RowMapper<ItemVariantDetail> {
	
	@Override
	public ItemVariantDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
		Integer itemVariantId = rs.getInt("item_variant_id");
		Attribute attributeName = Attribute.valueOf(rs.getString("attribute"));
		String attributeValue = rs.getString("value");
		Measure attributeMeasure = Measure.valueOf(rs.getString("measure"));

		ItemVariantDetail entity = new ItemVariantDetail();
		entity.setItemVariantId(itemVariantId);
		entity.setAttributeName(attributeName);
		entity.setAttributeValue(attributeValue);
		entity.setAttributeMeasure(attributeMeasure);
		return entity;
	}
}

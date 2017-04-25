package ru.mail.ales2003.deals2017.dao.db.impl.mapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ru.mail.ales2003.deals2017.dao.api.customentities.ItemVariantCommonInfo;

public class ItemVariantCommonInfoMapper implements RowMapper<ItemVariantCommonInfo> {
    @Override
    public ItemVariantCommonInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
    	 Integer itemVariantId = rs.getInt("id");
	        String itemName = rs.getString("name");
	        String itemDescription = rs.getString("description");
	        BigDecimal itemVariantPrice = rs.getBigDecimal("price");
	        
	        ItemVariantCommonInfo entity = new ItemVariantCommonInfo();
	        
	        entity.setItemVariantId(itemVariantId);
	        entity.setItemName(itemName);
	        entity.setItemDescription(itemDescription);
	        entity.setItemVariantPrice(itemVariantPrice);
	        return entity;
    }

}

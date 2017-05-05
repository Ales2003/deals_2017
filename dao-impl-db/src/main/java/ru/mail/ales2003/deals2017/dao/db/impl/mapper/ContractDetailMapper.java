package ru.mail.ales2003.deals2017.dao.db.impl.mapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ru.mail.ales2003.deals2017.dao.api.customentities.ContractDetail;

public class ContractDetailMapper implements RowMapper<ContractDetail> {

	@Override
	public ContractDetail mapRow(ResultSet rs, int rowNum) throws SQLException {

		Integer contractId = rs.getInt("contract_id");
		Integer itemVariantId = rs.getInt("item_variant_id");
		String itemName = rs.getString("item_name");
		Integer itemVariantQuantity = rs.getInt("item_variant_qnt");
		BigDecimal itemVariantPrice = rs.getBigDecimal("item_variant_price");
		BigDecimal itemVariantTotalPrice = rs.getBigDecimal("item_variant_total_price");

		ContractDetail entity = new ContractDetail();
		entity.setContractId(contractId);
		entity.setItemVariantId(itemVariantId);
		entity.setItemName(itemName);
		entity.setItemVariantQuantity(itemVariantQuantity);
		entity.setItemVariantPrice(itemVariantPrice);
		entity.setItemVariantTotalPrice(itemVariantTotalPrice);

		return entity;
	}
}

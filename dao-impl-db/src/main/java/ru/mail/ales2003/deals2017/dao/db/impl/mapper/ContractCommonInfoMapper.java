package ru.mail.ales2003.deals2017.dao.db.impl.mapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.RowMapper;

import ru.mail.ales2003.deals2017.dao.api.customentities.ContractCommonInfo;
import ru.mail.ales2003.deals2017.datamodel.ContractStatus;
import ru.mail.ales2003.deals2017.datamodel.CustomerType;
import ru.mail.ales2003.deals2017.datamodel.PayForm;
import ru.mail.ales2003.deals2017.datamodel.PayStatus;

public class ContractCommonInfoMapper implements RowMapper<ContractCommonInfo> {

	@Override
	public ContractCommonInfo mapRow(ResultSet rs, int rowNum) throws SQLException {

		// contract attributes
		Integer contractId = rs.getInt("contract_id");//
		Timestamp created = rs.getTimestamp("creation_date");//
		ContractStatus contractStatus = ContractStatus.valueOf(rs.getString("status"));//
		PayForm payForm = PayForm.valueOf(rs.getString("pay_form"));//
		PayStatus payStatus = PayStatus.valueOf(rs.getString("pay_status"));//
		BigDecimal totalAmount = rs.getBigDecimal("total_amount");//

		// customer attributes
		CustomerType customerType = CustomerType.valueOf(rs.getString("customer_type"));
		Integer customerId = rs.getInt("customer_id");
		String customerCompanyName = rs.getString("customer_company");
		String customerLastName = rs.getString("customer_name");

		// manager attributes
		Integer managerId = rs.getInt("manager_id");
		String managerLastName = rs.getString("manager_name");

		ContractCommonInfo entity = new ContractCommonInfo();

		entity.setContractId(contractId);
		entity.setCreated(created);
		entity.setContractStatus(contractStatus);
		entity.setPayForm(payForm);
		entity.setPayStatus(payStatus);
		entity.setTotalAmount(totalAmount);

		entity.setCustomerId(customerId);
		entity.setCustomerType(customerType);
		entity.setCustomerCompanyName(customerCompanyName);
		entity.setCustomerLastName(customerLastName);

		entity.setManagerId(managerId);
		entity.setManagerLastName(managerLastName);

		return entity;
	}

}

package ru.mail.ales2003.deals2017.dao.api.filters;

import java.math.BigDecimal;
import java.sql.Timestamp;

import ru.mail.ales2003.deals2017.datamodel.ContractStatus;
import ru.mail.ales2003.deals2017.datamodel.CustomerType;
import ru.mail.ales2003.deals2017.datamodel.PayForm;
import ru.mail.ales2003.deals2017.datamodel.PayStatus;

public interface IContractFilter extends IDealsFilter {

	// setters about customer
	public void setCustomerId(Integer customerId);

	public void setCustomerLastName(String customerLastName);

	public void setCustomerCompany(String customerCompany);

	public void setCustomerType(CustomerType customerType);

	// setters about manager
	public void setManagerId(Integer managerId);

	public void setManagerLastName(String managerLastName);

	// setters about contract

	public void setCreatedFROM(Timestamp createdFROM);

	public void setCreatedTO(Timestamp createdTO);

	public void setAmountMIN(BigDecimal amountMIN);

	public void setAmountMAX(BigDecimal amountMAX);

	public void setContractStatus(ContractStatus contractStatus);

	public void setPayForm(PayForm payForm);

	public void setPayStatus(PayStatus payStatus);

}

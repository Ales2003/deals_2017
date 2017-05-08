package ru.mail.ales2003.deals2017.dao.api.filters;

import java.math.BigDecimal;
import java.sql.Timestamp;

import ru.mail.ales2003.deals2017.datamodel.ContractStatus;
import ru.mail.ales2003.deals2017.datamodel.CustomerType;
import ru.mail.ales2003.deals2017.datamodel.PayForm;
import ru.mail.ales2003.deals2017.datamodel.PayStatus;

public interface IContractFilter extends IDealsFilter {

	// setters about customer
	void setCustomerId(Integer customerId);

	void setCustomerLastName(String customerLastName);

	void setCustomerCompany(String customerCompany);

	void setCustomerType(CustomerType customerType);

	// setters about manager
	void setManagerId(Integer managerId);

	void setManagerLastName(String managerLastName);

	// setters about contract

	void setCreatedFROM(Timestamp createdFROM);

	void setCreatedTO(Timestamp createdTO);

	void setAmountMIN(BigDecimal amountMIN);

	void setAmountMAX(BigDecimal amountMAX);

	void setContractStatus(ContractStatus contractStatus);

	void setPayForm(PayForm payForm);

	void setPayStatus(PayStatus payStatus);

}

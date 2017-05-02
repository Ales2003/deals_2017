package ru.mail.ales2003.deals2017.dao.api.customentities;

import java.math.BigDecimal;
import java.sql.Timestamp;

import ru.mail.ales2003.deals2017.datamodel.ContractStatus;
import ru.mail.ales2003.deals2017.datamodel.CustomerGroup;
import ru.mail.ales2003.deals2017.datamodel.PayForm;
import ru.mail.ales2003.deals2017.datamodel.PayStatus;

public class ContractCommonInfo {

	//contract attributes
	private Integer contractId;
	private Timestamp created;
	private ContractStatus contractStatus;
	private PayForm payForm;
	private PayStatus payStatus;
	private BigDecimal totalAmount;

	//customer attributes
	private Integer customerId;
	private CustomerGroup customerGroup;
	private String customerCompanyName;
	private String customerLastName;

	//manager attributes
	private String managerLastName;

	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public ContractStatus getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(ContractStatus contractStatus) {
		this.contractStatus = contractStatus;
	}

	public PayForm getPayForm() {
		return payForm;
	}

	public void setPayForm(PayForm payForm) {
		this.payForm = payForm;
	}

	public PayStatus getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(PayStatus payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}


	public CustomerGroup getCustomerGroup() {
		return customerGroup;
	}

	public void setCustomerGroup(CustomerGroup customerGroup) {
		this.customerGroup = customerGroup;
	}

	public String getCustomerCompanyName() {
		return customerCompanyName;
	}

	public void setCustomerCompanyName(String customerCompanyName) {
		this.customerCompanyName = customerCompanyName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public String getManagerLastName() {
		return managerLastName;
	}

	public void setManagerLastName(String managerLastName) {
		this.managerLastName = managerLastName;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Override
	public String toString() {
		return "ContractCommonInfo [contractId=" + contractId + ", created=" + created + ", contractStatus="
				+ contractStatus + ", payForm=" + payForm + ", payStatus=" + payStatus + ", totalAmount=" + totalAmount
				+ ", customerId=" + customerId + ", customerGroup=" + customerGroup + ", customerCompanyName="
				+ customerCompanyName + ", customerLastName=" + customerLastName + ", managerLastName="
				+ managerLastName + "]";
	}



}

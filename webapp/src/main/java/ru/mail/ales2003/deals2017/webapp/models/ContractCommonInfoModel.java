package ru.mail.ales2003.deals2017.webapp.models;

import java.math.BigDecimal;

public class ContractCommonInfoModel {

	// contract attributes
	private Integer contractId;
	private String created;
	private String contractStatus;
	private String payForm;
	private String payStatus;
	private BigDecimal totalAmount;

	// customer attributes
	private Integer customerId;
	private String customerType;
	private String customerCompanyName;
	private String customerLastName;

	// manager attributes
	private Integer managerId;
	private String managerLastName;

	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}

	public String getPayForm() {
		return payForm;
	}

	public void setPayForm(String payForm) {
		this.payForm = payForm;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
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

	public Integer getManagerId() {
		return managerId;
	}

	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}

	public String getManagerLastName() {
		return managerLastName;
	}

	public void setManagerLastName(String managerLastName) {
		this.managerLastName = managerLastName;
	}

	@Override
	public String toString() {
		return "ContractCommonInfoModel [contractId=" + contractId + ", created=" + created + ", contractStatus="
				+ contractStatus + ", payForm=" + payForm + ", payStatus=" + payStatus + ", totalAmount=" + totalAmount
				+ ", customerId=" + customerId + ", customerType=" + customerType + ", customerCompanyName="
				+ customerCompanyName + ", customerLastName=" + customerLastName + ", managerId=" + managerId
				+ ", managerLastName=" + managerLastName + "]";
	}

}

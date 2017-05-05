package ru.mail.ales2003.deals2017.webapp.models;

import java.math.BigDecimal;

public class ContractModel {
	private Integer id;
	// Converters???
	private String created;
	// Converters???
	private String contractStatus;
	private String payForm;
	private String payStatus;

	private Integer customerId;
	private BigDecimal totalPrice;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "ContractModel [id=" + id + ", created=" + created + ", contractStatus=" + contractStatus + ", payForm="
				+ payForm + ", payStatus=" + payStatus + ", customerId=" + customerId + ", totalPrice=" + totalPrice
				+ "]";
	}

}

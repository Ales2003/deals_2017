package ru.mail.ales2003.deals2017.datamodel;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Contract {

	private Integer id;
	private Timestamp сreated;
	private ContractStatus contractStatus;
	private PayForm payForm;
	private PayStatus payStatus;
	private Integer customerId;
	private BigDecimal totalPrice;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getСreated() {
		return сreated;
	}

	public void setСreated(Timestamp сreated) {
		this.сreated = сreated;
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

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "Contract [id=" + id + ", сreated=" + сreated + ", contractStatus=" + contractStatus + ", payForm="
				+ payForm + ", payStatus=" + payStatus + ", customerId=" + customerId + ", totalPrice=" + totalPrice
				+ "]";
	}

}

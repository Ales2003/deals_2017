package ru.mail.ales2003.deals2017.datamodel;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Contract {

	private Integer id;
	private Timestamp created;
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

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "Contract [id=" + id + ", сreated=" + created + ", contractStatus=" + contractStatus + ", payForm="
				+ payForm + ", payStatus=" + payStatus + ", customerId=" + customerId + ", totalPrice=" + totalPrice
				+ "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contractStatus == null) ? 0 : contractStatus.hashCode());
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((payForm == null) ? 0 : payForm.hashCode());
		result = prime * result + ((payStatus == null) ? 0 : payStatus.hashCode());
		result = prime * result + ((totalPrice == null) ? 0 : totalPrice.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contract other = (Contract) obj;
		if (contractStatus != other.contractStatus)
			return false;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (payForm != other.payForm)
			return false;
		if (payStatus != other.payStatus)
			return false;
		if (totalPrice == null) {
			if (other.totalPrice != null)
				return false;
		} else if (!totalPrice.equals(other.totalPrice))
			return false;
		return true;
	}
	
	

	
	
}

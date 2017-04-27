package ru.mail.ales2003.deals2017.datamodel;

public class ItemVariantInContract {

	private Integer id;
	private Integer quantity;
	private Integer contractId;
	private Integer itemVariantId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	public Integer getItemVariantId() {
		return itemVariantId;
	}

	public void setItemVariantId(Integer itemVariantId) {
		this.itemVariantId = itemVariantId;
	}

	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ItemVariantInContract [id=" + id + ", quantity=" + quantity + ", contractId=" + contractId
				+ ", itemVariantId=" + itemVariantId + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contractId == null) ? 0 : contractId.hashCode());
		result = prime * result + ((itemVariantId == null) ? 0 : itemVariantId.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		ItemVariantInContract other = (ItemVariantInContract) obj;
		if (contractId == null) {
			if (other.contractId != null)
				return false;
		} else if (!contractId.equals(other.contractId))
			return false;
		if (itemVariantId == null) {
			if (other.itemVariantId != null)
				return false;
		} else if (!itemVariantId.equals(other.itemVariantId))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		return true;
	}

}

package ru.mail.ales2003.deals2017.datamodel;

public class Contract2ItemVariant {

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

	@Override
	public String toString() {
		return "Contract2ItemVariant [id=" + id + ", quantity=" + quantity + ", contractId=" + contractId
				+ ", itemVariantId=" + itemVariantId + "]";
	}

	

}

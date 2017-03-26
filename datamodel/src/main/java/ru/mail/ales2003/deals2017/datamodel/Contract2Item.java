package ru.mail.ales2003.deals2017.datamodel;

public class Contract2Item {

	private Integer id;
	private Integer quantity;
	private Integer contractId;
	private Integer itemId;

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

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	@Override
	public String toString() {
		return "Contract2Item [id=" + id + ", quantity=" + quantity + ", contractId=" + contractId + ", itemId="
				+ itemId + "]";
	}

}

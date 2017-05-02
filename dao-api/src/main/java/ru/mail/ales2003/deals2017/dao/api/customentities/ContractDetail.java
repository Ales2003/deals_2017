package ru.mail.ales2003.deals2017.dao.api.customentities;

import java.math.BigDecimal;

public class ContractDetail {

	private Integer contractId;

	private Integer itemVariantId;

	private String itemName;
	private BigDecimal itemVariantPrice;
	private Integer itemVariantQuantity;
	private BigDecimal itemVariantTotalPrice;

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

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public BigDecimal getItemVariantPrice() {
		return itemVariantPrice;
	}

	public void setItemVariantPrice(BigDecimal itemVariantPrice) {
		this.itemVariantPrice = itemVariantPrice;
	}

	public Integer getItemVariantQuantity() {
		return itemVariantQuantity;
	}

	public void setItemVariantQuantity(Integer itemVariantQuantity) {
		this.itemVariantQuantity = itemVariantQuantity;
	}

	public BigDecimal getItemVariantTotalPrice() {
		return itemVariantTotalPrice;
	}

	public void setItemVariantTotalPrice(BigDecimal itemVariantTotalPrice) {
		this.itemVariantTotalPrice = itemVariantTotalPrice;
	}

	@Override
	public String toString() {
		return "ContractDetail [contractId=" + contractId + ", itemVariantId=" + itemVariantId + ", itemName="
				+ itemName + ", itemVariantPrice=" + itemVariantPrice + ", itemVariantQuantity=" + itemVariantQuantity
				+ ", itemVariantTotalPrice=" + itemVariantTotalPrice + "]";
	}

}

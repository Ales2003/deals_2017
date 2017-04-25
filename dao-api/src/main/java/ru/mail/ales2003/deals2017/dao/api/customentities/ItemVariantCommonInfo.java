package ru.mail.ales2003.deals2017.dao.api.customentities;

import java.math.BigDecimal;

public class ItemVariantCommonInfo {
	
	private Integer itemVariantId;

	private String itemName;

	private String itemDescription;

	private BigDecimal itemVariantPrice;

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

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public BigDecimal getItemVariantPrice() {
		return itemVariantPrice;
	}

	public void setItemVariantPrice(BigDecimal itemVariantPrice) {
		this.itemVariantPrice = itemVariantPrice;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ItemVariantCommonInfo [itemVariantId=" + itemVariantId + ", itemName=" + itemName + ", itemDescription="
				+ itemDescription + ", itemVariantPrice=" + itemVariantPrice + "]";
	}

	

	
	
	
}

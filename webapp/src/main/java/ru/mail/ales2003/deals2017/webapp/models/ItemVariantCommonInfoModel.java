package ru.mail.ales2003.deals2017.webapp.models;

import java.math.BigDecimal;

public class ItemVariantCommonInfoModel {

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

	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemName
	 *            the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return the itemDescription
	 */
	public String getItemDescription() {
		return itemDescription;
	}

	/**
	 * @param itemDescription
	 *            the itemDescription to set
	 */
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	/**
	 * @return the itemVariantPrice
	 */
	public BigDecimal getItemVariantPrice() {
		return itemVariantPrice;
	}

	/**
	 * @param itemVariantPrice the itemVariantPrice to set
	 */
	public void setItemVariantPrice(BigDecimal itemVariantPrice) {
		this.itemVariantPrice = itemVariantPrice;
	}

	@Override
	public String toString() {
		return "ItemVariantCommonInfoModel [itemVariantId=" + itemVariantId + ", itemName=" + itemName
				+ ", itemDescription=" + itemDescription + ", itemVariantPrice=" + itemVariantPrice + "]";
	}



}

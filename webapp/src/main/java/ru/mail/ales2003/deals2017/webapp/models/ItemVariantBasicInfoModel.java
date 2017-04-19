package ru.mail.ales2003.deals2017.webapp.models;

import java.math.BigDecimal;

public class ItemVariantBasicInfoModel {

	private String itemName;

	private String itemDescription;

	private BigDecimal itemVariantPrice;

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



}

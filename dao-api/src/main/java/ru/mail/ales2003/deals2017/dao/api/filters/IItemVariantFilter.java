package ru.mail.ales2003.deals2017.dao.api.filters;

import java.math.BigDecimal;

public interface IItemVariantFilter extends IDealsFilter {

	/**
	 * @param itemVariantName
	 *            the name of item variant to set
	 */
	public void setItemVariantName(String itemVariantName);

	/**
	 * @param itemVariantPriceMIN
	 *            the price of item variant to set
	 */
	public void setItemVariantPriceMIN (BigDecimal itemVariantPriceMIN);
	
	/**
	 * @param itemVariantPriceMAX
	 *            the price of item variant to set
	 */
	public void setItemVariantPriceMAX (BigDecimal itemVariantPriceMAX);

	/**
	 * @param itemVariantDescription
	 *            the the description of item variant to set
	 */
	public void setItemVariantDescription(String itemVariantDescription);

}

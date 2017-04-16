package ru.mail.ales2003.deals2017.dao.api.custom;

import java.util.List;

import ru.mail.ales2003.deals2017.dao.api.custom.entities.ItemVariantBasicInfo;

public interface IItemVariantBasicInfoDao {

	/**
	 * @param itemVariantId
	 *            - the id of items variant
	 * @return - The method returns a list of the details of the items variant
	 */

	/**
	 * @param itemVariantId
	 *            - the id of items variant.
	 * @return - The method returns a basic inform about items variant: id of
	 *         items variant, items name, items description, price of items variant. 
	 */
	ItemVariantBasicInfo getBasicInfo(Integer itemVariantId);

	/**
	 * @return - The method returns a list of the basic inform about each item.
	 */
	List<ItemVariantBasicInfo> getAllBasicInfo();

}

package ru.mail.ales2003.deals2017.dao.api.customdao;

import java.util.List;

import ru.mail.ales2003.deals2017.dao.api.customentities.ItemVariantCommonInfo;
import ru.mail.ales2003.deals2017.dao.api.filters.IItemVariantFilter;

/**
 * Methods provide a common information about item variant: id of item variant,
 * items name, items description, price of items variant. These methods are
 * designed to display understandable information to customers.
 * 
 * @author admin
 */
public interface IItemVariantCommonInfoDao {

	/**
	 * @param itemVariantId
	 *            - the id of item variant.
	 * @return - The method returns a common info about item variant.
	 */
	ItemVariantCommonInfo getCommonInfo(Integer itemVariantId);

	/**
	 * @param filter
	 *            - the IItemVariantFilter. 
	 * @return filtered list List&ltItemVariantCommonInfo&gt entities
	 */
	List<ItemVariantCommonInfo> getCommonInfoFiltered(IItemVariantFilter filter);

	/**
	 * @return - list List&ltItemVariantCommonInfo&gt of the common info about
	 *         all item variants.
	 */
	List<ItemVariantCommonInfo> getCommonInfoForAll();

}

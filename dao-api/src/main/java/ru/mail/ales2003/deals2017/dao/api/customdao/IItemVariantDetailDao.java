package ru.mail.ales2003.deals2017.dao.api.customdao;

import java.util.List;

import ru.mail.ales2003.deals2017.dao.api.customentities.ItemVariantDetail;

/**
 * Methods provide a detail information about all attributes of item variant: id
 * of item variant, attribute name, attribute value and unit of measurement.
 * These methods are designed to display understandable information to customers.
 * 
 * @author admin
 */
public interface IItemVariantDetailDao {

	/**
	 * @param itemVariantId
	 *            - the id of item variant
	 * @return - a list List&ltItemVariantDetail&gt of the item variant details
	 */
	List<ItemVariantDetail> getDetails(Integer itemVariantId);
}

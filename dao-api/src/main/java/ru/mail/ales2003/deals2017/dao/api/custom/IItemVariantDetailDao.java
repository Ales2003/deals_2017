package ru.mail.ales2003.deals2017.dao.api.custom;

import java.util.List;

import ru.mail.ales2003.deals2017.dao.api.custom.entities.ItemVariantDetail;

public interface IItemVariantDetailDao {
	
	
	
	/**
	 * @param itemVariantId - the id of items variant
	 * @return - The method returns a list of the details of the items variant
	 */
	List<ItemVariantDetail> getDetails(Integer itemVariantId);
}

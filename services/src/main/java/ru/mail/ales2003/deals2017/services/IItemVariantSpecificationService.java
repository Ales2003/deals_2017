package ru.mail.ales2003.deals2017.services;

import java.util.List;

import ru.mail.ales2003.deals2017.dao.api.customentities.ItemVariantCommonInfo;
import ru.mail.ales2003.deals2017.dao.api.customentities.ItemVariantDetail;
import ru.mail.ales2003.deals2017.dao.api.customentities.ItemVariantSpecification;
import ru.mail.ales2003.deals2017.dao.api.filters.IItemVariantFilter;

/**
 * Methods provide both common and detailed information about item variants.
 * The first part of the methods is based on the use IItemVariantCommonInfoDao.
 * The first part of the methods is based on the use IItemVariantDetailDao. The
 * third part of the methods is based on both marked Daos. These methods are
 * designed to display understandable information to customers.
 * 
 * @author admin
 */

public interface IItemVariantSpecificationService {

	/**
	 * @param itemVariantId
	 * @return common info for one item variant
	 */
	ItemVariantCommonInfo getCommonInfo(Integer itemVariantId);

	/**
	 * @return common info for all item variants
	 */
	List<ItemVariantCommonInfo> getCommonInfoForAll();

	/**
	 * @param filter
	 * @return common infos list List&ltItemVariantCommonInfo&gt for some item
	 *         variants after filtering by basic parameters: items name, items
	 *         description, item variants price also with sorting and
	 *         limit/offset.
	 */
	List<ItemVariantCommonInfo> getCommonInfoFiltered(IItemVariantFilter filter);

	/**
	 * @param itemVariantId
	 * @return details list List&ltItemVariantDetail&gt for one item variant
	 */
	List<ItemVariantDetail> getDetails(Integer itemVariantId);

	/**
	 * @param itemVariantId
	 * @return specification as a consolidation of common information and
	 *         details list for one item variant
	 */
	ItemVariantSpecification getSpecification(Integer itemVariantId);



}

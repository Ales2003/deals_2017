package ru.mail.ales2003.deals2017.services;

import java.util.List;

import ru.mail.ales2003.deals2017.dao.api.custom.entities.ItemVariantBasicInfo;
import ru.mail.ales2003.deals2017.dao.api.custom.entities.ItemVariantDetail;
import ru.mail.ales2003.deals2017.dao.api.custom.entities.ItemVariantSpecification;
import ru.mail.ales2003.deals2017.dao.api.filters.IItemVariantFilter;

public interface IItemVariantSpecificationService {
	// ===========================Methods for reading with an improved result

		// returns basic info for one item variant
		ItemVariantBasicInfo getBasicInfo(Integer itemVariantId);

		// returns list basic info for each item variant
		List<ItemVariantBasicInfo> getBasicInfoForEach();

		// returns details for one item variant
		List<ItemVariantDetail> getDetails(Integer itemVariantId);

		// returns basic info + details = specification for one item variant
		ItemVariantSpecification getSpecification(Integer itemVariantId);

		// returns basic info with filtering by basic parameters
		List<ItemVariantBasicInfo> getFilteredBasicInfo(IItemVariantFilter filter);

		// returns basic info + details = specification for each item variant

		/**
		 * @param filter
		 * @return basic info with details (specification) for each filtered item
		 *         variant
		 */
		List<ItemVariantSpecification> getFilteredSpecifications(IItemVariantFilter filter);

		/**
		 * @param filter
		 * @return basic info with details (specification) for each item variant
		 */
		List<ItemVariantSpecification> getSpecifications();

}

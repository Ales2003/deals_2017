package ru.mail.ales2003.deals2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ru.mail.ales2003.deals2017.dao.api.custom.entities.ItemVariantBasicInfo;
import ru.mail.ales2003.deals2017.dao.api.custom.entities.ItemVariantDetail;
import ru.mail.ales2003.deals2017.dao.api.custom.entities.ItemVariantSpecification;
import ru.mail.ales2003.deals2017.dao.api.filters.IItemVariantFilter;
import ru.mail.ales2003.deals2017.datamodel.CharacterTypeInItemVariant;
import ru.mail.ales2003.deals2017.datamodel.ItemVariant;

/**
 * @author admin
 *
 */
public interface IItemVariantService {

	// ======================Item variant entities management and handling

	/**
	 * @param itemVariantId
	 * @return ItemVariant entity
	 */
	ItemVariant getItemVariant(Integer itemVariantId);

	/**
	 * @return List&ltCharacterType&gt itemVariants
	 */
	List<ItemVariant> getAllItemVariants();

	/**
	 * @param itemVariant
	 *            entity
	 */
	@Transactional
	void saveItemVariant(ItemVariant itemVariant);

	/**
	 * @param itemVariantArray
	 */
	@Transactional
	void saveItemVariantMultiple(ItemVariant... itemVariantArray);

	/**
	 * @param itemVariantId
	 */
	@Transactional
	void deleteItemVariant(Integer itemVariantId);

	// ==================Attributes management and handling in an item variant

	/**
	 * @param id
	 * @return CharacterTypeInItemVariant entity
	 */
	CharacterTypeInItemVariant getAttribute(Integer id);

	/**
	 * @return List&ltCharacterTypeInItemVariant&gt entitys
	 */
	public List<CharacterTypeInItemVariant> getAllAttributes();

	/**
	 * @param attribute
	 */
	@Transactional
	void saveAttribute(CharacterTypeInItemVariant attribute);

	/**
	 * @param attributeArray
	 */
	@Transactional
	void saveAttributeMultiple(CharacterTypeInItemVariant... attributeArray);

	/**
	 * @param id
	 */
	public void deleteAttribute(Integer id);

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

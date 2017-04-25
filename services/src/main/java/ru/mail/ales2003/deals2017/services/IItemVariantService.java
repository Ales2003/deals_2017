package ru.mail.ales2003.deals2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

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
	 * @return List&ltCharacterType&gt entities
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
}

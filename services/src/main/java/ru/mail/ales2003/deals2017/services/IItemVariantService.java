package ru.mail.ales2003.deals2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ru.mail.ales2003.deals2017.dao.api.custom.entities.ItemVariantBasicInfo;
import ru.mail.ales2003.deals2017.dao.api.custom.entities.ItemVariantDetail;
import ru.mail.ales2003.deals2017.dao.api.custom.entities.ItemVariantSpecification;
import ru.mail.ales2003.deals2017.datamodel.CharacterTypeInItemVariant;
import ru.mail.ales2003.deals2017.datamodel.ItemVariant;

public interface IItemVariantService {

	// Item variant entities management and handling

	ItemVariant getItemVariant(Integer itemVariantId);

	List<ItemVariant> getAllItemVariants();

	@Transactional
	void saveItemVariant(ItemVariant itemVariant);

	@Transactional
	void saveItemVariantMultiple(ItemVariant... itemVariantArray);

	@Transactional
	void deleteItemVariant(Integer itemVariantId);

	// Attributes management and handling in an item variant

	CharacterTypeInItemVariant getCharacterTypeInItemVariant(Integer id);

	public List<CharacterTypeInItemVariant> getAllCharacterTypeInItemVariant();

	@Transactional
	void saveCharacterTypeInItemVariant(CharacterTypeInItemVariant attribute);

	@Transactional
	void saveMultipleCharacterTypeInItemVariant(CharacterTypeInItemVariant... attributeArray);

	public void deleteCharacterTypeInItemVariant(Integer id);

	// Methods for reading with an improved result

	ItemVariantBasicInfo getBasicInfo(Integer itemVariantId);
	
	List<ItemVariantBasicInfo> getAllBasicInfo();

	List<ItemVariantDetail> getDetails(Integer itemVariantId);
	
	ItemVariantSpecification getSpecification(Integer itemVariantId);
}

package ru.mail.ales2003.deals2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ru.mail.ales2003.deals2017.datamodel.CharacterType;
import ru.mail.ales2003.deals2017.datamodel.ItemVariant;

public interface IItemVariantService {

	ItemVariant getItemVariant(Integer itemVariantId);

	List<ItemVariant> getAllItemVariants();

	@Transactional
	void saveItemVariant(ItemVariant itemVariant);

	@Transactional
	void saveItemVariantMultiple(ItemVariant... itemVariantArray);

	@Transactional
	void deleteItemVariant(Integer itemVariantId);

	
	

}

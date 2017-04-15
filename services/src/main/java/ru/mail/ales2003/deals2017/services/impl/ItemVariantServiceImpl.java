package ru.mail.ales2003.deals2017.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ru.mail.ales2003.deals2017.dao.api.ICharacterTypeInItemVariantDao;
import ru.mail.ales2003.deals2017.dao.api.IItemVariantDao;
import ru.mail.ales2003.deals2017.dao.api.custom.IItemVariantBasicInfoDao;
import ru.mail.ales2003.deals2017.dao.api.custom.entities.ItemVariantBasicInfo;
import ru.mail.ales2003.deals2017.datamodel.CharacterTypeInItemVariant;
import ru.mail.ales2003.deals2017.datamodel.ItemVariant;
import ru.mail.ales2003.deals2017.services.IItemVariantService;

@Service
public class ItemVariantServiceImpl implements IItemVariantService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemVariantServiceImpl.class);

	@Inject
	public IItemVariantDao itemVariantDao;

	@Inject
	public IItemVariantBasicInfoDao basicInfoDao;

	@Inject
	ICharacterTypeInItemVariantDao attributeDao;

	// ItemVariant management and handling
	// =============READING AREA===============
	@Override
	public ItemVariant getItemVariant(Integer id) {
		if (itemVariantDao.get(id) == null) {
			LOGGER.error("Error: itemVariant with id = " + id + " don't exist in storage)");
			return null;
		} else {
			ItemVariant itemVariant = itemVariantDao.get(id);
			LOGGER.info("Read one itemVariant: id={}, itemId={}, variantPrice={}", itemVariant.getId(),
					itemVariant.getItemId(), itemVariant.getVariantPrice());
			return itemVariant;
		}
	}

	@Override
	public List<ItemVariant> getAllItemVariants() {
		if (itemVariantDao.getAll() == null) {
			LOGGER.error("Error: all itemVariants don't exist in storage");
			return null;
		} else {
			LOGGER.info("Read all itemVariants");
			return itemVariantDao.getAll();
		}
	}

	// =============CREATION/UPDATE AREA===============
	@Override
	public void saveItemVariant(ItemVariant itemVariant) {
		if (itemVariant == null) {
			LOGGER.error("Error: as the item was sent a null reference");
			return;
		} else if (itemVariant.getId() == null) {
			itemVariantDao.insert(itemVariant);
			LOGGER.info("Inserted new itemVariant: id={}, itemId={}, variantPrice={}", itemVariant.getId(),
					itemVariant.getItemId(), itemVariant.getVariantPrice());
		} else {
			itemVariantDao.update(itemVariant);
			LOGGER.info("Updated itemVariant: id={}, itemId={}, variantPrice={}", itemVariant.getId(),
					itemVariant.getItemId(), itemVariant.getVariantPrice());
		}

	}

	@Override
	public void saveItemVariantMultiple(ItemVariant... itemVariantArray) {
		for (ItemVariant itemVariant : itemVariantArray) {
			LOGGER.debug("Inserted new itemVariant from array: " + itemVariant);
			saveItemVariant(itemVariant);
		}
		LOGGER.info("Inserted itemVariants from array");
	}

	// =============DELETE AREA===============
	@Override
	public void deleteItemVariant(Integer id) {
		if (id == null) {
			LOGGER.error("Error: as the id was sent a null reference");
			return;
		} else {
			itemVariantDao.delete(id);
			LOGGER.info("Deleted itemVariant by id: " + id);
		}

	}

	// Attributes management and handling in an item variant

	// =============READING AREA===============
	public ItemVariantBasicInfo getItemVariantWithBasicInfo(Integer itemVariantId) {

		return basicInfoDao.getItemVariantWithBasicInfo(itemVariantId);

	};

	// =============CREATION AREA===============

	/*
	 * This method saves the characteristic of the ItemVariant using the ready
	 * Dao and classes from Datamodel
	 */
	public void saveWithAttribute(CharacterTypeInItemVariant attribute) {

		if (attribute == null) {
			LOGGER.error("Error: as the item was sent a null reference");
			return;
		} else if (attribute.getId() == null) {
			attributeDao.insert(attribute);
			LOGGER.info("Inserted new attribute: id={}, itemVariantId={}, attribute={}, characterTypeId={}, value={}",
					attribute.getId(), attribute.getItemVariantId(), attribute.getAttribute(),
					attribute.getCharacterTypeId(), attribute.getValue());
		} else {
			attributeDao.update(attribute);
			LOGGER.info("Updated attribute: id={}, itemVariantId={}, attribute={}, characterTypeId={}, value={}",
					attribute.getId(), attribute.getItemVariantId(), attribute.getAttribute(),
					attribute.getCharacterTypeId(), attribute.getValue());
		}
	}

	/*
	 * This method saves list of characteristics of the ItemVariant using the
	 * ready Dao and classes from Datamodel
	 */
	public void saveMultipleWithAttribute(CharacterTypeInItemVariant... attributeArray) {
		for (CharacterTypeInItemVariant attribute : attributeArray) {
			LOGGER.debug("Inserted new attribute from array: " + attribute);
			saveWithAttribute(attribute);
		}
		LOGGER.info("Inserted attributes from array");
	}

	public List<CharacterTypeInItemVariant> getAttributesByItemVariantId(Integer id) {
		List<CharacterTypeInItemVariant> attributes = new ArrayList<>();
		// TO DO + add in SQL in DAO
		return attributes;
	}

	// in DataModel create classes ItemVariantAttributes:
	// fields Integer ItemId;
	// String ItemName;
	// List<ItemVariantAttributes> attributes,
	// & ItemVariantFullDescription:
	// fields Integer AttributeId;
	// Attribute attributeName;
	// String attributeValue
	// String measureName

	// TO DO ItemVariantAttributes- SQL in DAO
	// TO DO ItemName SQL in DAO
	/*
	 * public ItemVariantFullDescription getItemVariantFullDescription(Integer
	 * Id) {
	 * 
	 * }
	 */

}

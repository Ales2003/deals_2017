package ru.mail.ales2003.deals2017.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ru.mail.ales2003.deals2017.dao.api.ICharacterTypeInItemVariantDao;
import ru.mail.ales2003.deals2017.dao.api.IItemVariantDao;
import ru.mail.ales2003.deals2017.dao.api.custom.IItemVariantBasicInfoDao;
import ru.mail.ales2003.deals2017.dao.api.custom.IItemVariantDetailDao;
import ru.mail.ales2003.deals2017.dao.api.custom.entities.ItemVariantBasicInfo;
import ru.mail.ales2003.deals2017.dao.api.custom.entities.ItemVariantDetail;
import ru.mail.ales2003.deals2017.dao.api.custom.entities.ItemVariantSpecification;
import ru.mail.ales2003.deals2017.datamodel.CharacterTypeInItemVariant;
import ru.mail.ales2003.deals2017.datamodel.ItemVariant;
import ru.mail.ales2003.deals2017.services.IItemVariantService;

@Service
public class ItemVariantServiceImpl implements IItemVariantService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemVariantServiceImpl.class);

	@Inject
	public IItemVariantDao itemVariantDao;

	@Inject
	public ICharacterTypeInItemVariantDao attributeDao;

	@Inject
	public IItemVariantBasicInfoDao basicInfoDao;

	@Inject
	public IItemVariantDetailDao detailsDao;

	// ItemVariant management and handling
	// =============READING AREA===============
	@Override
	public ItemVariant getItemVariant(Integer id) {
		if (itemVariantDao.get(id) == null) {
			LOGGER.error("Error: itemVariant with id = {} don't exist in storage", id);
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
			LOGGER.info("Read all itemVariants:");
			for (ItemVariant iv : itemVariantDao.getAll()) {
				LOGGER.info("itemVariant = {}", iv.toString());
			}
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

	@Override
	public CharacterTypeInItemVariant getCharacterTypeInItemVariant(Integer id) {
		if (attributeDao.get(id) == null) {
			LOGGER.error("Error: characterTypeInItemVariant with id = " + id + " don't exist in storage)");
			return null;
		} else {
			CharacterTypeInItemVariant attribute = attributeDao.get(id);
			LOGGER.info(
					"Read one characterTypeInItemVariant: id={}, itemVariantId={}, attribute={}, value ={}, characterTypeId={}",
					attribute.getId(), attribute.getItemVariantId(), attribute.getAttribute(), attribute.getValue(),
					attribute.getCharacterTypeId());
			return attribute;
		}
	}

	@Override
	public List<CharacterTypeInItemVariant> getAllCharacterTypeInItemVariant() {
		if (attributeDao.getAll() == null) {
			LOGGER.error("Error: all characterTypeInItemVariant don't exist in storage");
			return null;
		} else {
			LOGGER.info("Read all characterTypeInItemVariant");
			return attributeDao.getAll();
		}
	}

	// =============CREATION AREA===============

	/*
	 * This method saves the characteristic of the ItemVariant using the ready
	 * Dao and classes from Datamodel
	 */
	@Override
	public void saveCharacterTypeInItemVariant(CharacterTypeInItemVariant attribute) {

		if (attribute == null) {
			LOGGER.error("Error: as the item was sent a null reference");
			return;
		} else if (attribute.getId() == null) {
			attributeDao.insert(attribute);
			LOGGER.info(
					"Inserted new characterTypeInItemVariant: id={}, itemVariantId={}, attribute={}, characterTypeId={}, value={}",
					attribute.getId(), attribute.getItemVariantId(), attribute.getAttribute(),
					attribute.getCharacterTypeId(), attribute.getValue());
		} else {
			attributeDao.update(attribute);
			LOGGER.info(
					"Updated characterTypeInItemVariant: id={}, itemVariantId={}, attribute={}, characterTypeId={}, value={}",
					attribute.getId(), attribute.getItemVariantId(), attribute.getAttribute(),
					attribute.getCharacterTypeId(), attribute.getValue());
		}
	}

	/*
	 * This method saves list of characteristics of the ItemVariant using the
	 * ready Dao and classes from Datamodel
	 */
	@Override
	public void saveMultipleCharacterTypeInItemVariant(CharacterTypeInItemVariant... attributeArray) {
		for (CharacterTypeInItemVariant attribute : attributeArray) {
			LOGGER.debug("Inserted new characterTypeInItemVariant from array: " + attribute);
			saveCharacterTypeInItemVariant(attribute);
		}
		LOGGER.info("Inserted characterTypeInItemVariant from array");
	}

	// =============DELETE AREA===============
	@Override
	public void deleteCharacterTypeInItemVariant(Integer id) {
		if (id == null) {
			LOGGER.error("Error: as the id was sent a null reference");
			return;
		} else {
			attributeDao.delete(id);
			LOGGER.info("Deleted characterTypeInItemVariant by id: " + id);
		}
	}

	// =============READING AREA BASED CUSTOM CLASSES ===============
	@Override
	public ItemVariantBasicInfo getBasicInfo(Integer itemVariantId) {
		if (basicInfoDao.getBasicInfo(itemVariantId) == null) {
			LOGGER.error("Error: itemVariant with id = {} don't exist in storage", itemVariantId);
			return null;
		} else {
			LOGGER.info("Read itemVariant with id = {} with basic info: {}", itemVariantId, basicInfoDao.getBasicInfo(itemVariantId).toString());
			ItemVariantBasicInfo basicInfo = basicInfoDao.getBasicInfo(itemVariantId);
			return basicInfo;
		}
	}

	@Override
	public List<ItemVariantBasicInfo> getAllBasicInfo() {
		if (basicInfoDao.getAllBasicInfo() == null) {
			LOGGER.error("Error: all itemVariants don't exist in storage");
			return null;
		} else {
			LOGGER.info("Read all itemVariants with basic info:");

			for (ItemVariantBasicInfo bI : basicInfoDao.getAllBasicInfo()) {
				LOGGER.info("itemVariantBasicInfo = {}", bI.toString());
			}
			return basicInfoDao.getAllBasicInfo();
		}
	}

	@Override
	public List<ItemVariantDetail> getDetails(Integer itemVariantId) {
		if (detailsDao.getDetails(itemVariantId) == null) {
			LOGGER.error("Error: itemVariant with id = {} don't exist in storage", itemVariantId);
			return null;
		} else {
			LOGGER.info("Read itemVariant with id = {} with details: ", itemVariantId);
			for (ItemVariantDetail vD : detailsDao.getDetails(itemVariantId)) {
				LOGGER.info("itemVariantDetail = {}", vD.toString());
			}
			return detailsDao.getDetails(itemVariantId);
		}
	}

	@Override
	public ItemVariantSpecification getSpecification(Integer itemVariantId) {
		if (basicInfoDao.getBasicInfo(itemVariantId) == null || detailsDao.getDetails(itemVariantId) == null) {
			LOGGER.error("Error: itemVariant with id = {} don't exist in storage", itemVariantId);
			return null;
		} else {
			LOGGER.info("Read itemVariant with id = {} with basic info and details: ", itemVariantId);
			ItemVariantSpecification specification = new ItemVariantSpecification();
			specification.setInfo(basicInfoDao.getBasicInfo(itemVariantId));
			specification.setDetails(detailsDao.getDetails(itemVariantId));
			LOGGER.info("Specification is created: ");
			LOGGER.info("basic info = {} ", basicInfoDao.getBasicInfo(itemVariantId));
			for (ItemVariantDetail vD : detailsDao.getDetails(itemVariantId)) {
				LOGGER.info("detail = {}", vD.toString());
			}
			return specification;
		}
	}
}

package ru.mail.ales2003.deals2017.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ru.mail.ales2003.deals2017.dao.api.ICharacterTypeInItemVariantDao;
import ru.mail.ales2003.deals2017.dao.api.IItemVariantDao;
import ru.mail.ales2003.deals2017.dao.api.customdao.IItemVariantCommonInfoDao;
import ru.mail.ales2003.deals2017.dao.api.customdao.IItemVariantDetailDao;
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
	public IItemVariantCommonInfoDao basicInfoDao;

	@Inject
	public IItemVariantDetailDao detailsDao;

	private String itemVariantClassName = ItemVariant.class.getSimpleName();

	private String attributeClassName = CharacterTypeInItemVariant.class.getSimpleName();

	// ============================ItemVariant management and handling

	// =============READING AREA===============
	@Override
	public ItemVariant getItemVariant(Integer id) {
		if (itemVariantDao.get(id) == null) {
			String errMsg = String.format("[%s] entity with id = [%s] don't exist in storage", itemVariantClassName,
					id);
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg);
		} else {
			ItemVariant entity = itemVariantDao.get(id);
			LOGGER.info("Read one {} entity: {}", itemVariantClassName, entity.toString());
			return entity;
		}
	}

	@Override
	public List<ItemVariant> getAllItemVariants() {
		List<ItemVariant> entities = itemVariantDao.getAll();

		if (entities == null) {
			String errMsg = String.format("[%s] entities storage is epty", itemVariantClassName);
			LOGGER.error("Error: {}", errMsg);
			// throw null;
		}
		LOGGER.info("{} entities storage returns {} entities: ", itemVariantClassName, entities.size());
		LOGGER.info("Read all {} entities: ", itemVariantClassName);
		for (ItemVariant iv : entities) {
			LOGGER.info("{} entity = {}", itemVariantClassName, iv.toString());
		}
		return entities;
	}

	// =============CREATION/UPDATE AREA===============
	@Override
	public void saveItemVariant(ItemVariant entity) {
		if (entity == null) {
			LOGGER.error("Error: as the {} entity was sent a null reference", itemVariantClassName);
			return;
		} else if (entity.getId() == null) {
			itemVariantDao.insert(entity);
			LOGGER.info("Inserted new {} entity: {}", itemVariantClassName, entity.toString());
		} else {
			itemVariantDao.update(entity);
			LOGGER.info("Updated one {} entity: {}", itemVariantClassName, entity.toString());
		}
	}

	@Override
	public void saveItemVariantMultiple(ItemVariant... entityArray) {
		for (ItemVariant entity : entityArray) {
			LOGGER.debug("Inserted new {} entity from array: {}", itemVariantClassName, entity.toString());
			saveItemVariant(entity);
		}
		LOGGER.info("{} entities from array were inserted", itemVariantClassName);
	}

	// =============DELETE AREA===============
	@Override
	public void deleteItemVariant(Integer id) {
		if (id == null) {
			LOGGER.error("Error: as the id was sent a null reference");
			return;
		} else {
			itemVariantDao.delete(id);
			LOGGER.info("Deleted {} entity by id: {}", itemVariantClassName, id);
		}

	}

	// ==============Attributes management and handling in an item variant

	// =============READING AREA===============

	@Override
	public CharacterTypeInItemVariant getAttribute(Integer id) {
		if (attributeDao.get(id) == null) {
			String errMsg = String.format("[%s] entity with id = [%s] don't exist in storage", attributeClassName, id);
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg);
		} else {
			CharacterTypeInItemVariant attribute = attributeDao.get(id);
			LOGGER.info("Read one {} entity: {}", attributeClassName, attribute.toString());
			return attribute;
		}
	}

	@Override
	public List<CharacterTypeInItemVariant> getAllAttributes() {
		LOGGER.info("{} entities storage returns {} entities.", attributeClassName, attributeDao.getAll().size());
		LOGGER.info("Read all {} entities:", attributeClassName);
		for (CharacterTypeInItemVariant a : attributeDao.getAll()) {
			LOGGER.info("{} entity = {}", attributeClassName, a.toString());
		}
		return attributeDao.getAll();
	}

	// =============CREATION AREA===============

	/*
	 * This method saves the characteristic of the ItemVariant using the ready
	 * Dao and classes from Datamodel
	 */
	@Override
	public void saveAttribute(CharacterTypeInItemVariant attribute) {

		if (attribute == null) {
			LOGGER.error("Error: as the {} entity was sent a null reference", attributeClassName);
			return;
		} else if (attribute.getId() == null) {
			attributeDao.insert(attribute);
			LOGGER.info("Inserted new {} entity: {}", attributeClassName, attribute.toString());
		} else {
			attributeDao.update(attribute);
			LOGGER.info("Updated one {} entity: {}", attributeClassName, attribute.toString());
		}
	}

	/*
	 * This method saves list of characteristics of the ItemVariant using the
	 * ready Dao and classes from Datamodel
	 */
	@Override
	public void saveAttributeMultiple(CharacterTypeInItemVariant... attributeArray) {
		for (CharacterTypeInItemVariant attribute : attributeArray) {
			LOGGER.info("Inserted new {} entity from array: {}", attributeClassName, attribute.toString());
			saveAttribute(attribute);
		}
		LOGGER.info("{} entities from array were inserted", attributeClassName);
	}

	// =============DELETE AREA===============
	@Override
	public void deleteAttribute(Integer id) {
		if (id == null) {
			LOGGER.error("Error: as the id was sent a null reference");
			return;
		} else {
			attributeDao.delete(id);
			LOGGER.info("Deleted {} entity by id: {}", attributeClassName, id);
		}
	}

}

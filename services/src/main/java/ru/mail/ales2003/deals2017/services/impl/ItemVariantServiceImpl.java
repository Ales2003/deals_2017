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
import ru.mail.ales2003.deals2017.dao.api.custom.IItemVariantDetailDao;
import ru.mail.ales2003.deals2017.dao.api.custom.entities.ItemVariantBasicInfo;
import ru.mail.ales2003.deals2017.dao.api.custom.entities.ItemVariantDetail;
import ru.mail.ales2003.deals2017.dao.api.custom.entities.ItemVariantSpecification;
import ru.mail.ales2003.deals2017.dao.api.filters.IItemVariantFilter;
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

	private String itemVariantClassName = ItemVariant.class.getSimpleName();

	private String attributeClassName = CharacterTypeInItemVariant.class.getSimpleName();

	// ============================ItemVariant management and handling

	// =============READING AREA===============
	@Override
	public ItemVariant getItemVariant(Integer id) {
		if (itemVariantDao.get(id) == null) {
			String errMsg = String.format("[%s] with id = [%s] don't exist in storage", itemVariantClassName, id);
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg);
		} else {
			ItemVariant entity = itemVariantDao.get(id);
			LOGGER.info("Read one {}: {}", itemVariantClassName, entity.toString());
			return entity;
		}
	}

	@Override
	public List<ItemVariant> getAllItemVariants() {
		LOGGER.info("{} storage returns {} entitys: ", itemVariantClassName, itemVariantDao.getAll().size());
		LOGGER.info("Read all itemVariants:");
		for (ItemVariant iv : itemVariantDao.getAll()) {
			LOGGER.info("itemVariant = {}", iv.toString());
		}
		return itemVariantDao.getAll();
	}

	// =============CREATION/UPDATE AREA===============
	@Override
	public void saveItemVariant(ItemVariant entity) {
		if (entity == null) {
			LOGGER.error("Error: as the {} was sent a null reference", itemVariantClassName);
			return;
		} else if (entity.getId() == null) {
			itemVariantDao.insert(entity);
			LOGGER.info("Inserted new {}: {}", itemVariantClassName, entity.toString());
		} else {
			itemVariantDao.update(entity);
			LOGGER.info("Updated {}: {}", itemVariantClassName, entity.toString());
		}
	}

	@Override
	public void saveItemVariantMultiple(ItemVariant... entityArray) {
		for (ItemVariant entity : entityArray) {
			LOGGER.debug("Inserted new {} from array: {}", itemVariantClassName, entity.toString());
			saveItemVariant(entity);
		}
		LOGGER.info("Inserted {}s from array", itemVariantClassName);
	}

	// =============DELETE AREA===============
	@Override
	public void deleteItemVariant(Integer id) {
		if (id == null) {
			LOGGER.error("Error: as the id was sent a null reference");
			return;
		} else {
			itemVariantDao.delete(id);
			LOGGER.info("Deleted {} by id: {}", itemVariantClassName, id);
		}

	}

	// ==============Attributes management and handling in an item variant

	// =============READING AREA===============

	@Override
	public CharacterTypeInItemVariant getAttribute(Integer id) {
		if (attributeDao.get(id) == null) {
			String errMsg = String.format("[%s] with id = [%s] don't exist in storage", attributeClassName, id);
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg);
		} else {
			CharacterTypeInItemVariant attribute = attributeDao.get(id);
			LOGGER.info("Read one {}: {}", attributeClassName, attribute.toString());
			return attribute;
		}
	}

	@Override
	public List<CharacterTypeInItemVariant> getAllAttributes() {
		LOGGER.info("{} storage returns {} entitys.", attributeClassName, attributeDao.getAll().size());
		LOGGER.info("Read all attributes:");
		for (CharacterTypeInItemVariant a : attributeDao.getAll()) {
			LOGGER.info("attribute = {}", a.toString());
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
			LOGGER.error("Error: as the {} was sent a null reference", attributeClassName);
			return;
		} else if (attribute.getId() == null) {
			attributeDao.insert(attribute);
			LOGGER.info("Inserted new {}: {}", attributeClassName, attribute.toString());
		} else {
			attributeDao.update(attribute);
			LOGGER.info("Updated {}: {}", attributeClassName, attribute.toString());
		}
	}

	/*
	 * This method saves list of characteristics of the ItemVariant using the
	 * ready Dao and classes from Datamodel
	 */
	@Override
	public void saveAttributeMultiple(CharacterTypeInItemVariant... attributeArray) {
		for (CharacterTypeInItemVariant attribute : attributeArray) {
			LOGGER.info("Inserted new {} from array: {}", attributeClassName, attribute.toString());
			saveAttribute(attribute);
		}
		LOGGER.info("Inserted {}s from array", attributeClassName);
	}

	// =============DELETE AREA===============
	@Override
	public void deleteAttribute(Integer id) {
		if (id == null) {
			LOGGER.error("Error: as the id was sent a null reference");
			return;
		} else {
			attributeDao.delete(id);
			LOGGER.info("Deleted {} by id: {}", attributeClassName, id);
		}
	}

	// ========================== CUSTOM CLASSES management and handling

	// =============READING AREA BASED CUSTOM CLASSES ===============
	@Override
	public ItemVariantBasicInfo getBasicInfo(Integer itemVariantId) {
		if (basicInfoDao.getBasicInfo(itemVariantId) == null) {
			LOGGER.error("Error: itemVariant with id = {} don't exist in storage", itemVariantId);
			return null;
		} else {
			LOGGER.info("Read itemVariant with id = {} with basic info: {}", itemVariantId,
					basicInfoDao.getBasicInfo(itemVariantId).toString());
			ItemVariantBasicInfo basicInfo = basicInfoDao.getBasicInfo(itemVariantId);
			return basicInfo;
		}
	}

	@Override
	public List<ItemVariantBasicInfo> getBasicInfoForEach() {
		if (basicInfoDao.getBasicInfoForEach() == null) {
			LOGGER.error("Error: all itemVariants don't exist in storage");
			return null;
		} else {
			LOGGER.info("Read all itemVariants with basic info:");

			for (ItemVariantBasicInfo bI : basicInfoDao.getBasicInfoForEach()) {
				LOGGER.info("itemVariantBasicInfo = {}", bI.toString());
			}
			return basicInfoDao.getBasicInfoForEach();
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

	@Override
	public List<ItemVariantBasicInfo> getFilteredBasicInfo(IItemVariantFilter filter) {
		if (basicInfoDao.getFilteredBasicInfo(filter) == null) {
			LOGGER.error("Error: filteredItemVariant with such parameters is missing in the store. Parameters= {}",
					filter);
			return null;
		} else {
			LOGGER.info("Read filteredItemVariant with filter: {}", filter);
			for (ItemVariantBasicInfo bI : basicInfoDao.getFilteredBasicInfo(filter)) {
				LOGGER.info("filteredItemVariantBasicInfo = {}", bI.toString());
			}
			return basicInfoDao.getFilteredBasicInfo(filter);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ru.mail.ales2003.deals2017.services.IItemVariantService#getSpecification(
	 * ru.mail.ales2003.deals2017.dao.api.filters.IItemVariantFilter)
	 */
	@Override
	public List<ItemVariantSpecification> getFilteredSpecifications(IItemVariantFilter filter) {
		List<ItemVariantSpecification> specifications = new ArrayList<>();
		List<ItemVariantBasicInfo> basicInfos = new ArrayList<>();
		basicInfos = basicInfoDao.getFilteredBasicInfo(filter);
		if (basicInfos == null) {
			LOGGER.error("Error: all filtered item variants don't exist in storage");
			return null;
		} else {
			LOGGER.info("Read all filtered item variants with specifications:");
			ItemVariantSpecification specification = new ItemVariantSpecification();
			for (ItemVariantBasicInfo bI : basicInfos) {
				specification.setInfo(bI);
				specification.setDetails(detailsDao.getDetails(bI.getItemVariantId()));
				LOGGER.info("item variant with specifification = {}", specification.toString());
				specifications.add(specification);
			}
			return specifications;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ru.mail.ales2003.deals2017.services.IItemVariantService#getSpecifications
	 * ()
	 */
	@Override
	public List<ItemVariantSpecification> getSpecifications() {

		List<ItemVariantSpecification> specifications = new ArrayList<>();

		List<ItemVariantBasicInfo> basicInfos = new ArrayList<>();

		basicInfos = basicInfoDao.getBasicInfoForEach();

		if (basicInfos == null) {
			LOGGER.error("Error: all item variants don't exist in storage");
			return null;

		} else {
			LOGGER.info("Read all item variants with specifications:");

			ItemVariantSpecification specification = new ItemVariantSpecification();
			for (ItemVariantBasicInfo bI : basicInfos) {

				specification.setInfo(bI);
				specification.setDetails(detailsDao.getDetails(bI.getItemVariantId()));
				LOGGER.info("item variant with specifification = {}", specification.toString());
				specifications.add(0, specification);
				;
			}
			return specifications;
		}
	}

}

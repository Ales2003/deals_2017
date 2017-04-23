package ru.mail.ales2003.deals2017.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import ru.mail.ales2003.deals2017.datamodel.Attribute;
import ru.mail.ales2003.deals2017.datamodel.CharacterType;
import ru.mail.ales2003.deals2017.datamodel.CharacterTypeInItemVariant;
import ru.mail.ales2003.deals2017.datamodel.Item;
import ru.mail.ales2003.deals2017.datamodel.ItemVariant;
import ru.mail.ales2003.deals2017.datamodel.Measure;

public class ItemVariantServiceTestForAttribute extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemVariantServiceTestForAttribute.class);

	@Inject
	private IItemVariantService service;

	@Inject
	private IItemService itemService;

	@Inject
	private ICharacterTypeService measureService;

	private Item item;
	private Item itemFromDb;

	private ItemVariant itemVariant_1;
	private ItemVariant itemVariant_2;
	private ItemVariant itemVariantFromDb_1;
	private ItemVariant itemVariantFromDb_2;

	private CharacterType measure_1;
	private CharacterType measure_2;
	private CharacterType measureFromDb_1;
	private CharacterType measureFromDb_2;

	private CharacterTypeInItemVariant attribute_1;
	private CharacterTypeInItemVariant attribute_2;
	private CharacterTypeInItemVariant attributeFromDb_1;
	private CharacterTypeInItemVariant attributeFromDb_2;
	private CharacterTypeInItemVariant modifiedAttribute;

	@Before
	public void runBeforeTestMethod() {

		TestAssistant.createInstance("s");

		LOGGER.debug("Start preparation of the method");

		LOGGER.debug("Creating item in Db");
		item = new Item();
		itemService.save(item);
		itemFromDb = itemService.get(item.getId());
		LOGGER.debug("Item in Db was created with id={}", itemFromDb.getId());

		LOGGER.debug("Creating itemVariants in Db");
		itemVariant_1 = getItemVariantInstance(itemFromDb.getId(), new BigDecimal("250.00"));
		itemVariant_2 = getItemVariantInstance(itemFromDb.getId(), new BigDecimal("350.00"));
		service.saveItemVariant(itemVariant_1);
		service.saveItemVariant(itemVariant_2);
		itemVariantFromDb_1 = service.getItemVariant(itemVariant_1.getId());
		itemVariantFromDb_2 = service.getItemVariant(itemVariant_2.getId());
		LOGGER.debug("ItemVariants in Db were created with id={} and id={}", itemVariantFromDb_1.getId(),
				itemVariantFromDb_2.getId());

		LOGGER.debug("Creating characterTypes in Db");
		measure_1 = getMeasureInstance(Measure.KG);
		measure_2 = getMeasureInstance(Measure.M);
		measureService.save(measure_1);
		measureService.save(measure_2);
		measureFromDb_1 = measureService.get(measure_1.getId());
		measureFromDb_2 = measureService.get(measure_2.getId());
		LOGGER.debug("CharacterTypes in Db were created with id={} and id={}", itemVariantFromDb_1.getId(),
				itemVariantFromDb_2.getId());

		LOGGER.debug("Creating CharacterTypeInItemVariant in JVM");
		attribute_1 = getAttributeInstance(itemVariantFromDb_1.getId(), Attribute.WEIGHT, "10",
				measureFromDb_1.getId());
		attribute_2 = getAttributeInstance(itemVariantFromDb_2.getId(), Attribute.LENGTH, "100",
				measureFromDb_2.getId());
		LOGGER.debug("CharacterTypeInItemVariants in JVM were created");

		LOGGER.debug("Finish preparation of the method");
	}

	@After
	public void runAfterTestMethod() {

		TestAssistant.deleteInstance();

		LOGGER.debug("Start completion of the method");

		LOGGER.debug("Start deleting characterTypeInItemVariants from Db");
		for (CharacterTypeInItemVariant attribute : service.getAllAttributes()) {
			deleteAttributeFromDb(attribute.getId());
		}
		LOGGER.debug("CharacterTypeInItemVariants were deleted from Db ");

		LOGGER.debug("Start deleting characterTypes from Db");
		for (CharacterType measure : measureService.getAll()) {
			measureService.delete(measure.getId());
		}
		LOGGER.debug("CharacterTypes were deleted from Db ");

		LOGGER.debug("Start deleting itemVariants from Db");
		for (ItemVariant iV : service.getAllItemVariants()) {
			service.deleteItemVariant(iV.getId());
		}
		LOGGER.debug("ItemVariants were deleted from Db ");

		LOGGER.debug("Start deleting items from Db");
		for (Item i : itemService.getAll()) {
			itemService.delete(i.getId());
		}
		LOGGER.debug("Items were deleted from Db ");

		LOGGER.debug("Finish completion of the method");
	}

	/*
	 * Two objects with the same Id are compared: created in Java and saved in &
	 * extracted from the database
	 */
	@Test
	public void insertAttributeTest() {
		LOGGER.debug("Start insertAttributeTest method");
		service.saveAttribute(attribute_1);
		attributeFromDb_1 = service.getAttribute(attribute_1.getId());

		Assert.notNull(attributeFromDb_1, "instance must be saved");

		Assert.isTrue(
				(attributeFromDb_1.getItemVariantId() != null) && (attributeFromDb_1.getAttribute() != null)
						&& (attributeFromDb_1.getValue() != null) && (attributeFromDb_1.getCharacterTypeId() != null),
				"columns values must not by empty");

		Assert.isTrue(attributeFromDb_1.equals(attribute_1), "values of the corresponding columns must by eq.");
		LOGGER.debug("Finish insertAttributeTest method");
	}

	/*
	 * Test for the insertion of several objects, for each are compared two
	 * objects with the same Id: created in Java and saved in & extracted from
	 * the database
	 */

	@Test
	public void insertAttributeMultipleTest() {

		LOGGER.debug("Start insertAttributeMultipleTest method");

		service.saveAttributeMultiple(attribute_1, attribute_2);
		attributeFromDb_1 = service.getAttribute(attribute_1.getId());
		attributeFromDb_2 = service.getAttribute(attribute_2.getId());

		Assert.notNull(attributeFromDb_1, "attribute_1 must be saved");
		Assert.notNull(attributeFromDb_2, "attribute_2 must be saved");

		Assert.isTrue(
				(attributeFromDb_1.getItemVariantId() != null) && (attributeFromDb_1.getAttribute() != null)
						&& (attributeFromDb_1.getValue() != null) && (attributeFromDb_1.getCharacterTypeId() != null),
				"columns values must not by empty");

		Assert.isTrue(
				(attributeFromDb_2.getItemVariantId() != null) && (attributeFromDb_2.getAttribute() != null)
						&& (attributeFromDb_2.getValue() != null) && (attributeFromDb_2.getCharacterTypeId() != null),
				"columns values must not by empty");

		Assert.isTrue(attributeFromDb_1.equals(attribute_1), "values of the corresponding columns must by eq.");

		Assert.isTrue(attributeFromDb_2.equals(attribute_2), "values of the corresponding columns must by eq.");

		LOGGER.debug("Finish  insertAttributeMultipleTest method");
	}

	/*
	 * Three objects with the same Id are compared: created in Java, modified in
	 * Java and saved in & extracted from the database
	 */
	@Test
	public void updateAttributeTest() {
		LOGGER.debug("Start updateAttributeTest method");
		service.saveAttribute(attribute_1);
		modifiedAttribute = service.getAttribute(attribute_1.getId());
		modifiedAttribute.setItemVariantId(itemVariantFromDb_2.getId());
		modifiedAttribute.setAttribute(Attribute.HEIGHT);
		modifiedAttribute.setValue("200");
		modifiedAttribute.setCharacterTypeId(measure_2.getId());
		service.saveAttribute(modifiedAttribute);

		attributeFromDb_1 = service.getAttribute(modifiedAttribute.getId());

		Assert.isTrue((attribute_1.getId().equals(modifiedAttribute.getId())),
				"id of initial instance must by eq. to modified instance id");

		Assert.isTrue(
				!(attribute_1.getItemVariantId().equals(modifiedAttribute.getItemVariantId()))
						&& !(attribute_1.getAttribute().equals(modifiedAttribute.getAttribute()))
						&& !(attribute_1.getValue().equals(modifiedAttribute.getValue()))
						&& !(attribute_1.getCharacterTypeId().equals(modifiedAttribute.getCharacterTypeId())),
				"values of the corresponding columns of initial and modified instances must not by eq.");

		Assert.isTrue((attributeFromDb_1.equals(modifiedAttribute)), "values of the corresponding columns must by eq.");

		LOGGER.debug("Finish  updateAttributeTest method");
	}

	/*
	 * Test for the getting an object. Two objects with the same Id are
	 * compared: created in Java and saved in & extracted from the database
	 */
	@Test
	public void getAttributeTest() {
		LOGGER.debug("Start getAttributeTest method");
		service.saveAttribute(attribute_1);
		attributeFromDb_1 = service.getAttribute(attribute_1.getId());

		Assert.notNull(attributeFromDb_1, "instance must be saved");

		Assert.isTrue(
				(attributeFromDb_1.getItemVariantId() != null) && (attributeFromDb_1.getAttribute() != null)
						&& (attributeFromDb_1.getValue() != null) && (attributeFromDb_1.getCharacterTypeId() != null),
				"columns values must not by empty");

		Assert.isTrue(attributeFromDb_1.equals(attribute_1), "values of the corresponding columns must by eq.");

		LOGGER.debug("Finish  getAttributeTest method");
	}

	/*
	 * Test for the getting of several objects, for each are compared two
	 * objects with the same Id: created in Java and saved in & extracted from
	 * the database
	 */
	@Test
	public void getAllAttributeTest() {
		LOGGER.debug("Start getAllAttributeTest method");
		List<CharacterTypeInItemVariant> instances = new ArrayList<>();
		instances.add(attribute_1);
		instances.add(attribute_2);
		service.saveAttributeMultiple(attribute_1, attribute_2);
		List<CharacterTypeInItemVariant> instancesFromDb = service.getAllAttributes();
		Assert.isTrue(instances.size() == instancesFromDb.size(),
				"count of from Db instances must by eq. to count of inserted instances");

		for (int i = 0; i < instances.size(); i++) {
			// False if compared to ==, since the references to objects
			Assert.isTrue(instances.get(i).getId().equals(instancesFromDb.get(i).getId()),
					"id of every instance from Db must by eq. to appropriate prepared instance id");

			Assert.isTrue(instances.get(i).equals(instancesFromDb.get(i)),
					"column's values of every instance from Db must by eq. to appropriate prepared instance's column's values");
		}
		LOGGER.debug("Finish getAllAttributeTest method");
	}

	/*
	 * Test for the deleting. One object is created, saved in DB and deleted.
	 * Then the object is checked for absence in the database
	 */
	@Test(expected = IllegalArgumentException.class)
	public void deleteAttributeTest() {
		LOGGER.debug("Start deleteAttributeTest method");
		service.saveAttribute(attribute_1);
		attributeFromDb_1 = service.getAttribute(attribute_1.getId());
		deleteAttributeFromDb(attribute_1.getId());
		Assert.notNull(itemVariantFromDb_1, "instance must be saved");
		Assert.isNull(service.getAttribute(attribute_1.getId()), "instance must be deleted");
		LOGGER.debug("Finish deleteAttributeTest method");
	}

	// =============To do tests for attributDao===============

	/*
	 * method creates a new ItemVariant instance & gives it args
	 */
	private ItemVariant getItemVariantInstance(Integer itemId, BigDecimal variantPrice) {
		ItemVariant instance = new ItemVariant();
		instance.setItemId(itemId);
		instance.setVariantPrice(variantPrice);
		return instance;
	}

	/*
	 * method creates a new CharacterTypeInItemVariant instance & gives it args
	 */

	private CharacterTypeInItemVariant getAttributeInstance(Integer itemVariantId, Attribute attribute, String value,
			Integer characterTypeId) {
		CharacterTypeInItemVariant instance = new CharacterTypeInItemVariant();
		instance.setItemVariantId(itemVariantId);
		instance.setAttribute(attribute);
		instance.setValue(value);
		instance.setCharacterTypeId(characterTypeId);
		return instance;
	}

	/*
	 * method creates a new CharacterType instance & gives it args
	 */
	private CharacterType getMeasureInstance(Measure name) {
		CharacterType instance = new CharacterType();
		instance.setName(name);
		return instance;
	}

	/*
	 * method deletes an instance by id
	 */
	private void deleteAttributeFromDb(Integer id) {
		service.deleteAttribute(id);
	}

}

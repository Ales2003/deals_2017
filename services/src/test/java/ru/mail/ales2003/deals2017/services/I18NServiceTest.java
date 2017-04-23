package ru.mail.ales2003.deals2017.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import ru.mail.ales2003.deals2017.datamodel.CharacterType;
import ru.mail.ales2003.deals2017.datamodel.I18N;
import ru.mail.ales2003.deals2017.datamodel.Item;
import ru.mail.ales2003.deals2017.datamodel.Language;
import ru.mail.ales2003.deals2017.datamodel.Measure;
import ru.mail.ales2003.deals2017.datamodel.Table;

public class I18NServiceTest extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(I18NServiceTest.class);

	@Inject
	private II18NService service;

	@Inject
	private ICharacterTypeService characterTypeService;

	@Inject
	private IItemService itemService;

	private I18N instance_1;
	private I18N instance_2;
	private I18N instance_1FromDb;
	private I18N instance_2FromDb;
	private I18N modifiedInstance;

	private CharacterType characterType;
	private CharacterType characterTypeFromDb;

	private Item item;
	private Item itemFromDb;

	@Before
	public void runBeforeTestMethod() {

		LOGGER.debug("Creating customerGroup in Db");
		characterType = new CharacterType();
		characterType.setName(Measure.MM);
		characterTypeService.save(characterType);
		characterTypeFromDb = characterTypeService.get(characterType.getId());
		LOGGER.debug("CharacterType was created with id={}", characterType.getId());

		LOGGER.debug("Creating item in Db");
		item = new Item();
		item.setName("Table");
		itemService.save(item);
		itemFromDb = itemService.get(item.getId());
		LOGGER.debug("Item was created with id={}", itemFromDb.getId());

		LOGGER.debug("Start preparation of the method");
		instance_1 = getInstance(Table.CHARACTER_TYPE, characterTypeFromDb.getId(), Language.RU, "\u043C\u043C");
		instance_2 = getInstance(Table.CHARACTER_TYPE, characterTypeFromDb.getId(), Language.EN, "mm");
		LOGGER.debug("Finish preparation of the method");
	}

	@After
	public void runAfterTestMethod() {

		LOGGER.debug("Start completion of the method");

		LOGGER.debug("Start deleting i18nentitys from Db");
		for (I18N entity : service.getAll()) {
			deleteFromDb(entity.getId());
		}
		LOGGER.debug("I18nentitys were deleted from Db");

		LOGGER.debug("Start deleting characterTypes from Db");
		for (CharacterType cT : characterTypeService.getAll()) {
			characterTypeService.delete(cT.getId());
		}
		LOGGER.debug("CharacterTypes were deleted from Db ");

		LOGGER.debug("Finish completion of the method");
	}

	/*
	 * Two objects with the same Id are compared: created in Java and extracted
	 * from the database
	 */
	@Test
	public void insertTest() {
		LOGGER.debug("Start insertTest method");
		service.save(instance_1);
		I18N instance_1FromDb = service.get(instance_1.getId());

		Assert.notNull(instance_1FromDb, "i18n entity must be saved");

		Assert.isTrue(
				instance_1FromDb.getTableName() != null && instance_1FromDb.getMemberId() != null
						&& instance_1FromDb.getLanguage() != null && instance_1FromDb.getValue() != null,
				"columns must not by empty");

		Assert.isTrue(
				instance_1FromDb.getTableName().equals(instance_1.getTableName())
						&& instance_1FromDb.getMemberId().equals(instance_1.getMemberId())
						&& instance_1FromDb.getLanguage().equals(instance_1.getLanguage())
						&& instance_1FromDb.getValue().equals(instance_1.getValue()),
				"values of the corresponding columns must by eq.");

		LOGGER.debug("Finish insertTest method");
	}

	/*
	 * Test for the insertion of several objects, for each are compared two
	 * objects with the same Id: created in Java and saved in & extracted from
	 * the database
	 */
	@Test
	public void insertMultipleTest() {

		LOGGER.debug("Start insertMultipleTest method");

		service.saveMultiple(instance_1, instance_2);
		instance_1FromDb = service.get(instance_1.getId());
		instance_2FromDb = service.get(instance_2.getId());

		Assert.notNull(instance_1FromDb, "instance_1 must be saved");
		Assert.notNull(instance_2FromDb, "instance_2 must be saved");

		Assert.isTrue(instance_1FromDb.getTableName() != null && instance_1FromDb.getTableName() != null
				&& instance_1FromDb.getMemberId() != null && instance_1FromDb.getLanguage() != null
				&& instance_1FromDb.getValue() != null, "columns must not by empty");

		Assert.isTrue(
				instance_2FromDb.getTableName() != null && instance_2FromDb.getMemberId() != null
						&& instance_2FromDb.getLanguage() != null && instance_2FromDb.getValue() != null,
				"columns must not by empty");

		Assert.isTrue(
				instance_1FromDb.getTableName().equals(instance_1.getTableName())
						&& instance_1FromDb.getMemberId().equals(instance_1.getMemberId())
						&& instance_1FromDb.getLanguage().equals(instance_1.getLanguage())
						&& instance_1FromDb.getValue().equals(instance_1.getValue()),
				"values of the corresponding columns must by eq.");

		Assert.isTrue(
				instance_1FromDb.getTableName().equals(instance_2.getTableName())
						&& instance_2FromDb.getMemberId().equals(instance_2.getMemberId())
						&& instance_2FromDb.getLanguage().equals(instance_2.getLanguage())
						&& instance_2FromDb.getValue().equals(instance_2.getValue()),
				"values of the corresponding columns must by eq.");

		LOGGER.debug("Finish  insertMultipleTest method");
	}

	/*
	 * Three objects with the same Id are compared: created in Java, modified in
	 * Java and saved in & extracted from the database
	 */
	@Test
	public void updateTest() {
		LOGGER.debug("Start updateTest method");
		service.save(instance_1);
		modifiedInstance = service.get(instance_1.getId());

		modifiedInstance.setTableName(Table.ITEM);
		modifiedInstance.setMemberId(itemFromDb.getId());
		modifiedInstance.setLanguage(Language.DE);
		modifiedInstance.setValue("Tisch");

		service.save(modifiedInstance);

		instance_1FromDb = service.get(modifiedInstance.getId());

		Assert.isTrue((instance_1.getId().equals(modifiedInstance.getId())),
				"id of initial instance must by eq. to modified instance id");

		// ItemFromDb and characterTypeFromDb can have some Id, because lay in
		// different Db tables.
		// Also they are not compared.
		Assert.isTrue(
				!(instance_1.getTableName().equals(modifiedInstance.getTableName()))
						&& !(instance_1.getLanguage().equals(modifiedInstance.getLanguage()))
						&& !(instance_1.getValue().equals(modifiedInstance.getValue())),
				"values of the corresponding columns of initial and modified instances must not by eq.");

		Assert.isTrue((instance_1FromDb.getId().equals(modifiedInstance.getId())),
				"id of instance from Db must by eq. to id of  modified instances");

		Assert.isTrue(
				instance_1FromDb.getTableName().equals(modifiedInstance.getTableName())
						&& instance_1FromDb.getLanguage().equals(modifiedInstance.getLanguage())
						&& instance_1FromDb.getValue().equals(modifiedInstance.getValue()),
				"values of the corresponding columns of instance from Db and modified instances must by eq.");

		LOGGER.debug("Finish  updateTest method");
	}

	/*
	 * Test for the getting an object. Two objects with the same Id are
	 * compared: created in Java and saved in & extracted from the database
	 */
	@Test
	public void getTest() {
		LOGGER.debug("Start getTest method");
		service.save(instance_1);
		instance_1FromDb = service.get(instance_1.getId());
		Assert.notNull(instance_1FromDb, "instance must be saved");

		Assert.isTrue(
				(instance_1FromDb.getTableName() != null) && (instance_1FromDb.getMemberId() != null)
						&& (instance_1FromDb.getLanguage() != null) && (instance_1FromDb.getValue() != null),
				"columns values must not by empty");

		Assert.isTrue(
				instance_1FromDb.getTableName().equals(instance_1.getTableName())
						&& instance_1FromDb.getMemberId().equals(instance_1.getMemberId())
						&& instance_1FromDb.getLanguage().equals(instance_1.getLanguage())
						&& instance_1FromDb.getValue().equals(instance_1.getValue()),
				"values of the corresponding columns must by eq.");

		LOGGER.debug("Finish  getTest method");
	}

	/*
	 * Test for the getting of several objects, for each are compared two
	 * objects with the same Id: created in Java and saved in & extracted from
	 * the database
	 */
	@Test
	public void getAllTest() {
		LOGGER.debug("Start getAllTest method");
		List<I18N> instances = new ArrayList<>();
		instances.add(instance_1);
		instances.add(instance_2);
		service.saveMultiple(instance_1, instance_2);
		List<I18N> instancesFromDb = service.getAll();
		Assert.isTrue(instances.size() == instancesFromDb.size(),
				"count of from Db instances must by eq. to count of inserted instances");

		for (int i = 0; i < instances.size(); i++) {
			// False if compared to ==, since the references to objects
			Assert.isTrue(instances.get(i).getId().equals(instancesFromDb.get(i).getId()),
					"id of every instance from Db must by eq. to appropriate prepared instance id");

			Assert.isTrue(
					instances.get(i).getTableName().equals(instancesFromDb.get(i).getTableName())
							&& instances.get(i).getMemberId().equals(instancesFromDb.get(i).getMemberId())
							&& instances.get(i).getLanguage().equals(instancesFromDb.get(i).getLanguage())
							&& instances.get(i).getValue().equals(instancesFromDb.get(i).getValue()),
					"column's values of every instance from Db must by eq. to appropriate prepared instance's column's values");
		}
		LOGGER.debug("Finish getAllTest method");
	}

	/*
	 * Test for the deleting. One object is created, saved in DB and deleted.
	 * Then the object is checked for absence in the database
	 */
	@Test(expected = IllegalArgumentException.class)
	public void deleteTest() {
		LOGGER.debug("Start deleteTest method");
		service.save(instance_1);
		I18N typeFromDb = service.get(instance_1.getId());
		Assert.notNull(typeFromDb, "instance must be saved");
		deleteFromDb(instance_1.getId());
		// here it is expected the IllegalArgumentException.
		Assert.isNull(service.get(instance_1.getId()), "instance must be deleted");
		LOGGER.debug("Finish deleteTest method");
	}

	/*
	 * method creates a new instance & gives it args
	 */
	private I18N getInstance(Table table, Integer memberId, Language lang, String value) {
		I18N instance = new I18N();
		instance.setTableName(table);
		instance.setMemberId(memberId);
		instance.setLanguage(lang);
		instance.setValue(value);
		return instance;
	}

	/*
	 * method deletes an instance by id
	 */
	private void deleteFromDb(Integer id) {
		service.delete(id);
	}

}

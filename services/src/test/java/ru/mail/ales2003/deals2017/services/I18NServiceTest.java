package ru.mail.ales2003.deals2017.services;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import ru.mail.ales2003.deals2017.datamodel.I18N;
import ru.mail.ales2003.deals2017.datamodel.Language;
import ru.mail.ales2003.deals2017.datamodel.Table;

public class I18NServiceTest extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(I18NServiceTest.class);

	@Inject
	private II18NService service;

	private I18N instance_1;
	private I18N instance_2;
	private I18N instance_1FromDb;
	private I18N instance_2FromDb;
	private I18N modifiedInstance;

	@Before
	public void runBeforeTestMethod() {
		LOGGER.debug("Start preparation of the method");
		instance_1 = getInstance(Table.CHARACTER_TYPE, 48, Language.RU, "mm");
		instance_2 = getInstance(Table.CHARACTER_TYPE2ITEM_VARIANT, 50, Language.RU, "kg");
		LOGGER.debug("Finish preparation of the method");
	}

	@After
	public void runAfterTestMethod() {
		// LOGGER.debug("Start completion of the method");
		// for (Manager cg : service.getAll()) {
		// deleteFromDb(cg.getId());
		// }
		LOGGER.debug("Finish completion of the method");
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
				(instance_1FromDb.getTable() != null) && (instance_1FromDb.getEntityId() != null)
						&& (instance_1FromDb.getLanguage() != null) && (instance_1FromDb.getValue() != null),
				"columns values must not by empty");

		Assert.isTrue(
				instance_1FromDb.getTable().equals(instance_1.getTable())
						&& instance_1FromDb.getEntityId().equals(instance_1.getEntityId())
						&& instance_1FromDb.getLanguage().equals(instance_1.getLanguage())
						&& instance_1FromDb.getValue().equals(instance_1.getValue()),
				"values of the corresponding columns must by eq.");

		LOGGER.debug("Finish  getTest method");
	}

	private I18N getInstance(Table table, Integer id, Language lang, String value) {
		I18N instance = new I18N();
		instance.setTable(table);
		instance.setId(id);
		instance.setLanguage(lang);
		instance.setValue(value);
		return instance;
	}

}

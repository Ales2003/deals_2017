package ru.mail.ales2003.deals2017.services;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import ru.mail.ales2003.deals2017.datamodel.CustomerGroup;
import ru.mail.ales2003.deals2017.datamodel.I18N;
import ru.mail.ales2003.deals2017.datamodel.Language;
import ru.mail.ales2003.deals2017.datamodel.Table;

public class I18NServiceTest extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(I18NServiceTest.class);

	@Inject
	private II18NService service;

	@Inject
	private ICustomerGroupService customerGroupService;

	private I18N instance_1;
	private I18N instance_2;
	private I18N instance_1FromDb;
	private I18N instance_2FromDb;
	private I18N modifiedInstance;

	private CustomerGroup customerGroup;
	private CustomerGroup customerGroupFromDb;

	@Before
	public void runBeforeTestMethod() {

		LOGGER.debug("Creating customerGroup in Db");
		customerGroup = new CustomerGroup();
		customerGroupService.save(customerGroup);
		customerGroupFromDb = customerGroupService.get(customerGroup.getId());
		LOGGER.debug("CustomerGroup was created with id={}", customerGroup.getId());

		LOGGER.debug("Start preparation of the method");
		instance_1 = getInstance(Table.CUSTOMER_GROUP, customerGroupFromDb.getId(), Language.RU,
				customerGroupFromDb.getName().name());
		instance_2 = getInstance(Table.CUSTOMER_GROUP, customerGroupFromDb.getId(), Language.RU,
				customerGroupFromDb.getName().name());
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

		LOGGER.debug("Start deleting customerGroups from Db");
		for (CustomerGroup cG : customerGroupService.getAll()) {
			customerGroupService.delete(cG.getId());
		}
		LOGGER.debug("CustomerGroups were deleted from Db ");

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

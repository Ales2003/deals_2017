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

import ru.mail.ales2003.deals2017.datamodel.CustomerGroup;
import ru.mail.ales2003.deals2017.datamodel.CustomerType;

public class CustomerGroupServiceTest extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerGroupServiceTest.class);

	@Inject
	private ICustomerGroupService service;

	private CustomerGroup group_1;
	private CustomerGroup group_2;

	@Before
	public void runBeforeTestMethod() {
		LOGGER.debug("Start preparation of the method");
		group_1 = getInstance(CustomerType.INDIVIDUAL);
		group_2 = getInstance(CustomerType.COMPANY);
		LOGGER.debug("Finish preparation of the method");
	}

	@After
	public void runAfterTestMethod() {
		LOGGER.debug("Start completion of the method");
		for (CustomerGroup cg : service.getAll()) {
			deleteFromDb(cg.getId());
		}
		LOGGER.debug("Finish completion of the method");
	}

	/*
	 * Two objects with the same Id are compared: created in Java and saved in &
	 * extracted from the database
	 */
	@Test
	public void insertTest() {
		LOGGER.debug("Start insertTest method");
		service.save(group_1);
		CustomerGroup groupFromDb = service.get(group_1.getId());
		Assert.notNull(groupFromDb, "group must be saved");
		Assert.notNull(groupFromDb.getName(), "'name' column must not by empty");
		Assert.isTrue(groupFromDb.getName().equals(group_1.getName()), "name from Db must by eq. to prepared name");
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
		service.saveMultiple(group_1, group_2);
		CustomerGroup group_1FromDb = service.get(group_1.getId());
		CustomerGroup group_2FromDb = service.get(group_2.getId());
		Assert.notNull(group_1FromDb, "group_1 must be saved");
		Assert.notNull(group_2FromDb, "group_2 must be saved");
		Assert.notNull(group_1FromDb.getName(), "'name' column must not by empty");
		Assert.notNull(group_2FromDb.getName(), "'name' column must not by empty");
		Assert.isTrue(group_1FromDb.getName().equals(group_1.getName()), "name from Db must by eq. to prepared name");
		Assert.isTrue(group_2FromDb.getName().equals(group_2.getName()), "name from Db must by eq. to prepared name");
		LOGGER.debug("Finish  insertMultipleTest method");
	}

	/*
	 * Three objects with the same Id are compared: created in Java, modified in
	 * Java and saved in & extracted from the database
	 */
	@Test
	public void updateTest() {
		LOGGER.debug("Start updateTest method");
		service.save(group_1);
		CustomerGroup modifiedGroup = service.get(group_1.getId());
		modifiedGroup.setName(CustomerType.COMPANY);
		service.save(modifiedGroup);
		CustomerGroup groupFromDb = service.get(modifiedGroup.getId());
		Assert.isTrue((group_1.getId().equals(modifiedGroup.getId())),
				"id of initial group must by eq. to modified group id");
		Assert.isTrue(!(group_1.getName().equals(modifiedGroup.getName())),
				"name of initial and modified groups must not by eq.");
		Assert.isTrue((groupFromDb.getId().equals(modifiedGroup.getId())), "id from Db must by eq. to modified id");
		Assert.isTrue(groupFromDb.getName().equals(modifiedGroup.getName()),
				"name from Db must by eq. to modified name");
		LOGGER.debug("Finish  updateTest method");
	}

	/*
	 * Test for the getting an object. Two objects with the same Id are
	 * compared: created in Java and saved in & extracted from the database
	 */
	@Test
	public void getTest() {
		LOGGER.debug("Start getTest method");
		service.save(group_1);
		CustomerGroup groupFromDb = service.get(group_1.getId());
		Assert.notNull(groupFromDb, "group must be saved");
		Assert.notNull(groupFromDb.getName(), "'name' column must not by empty");
		Assert.isTrue(groupFromDb.getName().equals(group_1.getName()),
				"name of group from Db must by eq. to prepared groups name");
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
		List<CustomerGroup> groups = new ArrayList<>();
		groups.add(group_1);
		groups.add(group_2);
		service.saveMultiple(group_1, group_2);
		List<CustomerGroup> groupsFromDb = service.getAll();
		Assert.isTrue(groups.size() == groupsFromDb.size(),
				"count of from Db groups must by eq. to count of inserted groups");

		for (int i = 0; i < groups.size(); i++) {
			// False if compared to ==, since the references to objects
			Assert.isTrue(groups.get(i).getId().equals(groupsFromDb.get(i).getId()),
					"id of every group from Db must by eq. to appropriate prepared group id");
			Assert.isTrue(groups.get(i).getName().equals(groupsFromDb.get(i).getName()),
					"name of every group from Db must by eq. to appropriate prepared groups name");
		}
		LOGGER.debug("Finish getAllTest method");
	}

	/*
	 * Test for the deleting. One object is created, saved in DB and deleted.
	 * Then the object is checked for absence in the database
	 */
	@Test
	public void deleteTest() {
		LOGGER.debug("Start deleteTest method");
		service.save(group_1);
		CustomerGroup groupFromDb = service.get(group_1.getId());
		deleteFromDb(group_1.getId());
		Assert.notNull(groupFromDb, "group must be saved");
		Assert.isNull(service.get(group_1.getId()), "group must be deleted");
		LOGGER.debug("Finish deleteTest method");
	}

	/*
	 * method creates a new instance & gives it name
	 */
	private CustomerGroup getInstance(CustomerType name) {
		CustomerGroup instance = new CustomerGroup();
		instance.setName(name);
		return instance;
	}

	/*
	 * method deletes an instance by id
	 */
	private void deleteFromDb(Integer id) {
		service.delete(id);
	}
}

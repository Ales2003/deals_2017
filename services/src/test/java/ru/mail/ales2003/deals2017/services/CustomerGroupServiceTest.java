package ru.mail.ales2003.deals2017.services;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.util.Assert;

import ru.mail.ales2003.deals2017.datamodel.CustomerGroup;
import ru.mail.ales2003.deals2017.datamodel.CustomerType;

public class CustomerGroupServiceTest extends AbstractTest {

	@Inject
	private ICustomerGroupService service;

	@Ignore
	@Before
	public void runBeforeTestMethod() {
		System.out.println("@Before - runBeforeTestMethod");

	}

	@Ignore
	@After
	public void runAfterTestMethod() {
		// to do delete Manager & CustomerGroup
		System.out.println("@After - runAfterTestMethod");
	}

	// method creates a new instance
	private CustomerGroup getInstance() {
		CustomerGroup instance = new CustomerGroup();
		return instance;
	}

	// method deletes an instance by id
	private void deleteFromDb(Integer id) {
		service.delete(id);
	}

	/*
	 * Two objects with the same Id are compared: created in Java and extracted
	 * from the database
	 */
	@Test
	public void insertTest() {

		CustomerGroup group = getInstance();
		group.setName(CustomerType.INDIVIDUAL);

		service.save(group);

		CustomerGroup groupFromDb = service.get(group.getId());

		Assert.notNull(groupFromDb, "customerGroup must be saved");

		Assert.notNull(groupFromDb.getName(), "'name' column must not by empty");

		Assert.isTrue(groupFromDb.getName().equals(group.getName()), "name from Db must by eq. to prepared name");

		deleteFromDb(group.getId());

		/*
		 * System.out.println(
		 * "======================insertTest()===========================");
		 * System.out.println("creating a new customerGroup");
		 * 
		 * CustomerGroup group = new CustomerGroup();
		 * group.setName(CustomerType.INDIVIDUAL);
		 * 
		 * System.out.println("customerGroup after creating: " + group);
		 * System.out.println("saving the group in DB");
		 * 
		 * customerGroupDao.insert(group);
		 * 
		 * System.out.println("group after save in DB: " + group);
		 * 
		 * Integer savedGroupId = group.getId(); CustomerGroup groupFromDB =
		 * customerGroupDao.get(savedGroupId);
		 * 
		 * System.out.println("testing");
		 * 
		 * Assert.notNull(groupFromDB, "customerGroup must be saved");
		 * 
		 * Assert.notNull(groupFromDB.getName(),
		 * "'name' column must not by empty");
		 * 
		 * Assert.isTrue(groupFromDB.getName().equals(group.getName()),
		 * "name from DB must by eq. to prepared name");
		 * 
		 * System.out.println("delete the customerGroup from DB by id= " +
		 * savedGroupId);
		 * 
		 * customerGroupDao.delete(savedGroupId); CustomerGroup deletedGroup =
		 * customerGroupDao.get(savedGroupId);
		 * 
		 * System.out.println("customerGroup after delete: " + deletedGroup);
		 */

	}

	/*
	 * Test for the insertion of several objects, for each are compared two
	 * objects with the same Id: created in Java and extracted from the database
	 */
	@Test
	public void insertMultipleTest() {

		CustomerGroup group_1 = getInstance();
		group_1.setName(CustomerType.INDIVIDUAL);

		CustomerGroup group_2 = getInstance();
		group_2.setName(CustomerType.COMPANY);

		service.saveMultiple(group_1, group_2);

		CustomerGroup group_1FromDb = service.get(group_1.getId());
		CustomerGroup group_2FromDb = service.get(group_2.getId());

		Assert.notNull(group_1FromDb, "customerGroup_1 must be saved");
		Assert.notNull(group_2FromDb, "customerGroup_2 must be saved");

		Assert.notNull(group_1FromDb.getName(), "'name' column must not by empty");
		Assert.notNull(group_2FromDb.getName(), "'name' column must not by empty");

		Assert.isTrue(group_1FromDb.getName().equals(group_1.getName()), "name from Db must by eq. to prepared name");
		Assert.isTrue(group_1FromDb.getName().equals(group_1.getName()), "name from Db must by eq. to prepared name");

		deleteFromDb(group_1.getId());
		deleteFromDb(group_2.getId());

	}

	/*
	 * Three objects with the same Id are compared: created in Java, modified in
	 * Java and extracted from the database
	 */

	@Test
	public void updateTest() {

		CustomerGroup group = getInstance();
		group.setName(CustomerType.INDIVIDUAL);

		service.save(group);

		CustomerGroup modifiedGroup = service.get(group.getId());

		modifiedGroup.setName(CustomerType.COMPANY);

		service.save(modifiedGroup);

		CustomerGroup groupFromDb = service.get(modifiedGroup.getId());

		/*
		 * Assert.notNull(groupFromDb, "customerGroup must be saved");
		 * 
		 * Assert.notNull(groupFromDb.getName(),
		 * "'name' column must not by empty");
		 */
		Assert.isTrue(!(group.getName().equals(modifiedGroup.getName())),
				"name of initial and modified objects must not by eq.");

		Assert.isTrue(groupFromDb.getName().equals(modifiedGroup.getName()),
				"name from Db must by eq. to modified name");

		deleteFromDb(group.getId());
		/*
		 * final CustomerGroup afterSave;
		 * 
		 * System.out.println(
		 * "======================updateTest()===========================");
		 * System.out.println("creating new customerGroup");
		 * 
		 * CustomerGroup group = new CustomerGroup();
		 * group.setName(CustomerType.INDIVIDUAL);
		 * 
		 * System.out.println("customerGroup after creating: " + group);
		 * System.out.println("saving the group in DB");
		 * 
		 * customerGroupDao.insert(group);
		 * 
		 * System.out.println("group after save in DB: " + group);
		 * 
		 * System.out.
		 * println("save the state of group in local variable 'afterSave'");
		 * 
		 * afterSave = new CustomerGroup(); afterSave.setId(group.getId());
		 * afterSave.setName(group.getName());
		 * 
		 * System.out.println("change the customerGroup and update it in DB");
		 * group.setName(CustomerType.COMPANY);
		 * 
		 * customerGroupDao.update(group);
		 * 
		 * System.out.println("customerGroup after update: " + group);
		 * 
		 * CustomerGroup afterUpdate = group;
		 * 
		 * System.out.println("testing");
		 * 
		 * Assert.notNull(afterSave, "group must be saved");
		 * 
		 * Assert.notNull(afterSave.getName(),
		 * "'name' column must not by empty");
		 * 
		 * Assert.notNull(afterUpdate, "group must be updated and not empty");
		 * 
		 * Assert.notNull(afterUpdate.getName(),
		 * "'name' column must not by empty but it must be changed");
		 * 
		 * Assert.isTrue((afterSave.getId().equals(afterUpdate.getId())),
		 * "'id' column must not by changed");
		 * Assert.isTrue(!(afterSave.getName().equals(afterUpdate.getName())),
		 * "'name' column must by changed");
		 * 
		 * Integer savedGroupId = afterSave.getId();
		 * System.out.println("delete the customerGroup from DB by id= " +
		 * savedGroupId);
		 * 
		 * customerGroupDao.delete(savedGroupId); CustomerGroup deletedGroup =
		 * customerGroupDao.get(savedGroupId);
		 * 
		 * System.out.println("customerGroup after delete: " + deletedGroup);
		 * 
		 */ }

	@Test
	public void getTest() {

		/*
		 * System.out.println(
		 * "======================getTest()===========================");
		 * System.out.println("creating a new customerGroup");
		 * 
		 * CustomerGroup group = new CustomerGroup();
		 * group.setName(CustomerType.INDIVIDUAL);
		 * 
		 * System.out.println("customerGroup after creating: " + group);
		 * System.out.println("saving the group in DB");
		 * 
		 * customerGroupDao.insert(group);
		 * 
		 * System.out.println("group after save in DB: " + group);
		 * 
		 * Integer savedGroupId = group.getId(); CustomerGroup groupFromDB =
		 * customerGroupDao.get(savedGroupId);
		 * 
		 * System.out.println("testing");
		 * 
		 * Assert.notNull(groupFromDB, "customerGroup must be saved");
		 * 
		 * Assert.notNull(groupFromDB.getName(),
		 * "'name' column must not by empty");
		 * 
		 * Assert.isTrue(groupFromDB.getName().equals(group.getName()),
		 * "name from DB must by eq. to prepared name");
		 * 
		 * System.out.println("delete the customerGroup from DB by id= " +
		 * savedGroupId);
		 * 
		 * customerGroupDao.delete(savedGroupId); CustomerGroup deletedGroup =
		 * customerGroupDao.get(savedGroupId);
		 * 
		 * System.out.println("customerGroup after delete: " + deletedGroup);
		 */
	}

	@Test
	public void deleteTest() {

		/*
		 * System.out.println(
		 * "======================deleteTest()===========================");
		 * System.out.println("creating a new customerGroup");
		 * 
		 * CustomerGroup group = new CustomerGroup();
		 * group.setName(CustomerType.INDIVIDUAL);
		 * 
		 * System.out.println("customerGroup after creating: " + group);
		 * System.out.println("saving the group in DB");
		 * 
		 * customerGroupDao.insert(group);
		 * 
		 * System.out.println("group after save in DB: " + group);
		 * 
		 * Integer savedGroupId = group.getId(); CustomerGroup groupFromDB =
		 * customerGroupDao.get(savedGroupId);
		 * 
		 * System.out.println("testing");
		 * 
		 * Assert.notNull(groupFromDB, "customerGroup must be saved");
		 * 
		 * System.out.println("delete the customerGroup from DB by id= " +
		 * savedGroupId);
		 * 
		 * customerGroupDao.delete(savedGroupId); CustomerGroup deletedGroup =
		 * customerGroupDao.get(savedGroupId);
		 * 
		 * System.out.println("customerGroup after delete: " + deletedGroup);
		 * 
		 * Assert.isNull(deletedGroup, "customerGroup must be deleted");
		 */
	}

	@Ignore
	@Test
	public void getAllTest() {

		System.out.println("======================getAllTest()===========================");

	}

}

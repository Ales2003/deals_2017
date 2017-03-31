package ru.mail.ales2003.deals2017.dao.impl.db;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.util.Assert;

import ru.mail.ales2003.deals2017.datamodel.CustomerGroup;

public class CustomerGroupDaoTest extends AbstractTest {

	@Inject
	private ICustomerGroupDao customerGroupDao;

	@Test
	public void insertTest() {

		System.out.println("======================insertTest()===========================");
		System.out.println("creating a new customerGroup");

		CustomerGroup group = new CustomerGroup();
		group.setName("individual");

		System.out.println("customerGroup after creating: " + group);
		System.out.println("saving the group in DB");

		customerGroupDao.insert(group);

		System.out.println("group after save in DB: " + group);

		Integer savedGroupId = group.getId();
		CustomerGroup groupFromDB = customerGroupDao.get(savedGroupId);

		System.out.println("testing");

		Assert.notNull(groupFromDB, "customerGroup must be saved");

		Assert.notNull(groupFromDB.getName(), "'name' column must not by empty");

		Assert.isTrue(groupFromDB.getName().equals(group.getName()), "name from DB must by eq. to prepared name");

		System.out.println("delete the customerGroup from DB by id= " + savedGroupId);

		customerGroupDao.delete(savedGroupId);
		CustomerGroup deletedGroup = customerGroupDao.get(savedGroupId);

		System.out.println("customerGroup after delete: " + deletedGroup);

	}

	@Test
	public void getTest() {

		System.out.println("======================getTest()===========================");
		System.out.println("creating a new customerGroup");

		CustomerGroup group = new CustomerGroup();
		group.setName("individual");

		System.out.println("customerGroup after creating: " + group);
		System.out.println("saving the group in DB");

		customerGroupDao.insert(group);

		System.out.println("group after save in DB: " + group);

		Integer savedGroupId = group.getId();
		CustomerGroup groupFromDB = customerGroupDao.get(savedGroupId);

		System.out.println("testing");

		Assert.notNull(groupFromDB, "customerGroup must be saved");

		Assert.notNull(groupFromDB.getName(), "'name' column must not by empty");

		Assert.isTrue(groupFromDB.getName().equals(group.getName()), "name from DB must by eq. to prepared name");

		System.out.println("delete the customerGroup from DB by id= " + savedGroupId);

		customerGroupDao.delete(savedGroupId);
		CustomerGroup deletedGroup = customerGroupDao.get(savedGroupId);

		System.out.println("customerGroup after delete: " + deletedGroup);

	}

	@Test
	public void updateTest() {

		final CustomerGroup afterSave;

		System.out.println("======================updateTest()===========================");
		System.out.println("creating new customerGroup");

		CustomerGroup group = new CustomerGroup();
		group.setName("individual");

		System.out.println("customerGroup after creating: " + group);
		System.out.println("saving the group in DB");

		customerGroupDao.insert(group);

		System.out.println("group after save in DB: " + group);

		System.out.println("save the state of group in local variable 'afterSave'");

		afterSave = new CustomerGroup();
		afterSave.setId(group.getId());
		afterSave.setName(group.getName());

		System.out.println("change the customerGroup and update it in DB");
		group.setName("company");

		customerGroupDao.update(group);

		System.out.println("customerGroup after update: " + group);

		CustomerGroup afterUpdate = group;

		System.out.println("testing");

		Assert.notNull(afterSave, "group must be saved");

		Assert.notNull(afterSave.getName(), "'name' column must not by empty");

		Assert.notNull(afterUpdate, "group must be updated and not empty");

		Assert.notNull(afterUpdate.getName(), "'name' column must not by empty but it must be changed");

		Assert.isTrue(!(afterSave.getName().equals(afterUpdate.getName())), "'name' column must by changed");

		Integer savedGroupId = afterSave.getId();
		System.out.println("delete the customerGroup from DB by id= " + savedGroupId);

		customerGroupDao.delete(savedGroupId);
		CustomerGroup deletedGroup = customerGroupDao.get(savedGroupId);

		System.out.println("customerGroup after delete: " + deletedGroup);

	}

	@Test
	public void deleteTest() {

		System.out.println("======================deleteTest()===========================");
		System.out.println("creating a new customerGroup");

		CustomerGroup group = new CustomerGroup();
		group.setName("individual");

		System.out.println("customerGroup after creating: " + group);
		System.out.println("saving the group in DB");

		customerGroupDao.insert(group);

		System.out.println("group after save in DB: " + group);

		Integer savedGroupId = group.getId();
		CustomerGroup groupFromDB = customerGroupDao.get(savedGroupId);

		System.out.println("testing");

		Assert.notNull(groupFromDB, "customerGroup must be saved");

		System.out.println("delete the customerGroup from DB by id= " + savedGroupId);

		customerGroupDao.delete(savedGroupId);
		CustomerGroup deletedGroup = customerGroupDao.get(savedGroupId);

		System.out.println("customerGroup after delete: " + deletedGroup);

		Assert.isNull(deletedGroup, "customerGroup must be deleted");

	}

	@Ignore
	@Test
	public void getAllTest() {

		System.out.println("======================getAllTest()===========================");

	
	
	
	
	
	}

}

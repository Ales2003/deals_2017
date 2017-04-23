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

import ru.mail.ales2003.deals2017.datamodel.Customer;
import ru.mail.ales2003.deals2017.datamodel.CustomerGroup;
import ru.mail.ales2003.deals2017.datamodel.Manager;

public class CustomerServiceTest extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceTest.class);

	@Inject
	private ICustomerService service;

	@Inject
	private IManagerService managerService;

	@Inject
	private ICustomerGroupService customerGroupService;

	private Customer instance_1;
	private Customer instance_2;
	private Customer instance_1FromDb;
	private Customer instance_2FromDb;
	private Customer modifiedInstance;

	private Manager manager;
	private Manager managerFromDb;

	private CustomerGroup customerGroup;
	private CustomerGroup customerGroupFromDb;

	@Before
	public void runBeforeTestMethod() {
		LOGGER.debug("Start preparation of the method");

		LOGGER.debug("Creating manager in Db");
		manager = new Manager();
		managerService.save(manager);
		managerFromDb = managerService.get(manager.getId());
		LOGGER.debug("Manager was created with id={}", managerFromDb.getId());

		LOGGER.debug("Creating customerGroup in Db");
		customerGroup = new CustomerGroup();
		customerGroupService.save(customerGroup);
		customerGroupFromDb = customerGroupService.get(customerGroup.getId());
		LOGGER.debug("CustomerGroup was created with id={}", customerGroup.getId());

		LOGGER.debug("Creating customers in JVM");
		instance_1 = getInstance("Nikita", "Sergeevich", "Samohval", "Company1", "Grodno", "+37529...",
				customerGroupFromDb.getId(), managerFromDb.getId());
		instance_2 = getInstance("Andrei", "Ivanovich", "Ratich", "Company2", "Minsk", "+37529...",
				customerGroupFromDb.getId(), managerFromDb.getId());
		LOGGER.debug("Customers in JVM were created");

		LOGGER.debug("Finish preparation of the method");
	}

	@After
	public void runAfterTestMethod() {
		LOGGER.debug("Start completion of the method");

		LOGGER.debug("Start deleting customers from Db");
		for (Customer c : service.getAll()) {
			deleteFromDb(c.getId());
		}
		LOGGER.debug("Customers were deleted from Db ");

		LOGGER.debug("Start deleting customerGroups from Db");
		for (CustomerGroup cG : customerGroupService.getAll()) {
			customerGroupService.delete(cG.getId());
		}
		LOGGER.debug("CustomerGroups were deleted from Db ");

		LOGGER.debug("Start deleting managers from Db");
		for (Manager m : managerService.getAll()) {
			managerService.delete(m.getId());
		}
		LOGGER.debug("Managers were deleted from Db ");

		LOGGER.debug("Finish completion of the method");
	}

	/*
	 * Two objects with the same Id are compared: created in Java and saved in &
	 * extracted from the database
	 */
	@Test
	public void insertTest() {
		LOGGER.debug("Start insertTest method");
		service.save(instance_1);
		instance_1FromDb = service.get(instance_1.getId());

		Assert.notNull(instance_1FromDb, "instance must be saved");

		Assert.notNull(instance_1FromDb.getFirstName(), "'firstname' column must not by empty");
		Assert.notNull(instance_1FromDb.getPatronymic(), "'patronymic' column must not by empty");
		Assert.notNull(instance_1FromDb.getLastName(), "'lastName' column must not by empty");
		Assert.notNull(instance_1FromDb.getCompanyName(), "'companyName' column must not by empty");
		Assert.notNull(instance_1FromDb.getAddress(), "'address' column must not by empty");
		Assert.notNull(instance_1FromDb.getPhoneNumber(), "'phoneNumber' column must not by empty");
		Assert.notNull(instance_1FromDb.getCustomerGroupId(), "'customerGroupId' column must not by empty");
		Assert.notNull(instance_1FromDb.getManagerId(), "'managerId' column must not by empty");

		Assert.isTrue(
				instance_1FromDb.getFirstName().equals(instance_1.getFirstName())
						&& instance_1FromDb.getPatronymic().equals(instance_1.getPatronymic())
						&& instance_1FromDb.getLastName().equals(instance_1.getLastName())
						&& instance_1FromDb.getCompanyName().equals(instance_1.getCompanyName())
						&& instance_1FromDb.getAddress().equals(instance_1.getAddress())
						&& instance_1FromDb.getPhoneNumber().equals(instance_1.getPhoneNumber())
						&& instance_1FromDb.getCustomerGroupId().equals(instance_1.getCustomerGroupId())
						&& instance_1FromDb.getManagerId().equals(instance_1.getManagerId()),
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

		Assert.isTrue(instance_1FromDb.getFirstName() != null && instance_1FromDb.getPatronymic() != null
				&& instance_1FromDb.getPatronymic() != null && instance_1FromDb.getLastName() != null
				&& instance_1FromDb.getCompanyName() != null && instance_1FromDb.getAddress() != null
				&& instance_1FromDb.getPhoneNumber() != null && instance_1FromDb.getCustomerGroupId() != null
				&& instance_1FromDb.getManagerId() != null, "columns must not by empty");

		Assert.isTrue(instance_2FromDb.getFirstName() != null && instance_2FromDb.getPatronymic() != null
				&& instance_2FromDb.getPatronymic() != null && instance_2FromDb.getLastName() != null
				&& instance_2FromDb.getCompanyName() != null && instance_2FromDb.getAddress() != null
				&& instance_2FromDb.getPhoneNumber() != null && instance_2FromDb.getCustomerGroupId() != null
				&& instance_2FromDb.getManagerId() != null, "columns must not by empty");

		Assert.isTrue(
				instance_1FromDb.getFirstName().equals(instance_1.getFirstName())
						&& instance_1FromDb.getPatronymic().equals(instance_1.getPatronymic())
						&& instance_1FromDb.getLastName().equals(instance_1.getLastName())
						&& instance_1FromDb.getCompanyName().equals(instance_1.getCompanyName())
						&& instance_1FromDb.getAddress().equals(instance_1.getAddress())
						&& instance_1FromDb.getPhoneNumber().equals(instance_1.getPhoneNumber())
						&& instance_1FromDb.getCustomerGroupId().equals(instance_1.getCustomerGroupId())
						&& instance_1FromDb.getManagerId().equals(instance_1.getManagerId()),
				"values of the corresponding columns must by eq.");

		Assert.isTrue(
				instance_2FromDb.getFirstName().equals(instance_2.getFirstName())
						&& instance_2FromDb.getPatronymic().equals(instance_2.getPatronymic())
						&& instance_2FromDb.getLastName().equals(instance_2.getLastName())
						&& instance_2FromDb.getCompanyName().equals(instance_2.getCompanyName())
						&& instance_2FromDb.getAddress().equals(instance_2.getAddress())
						&& instance_2FromDb.getPhoneNumber().equals(instance_2.getPhoneNumber())
						&& instance_2FromDb.getCustomerGroupId().equals(instance_2.getCustomerGroupId())
						&& instance_2FromDb.getManagerId().equals(instance_2.getManagerId()),
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

		modifiedInstance.setFirstName("New" + instance_1.getFirstName());
		modifiedInstance.setPatronymic("New" + instance_1.getPatronymic());
		modifiedInstance.setLastName("New" + instance_1.getLastName());
		modifiedInstance.setCompanyName("New" + instance_1.getCompanyName());
		modifiedInstance.setAddress("New" + instance_1.getAddress());
		modifiedInstance.setPhoneNumber("New" + instance_1.getPhoneNumber());

		service.save(modifiedInstance);

		instance_1FromDb = service.get(modifiedInstance.getId());

		Assert.isTrue((instance_1.getId().equals(modifiedInstance.getId())),
				"id of initial instance must by eq. to modified instance id");

		/*
		 * System.out.println("instance_1: "+instance_1.getFirstName());
		 * System.out.println("modifiedInstance: "+modifiedInstance.getFirstName
		 * ());
		 * System.out.println("instance_1FromDb: "+instance_1FromDb.getFirstName
		 * ());
		 * 
		 * System.out.println(instance_1.getFirstName().equals(modifiedInstance.
		 * getFirstName()));
		 * System.out.println(instance_1FromDb.getFirstName().equals(
		 * modifiedInstance.getFirstName()));
		 */

		Assert.isTrue(
				!(instance_1.getFirstName().equals(modifiedInstance.getFirstName()))
						&& !(instance_1.getPatronymic().equals(modifiedInstance.getPatronymic()))
						&& !(instance_1.getLastName().equals(modifiedInstance.getLastName()))
						&& !(instance_1.getCompanyName().equals(modifiedInstance.getCompanyName()))
						&& !(instance_1.getAddress().equals(modifiedInstance.getAddress()))
						&& !(instance_1.getPhoneNumber().equals(modifiedInstance.getPhoneNumber())),
				"values of the corresponding columns of initial and modified instances must not by eq.");

		Assert.isTrue((instance_1FromDb.getId().equals(modifiedInstance.getId())),
				"id of instance from Db must by eq. to id of  modified instances");

		Assert.isTrue(
				instance_1FromDb.getFirstName().equals(modifiedInstance.getFirstName())
						&& instance_1FromDb.getPatronymic().equals(modifiedInstance.getPatronymic())
						&& instance_1FromDb.getLastName().equals(modifiedInstance.getLastName())
						&& instance_1FromDb.getCompanyName().equals(modifiedInstance.getCompanyName())
						&& instance_1FromDb.getAddress().equals(modifiedInstance.getAddress())
						&& instance_1FromDb.getPhoneNumber().equals(modifiedInstance.getPhoneNumber()),
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

		Assert.isTrue(instance_1FromDb.getFirstName() != null && instance_1FromDb.getPatronymic() != null
				&& instance_1FromDb.getPatronymic() != null && instance_1FromDb.getLastName() != null
				&& instance_1FromDb.getCompanyName() != null && instance_1FromDb.getAddress() != null
				&& instance_1FromDb.getPhoneNumber() != null && instance_1FromDb.getCustomerGroupId() != null
				&& instance_1FromDb.getManagerId() != null, "columns must not by empty");

		Assert.isTrue(
				instance_1FromDb.getFirstName().equals(instance_1.getFirstName())
						&& instance_1FromDb.getPatronymic().equals(instance_1.getPatronymic())
						&& instance_1FromDb.getLastName().equals(instance_1.getLastName())
						&& instance_1FromDb.getCompanyName().equals(instance_1.getCompanyName())
						&& instance_1FromDb.getAddress().equals(instance_1.getAddress())
						&& instance_1FromDb.getPhoneNumber().equals(instance_1.getPhoneNumber())
						&& instance_1FromDb.getCustomerGroupId().equals(instance_1.getCustomerGroupId())
						&& instance_1FromDb.getManagerId().equals(instance_1.getManagerId()),
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
		List<Customer> instances = new ArrayList<>();
		instances.add(instance_1);
		instances.add(instance_2);
		service.saveMultiple(instance_1, instance_2);
		List<Customer> instancesFromDb = service.getAll();
		Assert.isTrue(instances.size() == instancesFromDb.size(),
				"count of from Db instances must by eq. to count of inserted instances");

		for (int i = 0; i < instances.size(); i++) {
			// False if compared to ==, since the references to objects
			Assert.isTrue(instances.get(i).getId().equals(instancesFromDb.get(i).getId()),
					"id of every instance from Db must by eq. to appropriate prepared instance id");

			Assert.isTrue(
					instances.get(i).getFirstName().equals(instancesFromDb.get(i).getFirstName())
							&& instances.get(i).getPatronymic().equals(instancesFromDb.get(i).getPatronymic())
							&& instances.get(i).getLastName().equals(instancesFromDb.get(i).getLastName())
							&& instances.get(i).getCompanyName().equals(instancesFromDb.get(i).getCompanyName())
							&& instances.get(i).getAddress().equals(instancesFromDb.get(i).getAddress())
							&& instances.get(i).getPhoneNumber().equals(instancesFromDb.get(i).getPhoneNumber())
							&& instances.get(i).getCustomerGroupId().equals(instancesFromDb.get(i).getCustomerGroupId())
							&& instances.get(i).getManagerId().equals(instancesFromDb.get(i).getManagerId()),
					"column's values of every instance from Db must by eq. to appropriate prepared instance's column's values");
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
		service.save(instance_1);
		instance_1FromDb = service.get(instance_1.getId());
		Assert.notNull(instance_1FromDb, "instance must be saved");
		deleteFromDb(instance_1.getId());
		Assert.isNull(service.get(instance_1.getId()), "instance must be deleted");
		LOGGER.debug("Finish deleteTest method");
	}

	/*
	 * method creates a new instance & gives it args
	 */
	private Customer getInstance(String firstName, String patronymic, String lastName, String companyName,
			String address, String phoneNumber, Integer customerGroupId, Integer managerId) {
		Customer instance = new Customer();
		instance.setFirstName(firstName);
		instance.setPatronymic(patronymic);
		instance.setLastName(lastName);
		instance.setCompanyName(companyName);
		instance.setAddress(address);
		instance.setPhoneNumber(phoneNumber);
		instance.setCustomerGroupId(customerGroupId);
		instance.setManagerId(managerId);
		return instance;
	}

	/*
	 * method deletes an instance by id
	 */
	private void deleteFromDb(Integer id) {
		service.delete(id);
	}
}

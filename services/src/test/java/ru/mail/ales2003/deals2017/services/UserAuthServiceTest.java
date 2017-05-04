package ru.mail.ales2003.deals2017.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import ru.mail.ales2003.deals2017.datamodel.Customer;
import ru.mail.ales2003.deals2017.datamodel.CustomerGroup;
import ru.mail.ales2003.deals2017.datamodel.Manager;
import ru.mail.ales2003.deals2017.datamodel.Role;
import ru.mail.ales2003.deals2017.datamodel.UserAuth;

public class UserAuthServiceTest extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthServiceTest.class);

	@Inject
	private IUserAuthService service;

	@Inject
	private ICustomerService customerService;

	@Inject
	private IManagerService managerService;

	@Inject
	private ICustomerGroupService customerGroupService;

	private UserAuth instance_1;
	private UserAuth instance_2;
	private UserAuth instance_1FromDb;
	private UserAuth instance_2FromDb;
	private UserAuth modifiedInstance;
	private List<UserAuth> instances;
	private List<UserAuth> instancesFromDb;
	private Set<String> loginsBeforeDbSaving;
	private Set<String> loginsFromDb;

	private Customer customer;
	private Customer customerFromDb;

	private Manager manager;
	private Manager managerFromDb;

	private CustomerGroup customerGroup;
	private CustomerGroup customerGroupFromDb;

	@Before
	public void runBeforeTestMethod() {
		LOGGER.debug("Start preparation of the method.");

		LOGGER.debug("Creating manager in Db.");
		manager = getManagerInstance("John", "Ernest", "Wood", "topmanager");
		managerService.save(manager);
		managerFromDb = managerService.get(manager.getId());
		LOGGER.debug("Manager was created with id={}", managerFromDb.getId());

		LOGGER.debug("Creating customerGroup in Db.");
		customerGroup = new CustomerGroup();
		customerGroupService.save(customerGroup);
		customerGroupFromDb = customerGroupService.get(customerGroup.getId());
		LOGGER.debug("CustomerGroup was created with id={}", customerGroup.getId());

		LOGGER.debug("Creating customer in Db");
		customer = getCustomerInstance("Nikita", "Sergeevich", "Samohval", "Company1", "Grodno", "+37529...",
				customerGroupFromDb.getId(), managerFromDb.getId());
		customerService.save(customer);
		customerFromDb = customerService.get(customer.getId());
		LOGGER.debug("Customer was created with id={}", customerFromDb.getId());

		LOGGER.debug("Creating UserAuths in JVM");

		instance_1 = getUserAuthInstance(managerFromDb.getId(), Role.SALES_MANAGER, "s_manager", "s_password");
		instance_2 = getUserAuthInstance(customerFromDb.getId(), Role.CUSTOMER, "customer", "c_password");

		LOGGER.debug("UserAuths were created in JVM.");

		LOGGER.debug("Finish preparation of the method.");
	}

	@After
	public void runAfterTestMethod() {
		LOGGER.debug("Start completion of the method");

		LOGGER.debug("Start deleting userAuths from Db");
		for (UserAuth uA : service.getAll()) {
			service.delete(uA.getId());
		}
		LOGGER.debug("UserAuths were deleted from Db ");

		LOGGER.debug("Start deleting customers from Db");
		for (Customer c : customerService.getAll()) {
			customerService.delete(c.getId());
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

		Assert.isTrue(
				(instance_1FromDb.getInOwnTableId() != null) && (instance_1FromDb.getRole() != null)
						&& (instance_1FromDb.getLogin() != null) && (instance_1FromDb.getPassword() != null),
				"columns values must not by empty");

		Assert.isTrue(instance_1FromDb.equals(instance_1), "values of the corresponding columns must by eq.");
		LOGGER.debug("Finish insertTest method");

		LOGGER.debug("Finish insertTest method");
	}

	/*
	 * Test for the insertion of several objects, for each are compared two
	 * objects with the same Id: created in Java and extracted from the database
	 */
	
	@Test
	public void insertMultipleTest() {
		LOGGER.debug("Start insertMultipleTest method");
		service.saveMultiple(instance_1, instance_2);
		instance_1FromDb = service.get(instance_1.getId());
		instance_2FromDb = service.get(instance_2.getId());

		Assert.notNull(instance_1FromDb, "instance_1 must be saved");
		Assert.notNull(instance_2FromDb, "instance_2 must be saved");

		Assert.isTrue(
				(instance_1FromDb.getInOwnTableId() != null) && (instance_1FromDb.getRole() != null)
						&& (instance_1FromDb.getLogin() != null) && (instance_1FromDb.getPassword() != null),
				"columns values must not by empty");

		Assert.isTrue(
				(instance_2FromDb.getInOwnTableId() != null) && (instance_2FromDb.getRole() != null)
						&& (instance_2FromDb.getLogin() != null) && (instance_2FromDb.getPassword() != null),
				"columns values must not by empty");

		Assert.isTrue(
				instance_1FromDb.getInOwnTableId().equals(instance_1.getInOwnTableId())
						&& instance_1FromDb.getRole().equals(instance_1.getRole())
						&& instance_1FromDb.getLogin().equals(instance_1.getLogin())
						&& instance_1FromDb.getPassword().equals(instance_1.getPassword()),
				"values of the corresponding columns must by eq.");

		Assert.isTrue(
				instance_2FromDb.getInOwnTableId().equals(instance_2.getInOwnTableId())
						&& instance_2FromDb.getRole().equals(instance_2.getRole())
						&& instance_2FromDb.getLogin().equals(instance_2.getLogin())
						&& instance_2FromDb.getPassword().equals(instance_2.getPassword()),
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
		// id in own table must be not changed!!!
		modifiedInstance.setRole(Role.REVENEUE_MANAGER);
		modifiedInstance.setLogin("r_manager");
		modifiedInstance.setPassword("r_password");

		service.save(modifiedInstance);

		instance_1FromDb = service.get(modifiedInstance.getId());

		Assert.isTrue((instance_1.getId().equals(modifiedInstance.getId())),
				"id of initial instance must by eq. to modified instance id");
		// id in own table is not asserted!!!
		Assert.isTrue(
				!(instance_1.getRole().equals(modifiedInstance.getRole()))
						&& !(instance_1.getLogin().equals(modifiedInstance.getLogin()))
						&& !(instance_1.getPassword().equals(modifiedInstance.getPassword())),
				"values of the corresponding columns of initial and modified instances must not by eq.");

		Assert.isTrue((instance_1FromDb.getId().equals(modifiedInstance.getId())),
				"id of instance from Db must by eq. to id of  modified instances");

		Assert.isTrue(
				(instance_1FromDb.getInOwnTableId().equals(modifiedInstance.getInOwnTableId()))
						&& (instance_1FromDb.getRole().equals(modifiedInstance.getRole()))
						&& (instance_1FromDb.getLogin().equals(modifiedInstance.getLogin()))
						&& (instance_1FromDb.getPassword().equals(modifiedInstance.getPassword())),
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
				(instance_1FromDb.getInOwnTableId() != null) && (instance_1FromDb.getRole() != null)
						&& (instance_1FromDb.getLogin() != null) && (instance_1FromDb.getPassword() != null),
				"columns values must not by empty");

		Assert.isTrue(instance_1FromDb.equals(instance_1), "values of the corresponding columns must by eq.");

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

		instances = new ArrayList<>();
		instances.add(instance_1);
		instances.add(instance_2);
		service.saveMultiple(instance_1, instance_2);
		instancesFromDb = service.getAll();
		Assert.isTrue(instances.size() == instancesFromDb.size(),
				"count of from Db instances must by eq. to count of inserted instances");
		for (int i = 0; i < instances.size(); i++) {
			// False if compared to ==, since the references to objects
			Assert.isTrue(instances.get(i).getId().equals(instancesFromDb.get(i).getId()),
					"id of every instance from Db must by eq. to appropriate prepared instance id");

			Assert.isTrue(instances.get(i).equals(instancesFromDb.get(i)),
					"every instance from Db must by eq. to appropriate prepared instance");
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
		instance_1FromDb = service.get(instance_1.getId());
		service.delete(instance_1.getId());
		Assert.notNull(instance_1FromDb, "instance must be saved");
		Assert.isNull(service.get(instance_1.getId()), "instance must be deleted");
		LOGGER.debug("Finish deleteTest method");
	}

	/*
	 * Test for the getting an object by login. Two objects with the same Id are
	 * compared: created in Java and saved in & extracted from the database
	 */
	@Test
	public void getByLoginTest() {
		LOGGER.debug("Start getByLoginTest method");
		service.save(instance_1);
		instance_1FromDb = service.getByLogin(instance_1.getLogin());
		Assert.notNull(instance_1FromDb, "instance must be saved");

		Assert.isTrue(
				(instance_1FromDb.getInOwnTableId() != null) && (instance_1FromDb.getRole() != null)
						&& (instance_1FromDb.getLogin() != null) && (instance_1FromDb.getPassword() != null),
				"columns values must not by empty");

		Assert.isTrue(instance_1FromDb.equals(instance_1), "values of the corresponding columns must by eq.");

		LOGGER.debug("Finish  getByLoginTest method");
	}

	/*
	 * Test for the getting of loggin HashSet. Set is compared by size.
	 */
	@Test
	public void getAllLoginsTest() {

		LOGGER.debug("Start getAllLoginsTest method");

		instances = new ArrayList<>();
		instances.add(instance_1);
		instances.add(instance_2);

		loginsBeforeDbSaving = new HashSet<>();

		loginsBeforeDbSaving.add(instance_1.getLogin());
		loginsBeforeDbSaving.add(instance_2.getLogin());

		service.saveMultiple(instance_1, instance_2);

		loginsFromDb = new HashSet<>();

		instancesFromDb = service.getAll();
		for (UserAuth userAuth : instancesFromDb) {
			String loginFromDB = userAuth.getLogin();
			loginsFromDb.add(loginFromDB);
		}

		Assert.isTrue(loginsBeforeDbSaving.size() == loginsFromDb.size(),
				"count of from Db logins must by eq. to count of inserted logins");

		LOGGER.debug("Finish getAllLoginsTest method");
	}

	/*
	 * method creates a new manager instance & gives it args
	 */
	private Manager getManagerInstance(String firstName, String patronymic, String lastName, String position) {
		Manager instance = new Manager();
		instance.setFirstName(firstName);
		instance.setPatronymic(patronymic);
		instance.setLastName(lastName);
		instance.setPosition(position);
		return instance;
	}

	/*
	 * method creates a new customer instance & gives it args
	 */
	private Customer getCustomerInstance(String firstName, String patronymic, String lastName, String companyName,
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
	 * method creates a new manager instance & gives it args
	 */
	private UserAuth getUserAuthInstance(Integer inOwnTableId, Role role, String login, String password) {
		UserAuth instance = new UserAuth();
		instance.setInOwnTableId(inOwnTableId);
		instance.setRole(role);
		instance.setLogin(login);
		instance.setPassword(password);
		return instance;
	}

}

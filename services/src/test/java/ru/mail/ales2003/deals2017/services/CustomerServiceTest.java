package ru.mail.ales2003.deals2017.services;

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
		LOGGER.debug("manager was created with id={}", managerFromDb.getId());

		LOGGER.debug("Creating customerGroup in Db");
		customerGroup = new CustomerGroup();
		customerGroupService.save(customerGroup);
		customerGroupFromDb = customerGroupService.get(customerGroup.getId());
		LOGGER.debug("customerGroup was created with id={}", customerGroup.getId());

		LOGGER.debug("Creating customers in JVM");
		instance_1 = getInstance("Nikita", "Sergeevich", "Samohval", "null", "Grodno", "+37529...",
				customerGroupFromDb.getId(), managerFromDb.getId());
		instance_2 = getInstance("Andrei", "Ivanovich", "Ratich", "null", "Minsk", "+37529...",
				customerGroupFromDb.getId(), managerFromDb.getId());
		LOGGER.debug("customers in JVM was created");

		LOGGER.debug("Finish preparation of the method");
	}

	@After
	public void runAfterTestMethod() {
		LOGGER.debug("Start completion of the method");

		
		for (Customer cg : service.getAll()) {
			deleteFromDb(cg.getId());
		}
		
		//delete all customergroups& managers!!!
		
		
		LOGGER.debug("Finish completion of the method");
	}

	// method creates a new instance & gives it args
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

	// method deletes an instance by id
	private void deleteFromDb(Integer id) {
		service.delete(id);
	}

	@Test
	public void saveTest() {

		System.out.println("creating a new customer");

		Customer customer = new Customer();
		customer.setFirstName("Alex");
		customer.setPatronymic("Vladislavovich");
		customer.setLastName("Vishneuski");
		customer.setAddress("Grodno");
		customer.setCompanyName("Sinergia");
		customer.setCustomerGroupId(1);
		customer.setManagerId(2);
		customer.setPhoneNumber("1235");
		System.out.println("customer after creating: " + customer);

		System.out.println("saving the customer in DB");
		service.save(customer);

		System.out.println("customer after save in DB: " + customer);

		Integer savedCustomerId = customer.getId();
		Customer customerFromDB = service.get(savedCustomerId);

		System.out.println("testing");

		Assert.notNull(customerFromDB, "customer must be saved");

		Assert.notNull(customerFromDB.getFirstName(), "first_name column must not by empty");
		Assert.isTrue(customerFromDB.getFirstName().equals(customer.getFirstName()),
				"firstName from DB must by eq. to prepared firstName");

		Assert.notNull(customerFromDB.getPatronymic(), "patronymic column must not by empty");
		Assert.isTrue(customerFromDB.getPatronymic().equals(customer.getPatronymic()),
				"patronymic from DB must by eq. to prepared patronymic");

		Assert.notNull(customerFromDB.getLastName(), "last_name column must not by empty");
		Assert.isTrue(customerFromDB.getLastName().equals(customer.getLastName()),
				"lastName from DB must by eq. to prepared lastName");

		Assert.notNull(customerFromDB.getAddress(), "address column must not by empty");
		Assert.isTrue(customerFromDB.getAddress().equals(customer.getAddress()),
				"address from DB must by eq. to prepared address");

		Assert.notNull(customerFromDB.getCompanyName(), "company_name column must not by empty");
		Assert.isTrue(customerFromDB.getCompanyName().equals(customer.getCompanyName()),
				"companyName from DB must by eq. to prepared companyName");

		Assert.notNull(customerFromDB.getCustomerGroupId(), "customer_group_id column must not by empty");
		Assert.isTrue(customerFromDB.getCustomerGroupId().equals(customer.getCustomerGroupId()),
				"customerGroupId from DB must by eq. to prepared customerGroupId");

		Assert.notNull(customerFromDB.getManagerId(), "manager_id column must not by empty");
		Assert.isTrue(customerFromDB.getManagerId().equals(customer.getManagerId()),
				"managerId from DB must by eq. to prepared managerId");

		Assert.notNull(customerFromDB.getPhoneNumber(), "phone_number column must not by empty");
		Assert.isTrue(customerFromDB.getPhoneNumber().equals(customer.getPhoneNumber()),
				"phoneNumber from DB must by eq. to prepared phoneNumber");

		System.out.println("delete the customer from DB");
		service.delete(savedCustomerId);
		customerFromDB = service.get(savedCustomerId);
		System.out.println("customer after delete: " + customerFromDB);

	}
}

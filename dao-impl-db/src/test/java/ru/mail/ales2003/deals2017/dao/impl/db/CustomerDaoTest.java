package ru.mail.ales2003.deals2017.dao.impl.db;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.util.Assert;

import ru.mail.ales2003.deals2017.datamodel.Customer;

public class CustomerDaoTest extends AbstractTest {

	@Inject
	private ICustomerDao customerDao;

	@Before
	public void runBeforeTestMethod() {
		// to do creating Manager & CustomerGroup
		System.out.println("@Before - runBeforeTestMethod");

	}

	// Should rename to @AfterTestMethod
	@After
	public void runAfterTestMethod() {
		// to do delete Manager & CustomerGroup
		System.out.println("@After - runAfterTestMethod");
	}

	@Test
	public void insertTest() {

		System.out.println("creating a new customer");
		Customer customer = new Customer();
		customer.setFirstName("Alex");
		customer.setPatronymic("Vladislavovich");
		customer.setLastName("Vishneuski");
		customer.setAddress("Grodno");
		customer.setCompanyName("Sinergia");
		customer.setCustomerGroupId(1);
		customer.setManagerId(2);
		customer.setPhoneNumber("+375291234567");
		System.out.println("customer after creating: " + customer);

		System.out.println("saving the customer in DB");
		customerDao.insert(customer);

		System.out.println("customer after save in DB: " + customer);

		Integer savedCustomerId = customer.getId();
		Customer customerFromDB = customerDao.get(savedCustomerId);

		System.out.println("testing");

		Assert.notNull(customerFromDB, "customer must be saved");

		Assert.notNull(customerFromDB.getFirstName(), "'first_name' column must not by empty");
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
		customerDao.delete(savedCustomerId);
		customerFromDB = customerDao.get(savedCustomerId);
		System.out.println("customer after delete: " + customerFromDB);

	}

	@Ignore
	@Test
	public void getTest() {
	}

	@Ignore
	@Test
	public void updateTest() {
	}

	@Ignore
	@Test
	public void deleteTest() {
	}

	@Ignore
	@Test
	public void getAllTest() {
	}

}

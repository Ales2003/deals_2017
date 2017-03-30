package ru.mail.ales2003.deals2017.services;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.util.Assert;

import ru.mail.ales2003.deals2017.datamodel.Customer;

public class CustomerServiceTest extends AbstractTest {

	@Inject
	private ICustomerService service;

	@Test
	public void saveTest() {

		Customer customer = new Customer();
		customer.setFirstName("Alex");
		customer.setPatronymic("Vladislavovich");
		customer.setLastName("Vishneuski");
		customer.setAddress("Grodno");
		customer.setCompanyName("Sinergia");
		customer.setCustomerGroupId(1);
		customer.setManagerId(2);
		customer.setPhoneNumber("1235");

		service.save(customer);
		Integer savedCustomerId = customer.getId();

		Customer customerFromDB = service.get(savedCustomerId);

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
	}
}

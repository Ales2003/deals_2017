package ru.mail.ales2003.deals2017.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ru.mail.ales2003.deals2017.dao.impl.db.ICustomerDao;
import ru.mail.ales2003.deals2017.datamodel.Customer;
import ru.mail.ales2003.deals2017.services.ICustomerService;

@Service
public class CustomerServiceImpl implements ICustomerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Inject
	private ICustomerDao customerDao;

	@Override
	public Customer get(Integer id) {
		if (customerDao.get(id) == null) {
			LOGGER.error("Error: customer with id = " + id + " don't exist in storage)");
			return null;
		} else {
			Customer customer = customerDao.get(id);
			LOGGER.info(
					"Read one customer: id={}, first_name={}, patronymic={}, last_name={}, companyName={}, address={}, phoneNumber={}, customerGroupId={}, managerId={}",
					customer.getId(), customer.getFirstName(), customer.getPatronymic(), customer.getLastName(),
					customer.getCompanyName(), customer.getAddress(), customer.getPhoneNumber(),
					customer.getCustomerGroupId(), customer.getManagerId());
			return customer;
		}
	}

	@Override
	public List<Customer> getAll() {
		if (customerDao.getAll() == null) {
			LOGGER.error("Error: all customers don't exist in storage");
			return null;
		} else {
			LOGGER.info("Read all customers");
			return customerDao.getAll();
		}
	}

	@Override
	public void save(Customer customer) {
		if (customer == null) {
			LOGGER.error("Error: as the customer was sent a null reference");
			return;
		} else if (customer.getId() == null) {
			customerDao.insert(customer);
			LOGGER.info(
					"Inserted new customer: id={}, first_name={}, patronymic={}, last_name={}, companyName={}, address={}, phoneNumber={}, customerGroupId={}, managerId={}",
					customer.getId(), customer.getFirstName(), customer.getPatronymic(), customer.getLastName(),
					customer.getCompanyName(), customer.getAddress(), customer.getPhoneNumber(),
					customer.getCustomerGroupId(), customer.getManagerId());
		} else {
			customerDao.update(customer);
			LOGGER.info(
					"Updated customer: id={}, first_name={}, patronymic={}, last_name={}, companyName={}, address={}, phoneNumber={}, customerGroupId={}, managerId={}",
					customer.getId(), customer.getFirstName(), customer.getPatronymic(), customer.getLastName(),
					customer.getCompanyName(), customer.getAddress(), customer.getPhoneNumber(),
					customer.getCustomerGroupId(), customer.getManagerId());
		}
	}

	@Override
	public void saveMultiple(Customer... customerArray) {
		for (Customer customer : customerArray) {
			LOGGER.debug("Inserted new customer from array: " + customer);
			save(customer);
		}
		LOGGER.info("Inserted customers from array");
	}

	@Override
	public void delete(Integer id) {
		if (id == null) {
			LOGGER.error("Error: as the id was sent a null reference");
			return;
		} else {
			customerDao.delete(id);
			LOGGER.info("Deleted customer by id: " + id);
		}
	}

	//I need create so method maybe 
	//!!!maybe with StringFormat???
/*	private String getCustomerInfo(Customer customer){
		
		String customerInfo = 	new String();
		customerInfo = ("id={}, first_name={}, patronymic={}, last_name={}, companyName={}, address={}, phoneNumber={}, customerGroupId={}, managerId={}",
				customer.getId(), customer.getFirstName(), customer.getPatronymic(), customer.getLastName(),
					customer.getCompanyName(), customer.getAddress(), customer.getPhoneNumber(),
					customer.getCustomerGroupId(), customer.getManagerId());
		return customerInfo; 
	};*/
}

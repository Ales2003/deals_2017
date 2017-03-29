package ru.mail.ales2003.deals2017.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import ru.mail.ales2003.deals2017.dao.impl.db.ICustomerDao;
import ru.mail.ales2003.deals2017.datamodel.Customer;
import ru.mail.ales2003.deals2017.services.ICustomerService;

@Service
public class CustomerServiceImpl implements ICustomerService {

	@Inject
	private ICustomerDao customerDao;

	@Override
	public Customer get(Integer id) {
		return customerDao.get(id);
	}

	@Override
	public void save(Customer customer) {
		if (customer.getId() == null) {
			System.out.println("Insert new Customer");
			customerDao.insert(customer);
		} else {
			System.out.println("Update existing Customer");
			customerDao.update(customer);
		}

	}

	@Override
	public void saveMultiple(Customer... customerArr) {
		for (Customer customer : customerArr) {
			save(customer);
		}

	}

	@Override
	public List<Customer> getAll() {
		return customerDao.getAll();
	}

	@Override
	public void delete(Integer id) {
		customerDao.delete(id);

	}

}

package ru.mail.ales2003.deals2017.dao.impl.db;

import java.util.List;

import ru.mail.ales2003.deals2017.datamodel.Customer;

public interface ICustomerDao {

	Customer insert(Customer customer);

	Customer get(Integer id);

	void update(Customer customer);

	void delete(Integer id);

	List<Customer> getAll();
}

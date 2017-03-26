package ru.mail.ales2003.deals2017.services;

import ru.mail.ales2003.deals2017.datamodel.Customer;

public interface ICustomerService {

	Integer insert(Customer customer);

	Customer get(Integer id);

	void update(Customer customer);

	void delete(Integer id);
}

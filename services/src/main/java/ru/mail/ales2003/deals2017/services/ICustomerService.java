package ru.mail.ales2003.deals2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ru.mail.ales2003.deals2017.datamodel.Customer;

public interface ICustomerService {

	Customer get(Integer id);

	    void save(Customer customer);

	    @Transactional
	    void saveMultiple(Customer... customer);

	    List<Customer> getAll();

	    void delete(Integer id);
}

package ru.mail.ales2003.deals2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ru.mail.ales2003.deals2017.dao.api.customentities.AuthorizedCustomer;
import ru.mail.ales2003.deals2017.dao.api.customentities.AuthorizedManager;
import ru.mail.ales2003.deals2017.datamodel.Customer;

public interface ICustomerService {

	/**
	 * @param id
	 * @return Customer entity
	 */
	Customer get(Integer id);

	/**
	 * @return List&ltCustomer&gt entities
	 */
	List<Customer> getAll();

	/**
	 * @param entity
	 */
	@Transactional
	void save(Customer entity);

	/**
	 * @param entityArray
	 */
	@Transactional
	void saveMultiple(Customer... entityArray);

	/**
	 * @param id
	 */
	@Transactional
	void delete(Integer id);
	
	/**
	 * @param AuthorizedCustomer entity
	 */
	@Transactional
	void saveWithAuthorization(AuthorizedCustomer entity);
}

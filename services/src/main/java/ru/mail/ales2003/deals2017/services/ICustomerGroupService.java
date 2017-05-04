package ru.mail.ales2003.deals2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ru.mail.ales2003.deals2017.datamodel.CustomerGroup;

public interface ICustomerGroupService {

	/**
	 * @param id
	 * @return CustomerGroup entity
	 */
	CustomerGroup get(Integer id);

	/**
	 * @return List&ltCustomerGroup&gt entities
	 */
	List<CustomerGroup> getAll();

	/**
	 * @param entity
	 */
	@Transactional
	void save(CustomerGroup entity);

	/**
	 * @param entityArray
	 */
	@Transactional
	void saveMultiple(CustomerGroup... entityArray);

	/**
	 * @param id
	 */
	@Transactional
	void delete(Integer id);

	void refreshCustomerGroupSet();

	void fillCustomerGroupSet();

	void clearCustomerGroupSet();

	boolean isExist(CustomerGroup entity);

}

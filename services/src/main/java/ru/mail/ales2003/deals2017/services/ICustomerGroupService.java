package ru.mail.ales2003.deals2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ru.mail.ales2003.deals2017.datamodel.CustomerGroup;

public interface ICustomerGroupService {

	CustomerGroup get(Integer id);

	List<CustomerGroup> getAll();

	@Transactional
	void save(CustomerGroup entity);

	@Transactional
	void saveMultiple(CustomerGroup... entityAray);

	@Transactional
	void delete(Integer id);

}

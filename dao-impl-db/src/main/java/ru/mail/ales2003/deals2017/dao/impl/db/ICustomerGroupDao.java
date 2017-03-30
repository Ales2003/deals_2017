package ru.mail.ales2003.deals2017.dao.impl.db;

import java.util.List;

import ru.mail.ales2003.deals2017.datamodel.CustomerGroup;

public interface ICustomerGroupDao {

	CustomerGroup insert(CustomerGroup entity);

	CustomerGroup get(Integer id);

	void update(CustomerGroup entity);

	void delete(Integer id);

	List<CustomerGroup> getAll();
}

package ru.mail.ales2003.deals2017.dao.impl.db;

import ru.mail.ales2003.deals2017.datamodel.CustomerGroup;

public interface ICustomerGroupDao {
	
	Integer insert(CustomerGroup customerGroup);

	CustomerGroup get(Integer id);

	void update(CustomerGroup customerGroup);

	void delete(Integer id);
}

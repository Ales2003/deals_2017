package ru.mail.ales2003.deals2017.dao.impl.db;

import ru.mail.ales2003.deals2017.datamodel.Manager;

public interface IManagerDao {
	
	Manager insert(Manager manager);

	Manager get(Integer id);

	void update(Manager manager);

	void delete(Integer id);
}

package ru.mail.ales2003.deals2017.dao.api;

import java.util.List;

import ru.mail.ales2003.deals2017.datamodel.Manager;

public interface IManagerDao {

	Manager insert(Manager manager);

	Manager get(Integer id);

	List<Manager> getAll();

	void update(Manager manager);

	void delete(Integer id);
}

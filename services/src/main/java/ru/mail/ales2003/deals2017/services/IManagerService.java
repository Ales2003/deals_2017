package ru.mail.ales2003.deals2017.services;

import ru.mail.ales2003.deals2017.datamodel.Manager;

public interface IManagerService {

	Integer insert(Manager manager);

	Manager get(Integer id);

	void update(Manager manager);

	void delete(Integer id);

}

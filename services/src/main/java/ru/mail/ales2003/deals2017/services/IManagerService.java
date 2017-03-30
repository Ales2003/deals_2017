package ru.mail.ales2003.deals2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ru.mail.ales2003.deals2017.datamodel.Manager;

public interface IManagerService {

	Manager get(Integer id);

	List<Manager> getAll();

	@Transactional
	void save(Manager manager);

	@Transactional
	void saveMultiple(Manager... manager);

	@Transactional
	void delete(Integer id);

}

package ru.mail.ales2003.deals2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ru.mail.ales2003.deals2017.datamodel.Manager;

public interface IManagerService {

	/**
	 * @param id
	 * @return Manager entity
	 */
	Manager get(Integer id);

	/**
	 * @return List&ltManager&gt entities
	 */
	List<Manager> getAll();

	/**
	 * @param manager
	 */
	@Transactional
	void save(Manager entity);

	/**
	 * @param manager
	 */
	@Transactional
	void saveMultiple(Manager... entity);

	/**
	 * @param id
	 */
	@Transactional
	void delete(Integer id);

}

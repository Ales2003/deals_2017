package ru.mail.ales2003.deals2017.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ru.mail.ales2003.deals2017.dao.api.IManagerDao;
import ru.mail.ales2003.deals2017.datamodel.Manager;
import ru.mail.ales2003.deals2017.services.IManagerService;

@Service
public class ManagerServiceImpl implements IManagerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ManagerServiceImpl.class);

	@Inject
	private IManagerDao managerDao;

	@Override
	public Manager get(Integer id) {
		if (managerDao.get(id) == null) {
			LOGGER.error("Error: manager with id = " + id + " don't exist in storage)");
			return null;
		} else {
			Manager manager = managerDao.get(id);
			LOGGER.info("Read one manager: id={}, first_name={}, patronymic={}, last_name={}, position={}",
					manager.getId(), manager.getFirstName(), manager.getPatronymic(), manager.getLastName(),
					manager.getPosition());
			return manager;
		}
	}

	@Override
	public List<Manager> getAll() {
		if (managerDao.getAll() == null) {
			LOGGER.error("Error: all managers don't exist in storage");
			return null;
		} else {
			LOGGER.info("Read all managers");
			return managerDao.getAll();
		}
	}

	@Override
	public void save(Manager manager) {
		if (manager == null) {
			LOGGER.error("Error: as the manager was sent a null reference");
			return;
		} else if (manager.getId() == null) {
			managerDao.insert(manager);
			LOGGER.info("Inserted new manager: id={}, first_name={}, patronymic={}, last_name={}, position={}",
					manager.getId(), manager.getFirstName(), manager.getPatronymic(), manager.getLastName(),
					manager.getPosition());
		} else {
			managerDao.update(manager);
			LOGGER.info("Updated manager: id={}, first_name={}, patronymic={}, last_name={}, position={}",
					manager.getId(), manager.getFirstName(), manager.getPatronymic(), manager.getLastName(),
					manager.getPosition());
		}
	}

	@Override
	public void saveMultiple(Manager... managerArray) {
		for (Manager manager : managerArray) {
			LOGGER.debug("Inserted new manager from array: " + manager);
			save(manager);
		}
		LOGGER.info("Inserted managers from array");

	}

	@Override
	public void delete(Integer id) {
		if (id == null) {
			LOGGER.error("Error: as the id was sent a null reference");
			return;
		} else {
			managerDao.delete(id);
			LOGGER.info("Deleted manager by id: " + id);
		}
	}

}

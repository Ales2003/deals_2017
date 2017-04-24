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

	private String className = Manager.class.getSimpleName();

	@Override
	public Manager get(Integer id) {
		if (managerDao.get(id) == null) {
			String errMsg = String.format("[%s] with id = [%s] don't exist in storage", className, id);
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg);
		} else {
			Manager entity = managerDao.get(id);
			LOGGER.info("Read one {}: {}", className, entity.toString());
			return entity;
		}
	}

	@Override
	public List<Manager> getAll() {
		LOGGER.info("{} storage returns {} entitys.", className, managerDao.getAll().size());
		LOGGER.info("Read all {}s:", className);
		for (Manager m : managerDao.getAll()) {
			LOGGER.info("{} = {}", className, m.toString());
		}
		return managerDao.getAll();
	}

	@Override
	public void save(Manager entity) {
		if (entity == null) {
			LOGGER.error("Error: as the {} entity was sent a null reference", className);
			return;
		} else if (entity.getId() == null) {
			managerDao.insert(entity);
			LOGGER.info("Inserted new {} entity: {}", className, entity.toString());
		} else {
			managerDao.update(entity);
			LOGGER.info("Updated one {} entity: {}", className, entity.toString());
		}
	}

	@Override
	public void saveMultiple(Manager... entityArray) {
		for (Manager entity : entityArray) {
			LOGGER.info("Inserted new {} entity from array: {}", className, entity.toString());
			save(entity);
		}
		LOGGER.info("{} entities from array were inserted", className);

	}

	@Override
	public void delete(Integer id) {
		if (id == null) {
			LOGGER.error("Error: as the id was sent a null reference");
			return;
		} else {
			managerDao.delete(id);
			LOGGER.info("Deleted {} entity by id: {}", className, id);
		}
	}

}

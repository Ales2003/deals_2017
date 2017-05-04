package ru.mail.ales2003.deals2017.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ru.mail.ales2003.deals2017.dao.api.ICustomerGroupDao;
import ru.mail.ales2003.deals2017.datamodel.CustomerGroup;
import ru.mail.ales2003.deals2017.datamodel.CustomerType;
import ru.mail.ales2003.deals2017.services.ICustomerGroupService;

@Service
public class CustomerGroupServiceImpl implements ICustomerGroupService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerGroupServiceImpl.class);

	@Inject
	private ICustomerGroupDao customerGroupDao;

	private String className = CustomerGroup.class.getSimpleName();

	@Override
	public CustomerGroup get(Integer id) {
		if (customerGroupDao.getByManagerOrCustomerId(id) == null) {
			String errMsg = String.format("[%s] entity with id = [%s] don't exist in storage", className, id);
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg);
		} else {
			CustomerGroup entity = customerGroupDao.getByManagerOrCustomerId(id);
			LOGGER.info("Read one {} entity: {}", className, entity.toString());
			return entity;
		}
	}

	@Override
	public List<CustomerGroup> getAll() {
		LOGGER.info("{} entities storage returns {} entities.", className, customerGroupDao.getAll().size());
		LOGGER.info("Read all {} entities:", className);
		for (CustomerGroup cG : customerGroupDao.getAll()) {
			LOGGER.info("{} entity = {}", className, cG.toString());
		}
		return customerGroupDao.getAll();
	}

	@Override
	public void save(CustomerGroup entity) {
		if (entity == null) {
			LOGGER.error("Error: as the {} entity was sent a null reference", className);
			return;
		} else if (entity.getId() == null) {
			if (entity.getName() == null) {
				entity.setName(CustomerType.INDIVIDUAL);
			}
			customerGroupDao.insert(entity);
			LOGGER.info("Inserted new {} entity: {}", className, entity.toString());
		} else {
			customerGroupDao.update(entity);
			LOGGER.info("Updated one {} entity: {}", className, entity.toString());
		}
	}

	@Override
	public void saveMultiple(CustomerGroup... entityArray) {
		for (CustomerGroup entity : entityArray) {
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
			customerGroupDao.delete(id);
			LOGGER.info("Deleted {} entity by id: {}", className, id);
		}
	}
}

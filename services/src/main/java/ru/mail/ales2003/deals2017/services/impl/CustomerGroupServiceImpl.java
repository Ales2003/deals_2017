package ru.mail.ales2003.deals2017.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ru.mail.ales2003.deals2017.dao.api.ICustomerGroupDao;
import ru.mail.ales2003.deals2017.datamodel.CustomerGroup;
import ru.mail.ales2003.deals2017.datamodel.CustomerType;
import ru.mail.ales2003.deals2017.services.ICustomerGroupService;
import ru.mail.ales2003.deals2017.services.servicesexceptions.DuplicationKeyInformationException;

@Service
public class CustomerGroupServiceImpl implements ICustomerGroupService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerGroupServiceImpl.class);

	@Inject
	private ICustomerGroupDao customerGroupDao;

	private String className = CustomerGroup.class.getSimpleName();

	private static Set<String> customerGroupSet = new HashSet<>();

	@Override
	public CustomerGroup get(Integer id) {
		if (customerGroupDao.get(id) == null) {
			String errMsg = String.format("[%s] entity with id = [%s] don't exist in storage", className, id);
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg);
		} else {
			CustomerGroup entity = customerGroupDao.get(id);
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

			LOGGER.info("Refreshing CustomerGreoupSet");
			refreshCustomerGroupSet();

			if (isExist(entity)) {
				String errMsg = String.format("You want to insert [%s] entity with name [%s]. But such exist already.",
						className, entity.getName().name());
				LOGGER.error("Error: Warning: {}", errMsg);
				throw new DuplicationKeyInformationException(errMsg);
			}

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

	// Methods to avoid duplication in the CharacterType storage

	// LOGGING

	private void refreshCustomerGroupSet() {
		clearCustomerGroupSet();
		fillCustomerGroupSet();
	}

	// LOGGING

	private void fillCustomerGroupSet() {
		List<CustomerGroup> members = getAll();
		for (CustomerGroup instance : members) {
			String word = instance.getName().name();
			customerGroupSet.add(word);
		}
	}

	// LOGGING

	private void clearCustomerGroupSet() {
		customerGroupSet.clear();
	}

	// LOGGING

	private boolean isExist(CustomerGroup entity) {
		Boolean isExist = false;
		String word = entity.getName().name();
		if (isExist = customerGroupSet.contains(word)) {
			return isExist;
		}
		return isExist;
	}

}

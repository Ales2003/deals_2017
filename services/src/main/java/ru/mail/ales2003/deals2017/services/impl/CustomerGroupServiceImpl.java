package ru.mail.ales2003.deals2017.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ru.mail.ales2003.deals2017.dao.impl.db.ICustomerGroupDao;
import ru.mail.ales2003.deals2017.datamodel.CustomerGroup;
import ru.mail.ales2003.deals2017.datamodel.CustomerType;
import ru.mail.ales2003.deals2017.services.ICustomerGroupService;

@Service
public class CustomerGroupServiceImpl implements ICustomerGroupService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerGroupServiceImpl.class);

	@Inject
	private ICustomerGroupDao customerGroupDao;

	@Override
	public CustomerGroup get(Integer id) {
		if (customerGroupDao.get(id) == null) {
			LOGGER.error("Error: customerGroup with id = " + id + " don't exist in storage)");
			return null;
		} else {
			CustomerGroup customerGroup = customerGroupDao.get(id);
			LOGGER.info("Read one customerGroup: id={}, name={}", customerGroup.getId(), customerGroup.getName());
			return customerGroup;
		}
	}

	@Override
	public List<CustomerGroup> getAll() {

		if (customerGroupDao.getAll() == null) {
			LOGGER.error("Error: all customerGroups don't exist in storage");
			return null;
		} else {
			LOGGER.info("Read all customerGroups");
			return customerGroupDao.getAll();
		}
	}

	@Override
	public void save(CustomerGroup customerGroup) {
		if (customerGroup == null) {
			LOGGER.error("Error: as the customerGroup was sent a null reference");
			return;
		} else if (customerGroup.getId() == null) {
			if (customerGroup.getName() == null) {
				customerGroup.setName(CustomerType.INDIVIDUAL);
			}
			customerGroupDao.insert(customerGroup);
			LOGGER.info("Inserted new customerGroup: id={}, name={}", customerGroup.getId(), customerGroup.getName());
		} else {
			customerGroupDao.update(customerGroup);
			LOGGER.info("Updated customerGroup: id={}, name={}", customerGroup.getId(), customerGroup.getName());
		}
	}

	@Override
	public void saveMultiple(CustomerGroup... customerGroupArray) {
		for (CustomerGroup customerGroup : customerGroupArray) {
			LOGGER.debug("Inserted new customerGroup from array: " + customerGroup);
			save(customerGroup);
		}
		LOGGER.info("Inserted customerGroups from array");
	}

	@Override
	public void delete(Integer id) {
		if (id == null) {
			LOGGER.error("Error: as the id was sent a null reference");
			return;
		} else {
			customerGroupDao.delete(id);
			LOGGER.info("Deleted customerGroup by id: " + id);
		}
	}
}

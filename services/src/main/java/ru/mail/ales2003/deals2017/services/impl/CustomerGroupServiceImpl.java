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
		CustomerGroup customerGroup = customerGroupDao.get(id);
		LOGGER.info("Read one CustomerGroup: id={}, name={}", customerGroup.getId(), customerGroup.getName());
		return customerGroup;
	}

	@Override
	public List<CustomerGroup> getAll() {
		LOGGER.info("Read all CustomerGroups");
		return customerGroupDao.getAll();
	}

	@Override
	public void save(CustomerGroup customerGroup) {
		if (customerGroup.getId() == null) {

			if (customerGroup.getName() == null) {
				customerGroup.setName(CustomerType.INDIVIDUAL);
			}

			customerGroupDao.insert(customerGroup);

			LOGGER.info("Inserted new CustomerGroup: id={}, name={}", customerGroup.getId(), customerGroup.getName());
		} else {
			customerGroupDao.update(customerGroup);
			LOGGER.info("Updated new CustomerGroup: id={}, name={}", customerGroup.getId(), customerGroup.getName());
		}

	}

	@Override
	public void saveMultiple(CustomerGroup... customerGroupArray) {
		for (CustomerGroup customerGroup : customerGroupArray) {
			LOGGER.debug("Inserted new CustomerGroup from array: " + customerGroup);
			save(customerGroup);
		}
		LOGGER.info("Inserted CustomerGroups from array");
	}

	@Override
	public void delete(Integer id) {
		customerGroupDao.delete(id);
		LOGGER.info("Deleted   CustomerGroup by id: " + id);

	}

}

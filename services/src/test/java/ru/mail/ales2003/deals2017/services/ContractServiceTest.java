package ru.mail.ales2003.deals2017.services;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import ru.mail.ales2003.deals2017.datamodel.Contract;
import ru.mail.ales2003.deals2017.datamodel.ContractStatus;
import ru.mail.ales2003.deals2017.datamodel.Customer;
import ru.mail.ales2003.deals2017.datamodel.CustomerGroup;
import ru.mail.ales2003.deals2017.datamodel.Manager;
import ru.mail.ales2003.deals2017.datamodel.PayForm;
import ru.mail.ales2003.deals2017.datamodel.PayStatus;

public class ContractServiceTest extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContractServiceTest.class);

	@Inject
	private IContractService service;

	@Inject
	private ICustomerService customerService;

	@Inject
	private IManagerService managerService;

	@Inject
	private ICustomerGroupService customerGroupService;

	private Contract instance_1;
	private Contract instance_2;
	private Contract instance_1FromDb;
	private Contract instance_2FromDb;
	private Contract modifiedInstance;

	private Customer customer;
	private Customer customerFromDb;

	private Manager manager;
	private Manager managerFromDb;

	private CustomerGroup customerGroup;
	private CustomerGroup customerGroupFromDb;

	@Before
	public void runBeforeTestMethod() {
		LOGGER.debug("Start preparation of the method");

		LOGGER.debug("Creating manager in Db");
		manager = new Manager();
		managerService.save(manager);
		managerFromDb = managerService.get(manager.getId());
		LOGGER.debug("Manager was created with id={}", managerFromDb.getId());

		LOGGER.debug("Creating customerGroup in Db");
		customerGroup = new CustomerGroup();
		customerGroupService.save(customerGroup);
		customerGroupFromDb = customerGroupService.get(customerGroup.getId());
		LOGGER.debug("CustomerGroup was created with id={}", customerGroup.getId());

		LOGGER.debug("Creating customer in Db");
		customer = new Customer();
		customer.setManagerId(manager.getId());
		customer.setCustomerGroupId(customerGroup.getId());
		customerService.save(customer);
		customerFromDb = customerService.get(customer.getId());
		LOGGER.debug("Customer was created with id={}", customer.getId());

		LOGGER.debug("Creating contracts in JVM");
		instance_1 = getInstance(new Timestamp(new Date().getTime()), ContractStatus.CONTRACT_PREPARATION, PayForm.CASH,
				PayStatus.UNPAID, customer.getId(), new BigDecimal("2000.00"));
		instance_2 = getInstance(new Timestamp(new Date().getTime()), ContractStatus.CONTRACT_PREPARATION,
				PayForm.CASHLESS, PayStatus.UNPAID, customer.getId(), new BigDecimal("5000.00"));
		LOGGER.debug("Contracts in JVM were created");

		LOGGER.debug("Finish preparation of the method");

	}

	@After
	public void runAfterTestMethod() {
		LOGGER.debug("Start completion of the method");

		LOGGER.debug("Start deleting contracts from Db");
		for (Contract cnt : service.getAllContract()) {
			service.deleteContract(cnt.getId());
		}
		LOGGER.debug("Contracts were deleted from Db ");

		LOGGER.debug("Start deleting customers from Db");
		for (Customer cst : customerService.getAll()) {
			customerService.delete(cst.getId());
		}
		LOGGER.debug("Customers were deleted from Db ");

		LOGGER.debug("Start deleting customerGroups from Db");
		for (CustomerGroup cstG : customerGroupService.getAll()) {
			customerGroupService.delete(cstG.getId());
		}
		LOGGER.debug("CustomerGroups were deleted from Db ");

		LOGGER.debug("Start deleting managers from Db");
		for (Manager mng : managerService.getAll()) {
			managerService.delete(mng.getId());
		}
		LOGGER.debug("Managers were deleted from Db ");

		LOGGER.debug("Finish completion of the method");
	}

	/*
	 * Two objects with the same Id are compared: created in Java and saved in &
	 * extracted from the database
	 */
	@Test
	public void insertTest() {
		LOGGER.debug("Start insertTest method");
		service.saveContract(instance_1);
		instance_1FromDb = service.getContract(instance_1.getId());

		Assert.notNull(instance_1FromDb, "instance must be saved");
		System.out.println(instance_1);
		System.out.println(instance_1FromDb);
		Assert.isTrue(
				(instance_1FromDb.getCreated() != null) && (instance_1FromDb.getContractStatus() != null)
						&& (instance_1FromDb.getPayForm() != null) && (instance_1FromDb.getPayStatus() != null)
						&& (instance_1FromDb.getCustomerId() != null) && (instance_1FromDb.getTotalPrice() != null),
				"columns values must not by empty");

		Assert.isTrue(
				instance_1FromDb.getCreated().equals(instance_1.getCreated())
						&& instance_1FromDb.getContractStatus().equals(instance_1.getContractStatus())
						&& instance_1FromDb.getPayForm().equals(instance_1.getPayForm())
						&& instance_1FromDb.getPayStatus().equals(instance_1.getPayStatus())
						&& instance_1FromDb.getCustomerId().equals(instance_1.getCustomerId())
						&& instance_1FromDb.getTotalPrice().equals(instance_1.getTotalPrice()),
				"values of the corresponding columns must by eq.");
		LOGGER.debug("Finish insertTest method");

	}
	// !!!!!!!!!!!!!!!!!!!!!============
	/*
	 * @Test public void mapperTest() { List<ItemInContract> list =
	 * service.ic();
	 * 
	 * System.out.println(service.ic()); }
	 */

	/*
	 * method creates a new instance & gives it args
	 */
	private Contract getInstance(Timestamp сreated, ContractStatus contractStatus, PayForm payForm, PayStatus payStatus,
			Integer customerId, BigDecimal totalPrice) {
		Contract instance = new Contract();
		instance.setCreated(сreated);
		instance.setContractStatus(contractStatus);
		instance.setPayForm(payForm);
		instance.setPayStatus(payStatus);
		instance.setCustomerId(customerId);
		instance.setTotalPrice(totalPrice);
		return instance;
	}

	/*
	 * method deletes an instance by id
	 */
	private void deleteFromDb(Integer id) {
		service.deleteContract(id);
	}

}

package ru.mail.ales2003.deals2017.services;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import ru.mail.ales2003.deals2017.datamodel.Item;
import ru.mail.ales2003.deals2017.datamodel.ItemVariant;
import ru.mail.ales2003.deals2017.datamodel.ItemVariantInContract;
import ru.mail.ales2003.deals2017.datamodel.Manager;
import ru.mail.ales2003.deals2017.datamodel.PayForm;
import ru.mail.ales2003.deals2017.datamodel.PayStatus;

public class ContractServiceTestForItemVariants extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContractServiceTestForItemVariants.class);

	@Inject
	private IContractService service;

	@Inject
	private ICustomerService customerService;

	@Inject
	private IManagerService managerService;

	@Inject
	private ICustomerGroupService customerGroupService;

	@Inject
	private IItemService itemService;

	@Inject
	private IItemVariantService itemVariantService;

	private Manager manager;
	private Manager managerFromDb;

	private CustomerGroup customerGroup;
	private CustomerGroup customerGroupFromDb;

	private Customer customer;
	private Customer customerFromDb;

	private Contract contract_1;
	private Contract contract_2;
	private Contract contract_1FromDb;
	private Contract contract_2FromDb;

	private Item item;
	private Item itemFromDb;

	private ItemVariant itemVariant_1;
	private ItemVariant itemVariant_2;
	private ItemVariant itemVariant_1FromDb;
	private ItemVariant itemVariant_2FromDb;

	private ItemVariantInContract instance_1;
	private ItemVariantInContract instance_2;
	private ItemVariantInContract instance_1FromDb;
	private ItemVariantInContract instance_2FromDb;
	private ItemVariantInContract modifiedInstance;
	private List<ItemVariantInContract> instances;
	private List<ItemVariantInContract> instancesFromDb;

	@Before
	public void runBeforeTestMethod() {
		LOGGER.debug("Start preparation of the method");

		LOGGER.debug("Creating manager in Db");
		manager = new Manager();
		managerService.save(manager);
		managerFromDb = managerService.get(manager.getId());
		LOGGER.debug("Manager in Db  was created with id={}", managerFromDb.getId());

		LOGGER.debug("Creating customerGroup in Db");
		customerGroup = new CustomerGroup();
		customerGroupService.save(customerGroup);
		customerGroupFromDb = customerGroupService.get(customerGroup.getId());
		LOGGER.debug("CustomerGroup in Db  was created with id={}", customerGroup.getId());

		LOGGER.debug("Creating customer in Db");
		customer = new Customer();
		customer.setManagerId(manager.getId());
		customer.setCustomerGroupId(customerGroupFromDb.getId());
		customerService.save(customer);
		customerFromDb = customerService.get(customer.getId());
		LOGGER.debug("Customer in Db  was created with id={}", customer.getId());

		LOGGER.debug("Creating contracts in Db");
		contract_1 = getContractInstance(new Timestamp(new Date().getTime()), ContractStatus.CONTRACT_PREPARATION,
				PayForm.CASH, PayStatus.UNPAID, customerFromDb.getId(), new BigDecimal("0.00"));
		contract_2 = getContractInstance(new Timestamp(new Date().getTime()), ContractStatus.CONTRACT_PREPARATION,
				PayForm.CASHLESS, PayStatus.UNPAID, customerFromDb.getId(), new BigDecimal("0.00"));
		service.saveContract(contract_1);
		service.saveContract(contract_2);
		contract_1FromDb = service.getContract(contract_1.getId());
		contract_2FromDb = service.getContract(contract_2.getId());
		LOGGER.debug("Contracts in Db were created with id={} and id={}", contract_1FromDb.getId(),
				contract_2FromDb.getId());

		LOGGER.debug("Creating item in Db");
		item = new Item();
		itemService.save(item);
		itemFromDb = itemService.get(item.getId());
		LOGGER.debug("Item in Db was created with id={}", itemFromDb.getId());

		LOGGER.debug("Creating itemVariants in Db");
		itemVariant_1 = getItemVariantInstance(itemFromDb.getId(), new BigDecimal("250.00"));
		itemVariant_2 = getItemVariantInstance(itemFromDb.getId(), new BigDecimal("350.00"));
		itemVariantService.saveItemVariant(itemVariant_1);
		itemVariantService.saveItemVariant(itemVariant_2);
		itemVariant_1FromDb = itemVariantService.getItemVariant(itemVariant_1.getId());
		itemVariant_2FromDb = itemVariantService.getItemVariant(itemVariant_2.getId());
		LOGGER.debug("ItemVariants in Db were created with id={} and id={}", itemVariant_1FromDb.getId(),
				itemVariant_2FromDb.getId());

		LOGGER.debug("Creating itemVariantInContract in JVM");
		instance_1 = getItemVariantInContractInstance(10, contract_1FromDb.getId(), itemVariant_1.getId());
		instance_2 = getItemVariantInContractInstance(20, contract_1FromDb.getId(), itemVariant_1.getId());
		LOGGER.debug("itemVariantInContract in JVM were created");

		LOGGER.debug("Finish preparation of the method");

	}

	@After
	public void runAfterTestMethod() {
		LOGGER.debug("Start completion of the method");

		LOGGER.debug("Start deleting itemVariantsInContract from Db");
		for (ItemVariantInContract inst : service.getAllItemVariantsInContract()) {
			service.deleteItemVariantInContract(inst.getId());
		}
		LOGGER.debug("ItemVariantsInContracts were deleted from Db ");

		LOGGER.debug("Start deleting itemVariants from Db");
		for (ItemVariant iV : itemVariantService.getAllItemVariants()) {
			itemVariantService.deleteItemVariant(iV.getId());
		}
		LOGGER.debug("ItemVariants were deleted from Db ");

		LOGGER.debug("Start deleting items from Db");
		for (Item i : itemService.getAll()) {
			itemService.delete(i.getId());
		}
		LOGGER.debug("Items were deleted from Db ");

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
	 * Instances are eq. if values of the corresponding columns are eq.
	 */

	/*
	 * Two objects with the same Id are compared: created in Java and saved in &
	 * extracted from the database
	 */
	@Test
	public void insertTest() {
		LOGGER.debug("Start insertTest method");
		service.saveItemVariantInContract(instance_1);
		instance_1FromDb = service.getItemVariantInContract(instance_1.getId());

		Assert.notNull(instance_1FromDb, "instance must be saved");

		Assert.isTrue((instance_1FromDb.getQuantity() != null) && (instance_1FromDb.getContractId() != null)
				&& (instance_1FromDb.getItemVariantId() != null), "columns values must not by empty");

		Assert.isTrue(instance_1FromDb.equals(instance_1), "values of the corresponding columns must by eq.");
		LOGGER.debug("Finish insertTest method");

	}

	/*
	 * Test for the insertion of several objects, for each are compared two
	 * objects with the same Id: created in Java and extracted from the database
	 */

	@Test
	public void insertMultipleTest() {
		LOGGER.debug("Start insertMultipleTest method");
		service.saveItemVariantInContractMultiple(instance_1, instance_2);
		instance_1FromDb = service.getItemVariantInContract(instance_1.getId());
		instance_2FromDb = service.getItemVariantInContract(instance_2.getId());

		Assert.notNull(instance_1FromDb, "instance_1 must be saved");
		Assert.notNull(instance_2FromDb, "instance_2 must be saved");

		Assert.isTrue((instance_1FromDb.getQuantity() != null) && (instance_1FromDb.getContractId() != null)
				&& (instance_1FromDb.getItemVariantId() != null), "columns values must not by empty");

		Assert.isTrue((instance_2FromDb.getQuantity() != null) && (instance_2FromDb.getContractId() != null)
				&& (instance_2FromDb.getItemVariantId() != null), "columns values must not by empty");

		Assert.isTrue(instance_1FromDb.equals(instance_1), "values of the corresponding columns must by eq.");

		Assert.isTrue(instance_2FromDb.equals(instance_2), "values of the corresponding columns must by eq.");

		LOGGER.debug("Finish  insertMultipleTest method");
	}

	/*
	 * Three objects with the same Id are compared: created in Java, modified in
	 * Java and saved in & extracted from the database
	 */
	@Test
	public void updateTest() {
		LOGGER.debug("Start updateTest method");
		service.saveItemVariantInContract(instance_1);

		modifiedInstance = service.getItemVariantInContract(instance_1.getId());

		modifiedInstance.setQuantity(30);
		modifiedInstance.setContractId(contract_2FromDb.getId());
		modifiedInstance.setItemVariantId(itemVariant_2FromDb.getId());

		service.saveItemVariantInContract(modifiedInstance);

		instance_1FromDb = service.getItemVariantInContract(modifiedInstance.getId());

		Assert.isTrue((instance_1.getId().equals(modifiedInstance.getId())),
				"id of initial instance must by eq. to modified instance id");

		Assert.isTrue(!(instance_1.equals(modifiedInstance)),
				"values of the corresponding columns of initial and modified instances must not by eq.");

		Assert.isTrue((instance_1FromDb.getId().equals(modifiedInstance.getId())),
				"id of instance from Db must by eq. to id of  modified instances");

		Assert.isTrue((instance_1FromDb.equals(modifiedInstance)),
				"values of the corresponding columns of instance from Db and modified instances must by eq.");

		LOGGER.debug("Finish  updateTest method");
	}

	/*
	 * Test for the getting an object. Two objects with the same Id are
	 * compared: created in Java and saved in & extracted from the database
	 */
	@Test
	public void getTest() {
		LOGGER.debug("Start getTest method");
		service.saveItemVariantInContract(instance_1);
		instance_1FromDb = service.getItemVariantInContract(instance_1.getId());

		Assert.notNull(instance_1FromDb, "instance must be saved");

		Assert.isTrue((instance_1FromDb.getQuantity() != null) && (instance_1FromDb.getContractId() != null)
				&& (instance_1FromDb.getItemVariantId() != null), "columns values must not by empty");

		Assert.isTrue(instance_1FromDb.equals(instance_1), "values of the corresponding columns must by eq.");

		LOGGER.debug("Finish  getTest method");
	}

	/*
	 * Test for the getting of several objects, for each are compared two
	 * objects with the same Id: created in Java and saved in & extracted from
	 * the database
	 */
	@Test
	public void getAllTest() {

		LOGGER.debug("Start getAllTest method");

		instances = new ArrayList<>();
		instances.add(instance_1);
		instances.add(instance_2);
		service.saveItemVariantInContractMultiple(instance_1, instance_2);
		instancesFromDb = service.getAllItemVariantsInContract();

		Assert.isTrue(instances.size() == instancesFromDb.size(),
				"count of from Db instances must by eq. to count of inserted instances");
		for (int i = 0; i < instances.size(); i++) {
			// False if compared to ==, since the references to objects
			Assert.isTrue(instances.get(i).getId().equals(instancesFromDb.get(i).getId()),
					"id of every instance from Db must by eq. to appropriate prepared instance id");

			Assert.isTrue(instances.get(i).equals(instancesFromDb.get(i)),
					"every instance from Db must by eq. to appropriate prepared instance");
		}
		LOGGER.debug("Finish getAllTest method");
	}

	/*
	 * Test for the deleting. One object is created, saved in DB and deleted.
	 * Then the object is checked for absence in the database
	 */
	@Test(expected = IllegalArgumentException.class)
	public void deleteTest() {
		LOGGER.debug("Start deleteTest method");
		service.saveItemVariantInContract(instance_1);
		instance_1FromDb = service.getItemVariantInContract(instance_1.getId());
		service.deleteItemVariantInContract(instance_1.getId());

		Assert.notNull(instance_1FromDb, "instance must be saved");
		Assert.isNull(service.getItemVariantInContract(instance_1.getId()), "instance must be deleted");

		LOGGER.debug("Finish deleteTest method");
	}

	/*
	 * method creates a new Contract instance & gives it args
	 */
	private Contract getContractInstance(Timestamp сreated, ContractStatus contractStatus, PayForm payForm,
			PayStatus payStatus, Integer customerId, BigDecimal totalPrice) {
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
	 * method creates a new ItemVariant instance & gives it args
	 */
	private ItemVariant getItemVariantInstance(Integer itemId, BigDecimal variantPrice) {
		ItemVariant instance = new ItemVariant();
		instance.setItemId(itemId);
		instance.setVariantPrice(variantPrice);
		return instance;
	}

	/*
	 * method creates a new ItemVariantInContract instance & gives it args
	 */
	private ItemVariantInContract getItemVariantInContractInstance(Integer quantity, Integer contractId,
			Integer itemVariantId) {
		ItemVariantInContract instance = new ItemVariantInContract();
		instance.setQuantity(quantity);
		instance.setContractId(contractId);
		instance.setItemVariantId(itemVariantId);
		return instance;
	}

}

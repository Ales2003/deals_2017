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

import ru.mail.ales2003.deals2017.dao.api.customentities.ContractCommonInfo;
import ru.mail.ales2003.deals2017.dao.api.customentities.ContractDetail;
import ru.mail.ales2003.deals2017.dao.api.customentities.Invoice;
import ru.mail.ales2003.deals2017.dao.api.filters.ContractColumnNamesForSortingParams;
import ru.mail.ales2003.deals2017.dao.api.filters.OrderDirectionForSortingParams;
import ru.mail.ales2003.deals2017.dao.api.filters.PaginationParams;
import ru.mail.ales2003.deals2017.dao.api.filters.SortingParams;
import ru.mail.ales2003.deals2017.dao.db.filters.impl.ContractCommonInfoFilter;
import ru.mail.ales2003.deals2017.datamodel.Contract;
import ru.mail.ales2003.deals2017.datamodel.ContractStatus;
import ru.mail.ales2003.deals2017.datamodel.Customer;
import ru.mail.ales2003.deals2017.datamodel.CustomerGroup;
import ru.mail.ales2003.deals2017.datamodel.CustomerType;
import ru.mail.ales2003.deals2017.datamodel.Item;
import ru.mail.ales2003.deals2017.datamodel.ItemVariant;
import ru.mail.ales2003.deals2017.datamodel.ItemVariantInContract;
import ru.mail.ales2003.deals2017.datamodel.Manager;
import ru.mail.ales2003.deals2017.datamodel.PayForm;
import ru.mail.ales2003.deals2017.datamodel.PayStatus;

public class InvoiceServiceTest extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceServiceTest.class);

	@Inject
	private IInvoiceService service;

	@Inject
	private IContractService contractService;

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

	private Contract contract;
	private Contract contractFromDb;

	private Item item;
	private Item itemFromDb;

	private ItemVariant itemVariant;
	private ItemVariant itemVariantFromDb;

	private ItemVariantInContract instance_1;
	private ItemVariantInContract instance_1FromDb;
	private ItemVariantInContract instance_2;
	private ItemVariantInContract instance_2FromDb;

	private ContractCommonInfo commonInfo;
	private List<ContractCommonInfo> commonInfos;

	private List<ContractDetail> details;

	private Invoice invoice;

	@Before
	public void runBeforeTestMethod() {
		LOGGER.debug("Start preparation of the method");

		LOGGER.debug("Creating manager in Db");
		manager = new Manager();
		manager.setLastName("Manager");
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
		customer.setLastName("Customer");
		customer.setCompanyName("BigCompany");
		customer.setManagerId(manager.getId());
		customer.setCustomerGroupId(customerGroupFromDb.getId());
		customerService.save(customer);
		customerFromDb = customerService.get(customer.getId());
		LOGGER.debug("Customer in Db  was created with id={}", customer.getId());

		LOGGER.debug("Creating contract in Db");
		contract = getInstance(new Timestamp(new Date().getTime()), ContractStatus.CONTRACT_PREPARATION, PayForm.CASH,
				PayStatus.UNPAID, customerFromDb.getId(), new BigDecimal("0.00"));
		contractService.saveContract(contract);
		contractFromDb = contractService.getContract(contract.getId());
		LOGGER.debug("Contract in Db  was created with id={}", contract.getId());

		LOGGER.debug("Creating item in Db");
		item = getInstance(new BigDecimal("250.00"), "Very good heater!!!", "Heater1");
		itemService.save(item);
		itemFromDb = itemService.get(item.getId());
		LOGGER.debug("Item was created with id={}", itemFromDb.getId());

		LOGGER.debug("Creating itemVariant in Db");
		itemVariant = getInstance(itemFromDb.getId(), new BigDecimal("300.00"));
		itemVariantService.saveItemVariant(itemVariant);
		itemVariantFromDb = itemVariantService.getItemVariant(itemVariant.getId());
		LOGGER.debug("ItemVariant was created with id={}", contract.getId());

		LOGGER.debug("Creating itemVariantInContract in Db");
		instance_1 = getInstance(10, contract.getId(), itemVariant.getId());
		instance_2 = getInstance(20, contract.getId(), itemVariant.getId());
		contractService.saveItemVariantInContract(instance_1);
		contractService.saveItemVariantInContract(instance_2);
		instance_1FromDb = contractService.getItemVariantInContract(instance_1.getId());
		instance_2FromDb = contractService.getItemVariantInContract(instance_2.getId());
		LOGGER.debug("itemVariantInContract was created with id={}", contract.getId());

		LOGGER.debug("Finish preparation of the method");

	}

	@After
	public void runAfterTestMethod() {
		LOGGER.debug("Start completion of the method");

		LOGGER.debug("Start deleting itemVariantsInContract from Db");
		for (ItemVariantInContract inst : contractService.getAllItemVariantsInContract()) {
			contractService.deleteItemVariantInContract(inst.getId());
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
		for (Contract cnt : contractService.getAllContract()) {
			contractService.deleteContract(cnt.getId());
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
			customerGroupService.clearCustomerGroupSet();
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
	 * Test for the getting an object. Given object from Db is checked for the
	 * existence and filling of fields.
	 */
	// @Ignore
	@Test
	public void getCommonInfoTest() {
		LOGGER.debug("Start getCommonInfoTest method");
		commonInfo = service.getCommonInfo(contractFromDb.getId());
		Assert.notNull(commonInfo, "instance must not be empty");

		Assert.isTrue(
				(commonInfo.getContractId() != null) && (commonInfo.getContractStatus() != null)
						&& (commonInfo.getCreated() != null) && (commonInfo.getCustomerCompanyName() != null)
						&& (commonInfo.getCustomerId() != null) && (commonInfo.getCustomerLastName() != null)
						&& (commonInfo.getCustomerType() != null) && (commonInfo.getManagerId() != null)
						&& (commonInfo.getManagerLastName() != null) && (commonInfo.getPayForm() != null)
						&& (commonInfo.getPayStatus() != null) && (commonInfo.getTotalAmount() != null),
				"columns values must not by empty");

		LOGGER.debug("Finish  getCommonInfoTest method");
	}

	/*
	 * Test for the getting of several objects, for each are checked for the
	 * existence and filling of fields
	 */

	@Test
	public void getCommonInfoForAllTest() {
		LOGGER.debug("Start getCommonInfoForAllTest method");

		commonInfos = service.getCommonInfoForAll();
		for (ContractCommonInfo instance : commonInfos) {

			Assert.notNull(instance, "instance must not be empty");

			Assert.isTrue(instance.getContractId() != null, "columns values must not by empty");
			Assert.isTrue(instance.getContractStatus() != null, "columns values must not by empty");
			Assert.isTrue(instance.getCreated() != null, "columns values must not by empty");
			Assert.isTrue(instance.getCustomerCompanyName() != null, "columns values must not by empty");
			Assert.isTrue(instance.getCustomerLastName() != null, "columns values must not by empty");
			Assert.isTrue(instance.getCustomerType() != null, "columns values must not by empty");
			Assert.isTrue(instance.getManagerLastName() != null, "columns values must not by empty");
			Assert.isTrue(instance.getManagerLastName() != null, "columns values must not by empty");
			Assert.isTrue(instance.getPayForm() != null, "columns values must not by empty");
			Assert.isTrue(instance.getPayStatus() != null, "columns values must not by empty");
			Assert.isTrue(instance.getTotalAmount() != null, "columns values must not by empty");

		}
		LOGGER.debug("Finish  getCommonInfoForAllTest method");
	}

	@Test
	public void getCommonInfoFilteredTest() {
		LOGGER.debug("Start getCommonInfoFilteredTest method");
		ContractCommonInfoFilter filter = new ContractCommonInfoFilter();

		Integer customerId = customerFromDb.getId();
		String customerLastName = customerFromDb.getLastName();
		String customerCompany = customerFromDb.getCompanyName();
		CustomerType customerType = customerGroupFromDb.getName();

		Integer managerId = managerFromDb.getId();
		String managerLastName = managerFromDb.getLastName();

		Timestamp createdFROM = contractFromDb.getCreated();
		Timestamp createdTO = contractFromDb.getCreated();
		BigDecimal amountMIN = contractFromDb.getTotalPrice().multiply(new BigDecimal("0.5"));
		BigDecimal amountMAX = contractFromDb.getTotalPrice().multiply(new BigDecimal("1.5"));
		ContractStatus contractStatus = contractFromDb.getContractStatus();
		PayForm payForm = contractFromDb.getPayForm();
		PayStatus payStatus = contractFromDb.getPayStatus();

		PaginationParams paginationParams = new PaginationParams();
		paginationParams.setLimit(5);
		paginationParams.setOffset(0);

		SortingParams sortingParams = new SortingParams();
		sortingParams.setContractSortColumn(ContractColumnNamesForSortingParams.creation_date);

		sortingParams.setSortOrder(OrderDirectionForSortingParams.desc);

		filter.setCustomerId(customerId);
		filter.setCustomerLastName(customerLastName);
		filter.setCustomerCompany(customerCompany);
		filter.setCustomerType(customerType);

		filter.setManagerId(managerId);
		filter.setManagerLastName(managerLastName);

		filter.setCreatedFROM(createdFROM);
		filter.setCreatedTO(createdTO);
		filter.setAmountMIN(amountMIN);
		filter.setAmountMAX(amountMAX);
		filter.setContractStatus(contractStatus);
		filter.setPayForm(payForm);
		filter.setPayStatus(payStatus);

		filter.setPaginationParams(paginationParams);
		filter.setSortingParams(sortingParams);

		commonInfos = service.getCommonInfoFiltered(filter);

		for (ContractCommonInfo instance : commonInfos) {
			Assert.notNull(instance, "instance must not be empty");

			Assert.isTrue(instance.getContractId() != null, "columns values must not by empty");
			Assert.isTrue(instance.getContractStatus() != null, "columns values must not by empty");
			Assert.isTrue(instance.getCreated() != null, "columns values must not by empty");
			Assert.isTrue(instance.getCustomerCompanyName() != null, "columns values must not by empty");
			Assert.isTrue(instance.getCustomerLastName() != null, "columns values must not by empty");
			Assert.isTrue(instance.getCustomerType() != null, "columns values must not by empty");
			Assert.isTrue(instance.getManagerLastName() != null, "columns values must not by empty");
			Assert.isTrue(instance.getManagerLastName() != null, "columns values must not by empty");
			Assert.isTrue(instance.getPayForm() != null, "columns values must not by empty");
			Assert.isTrue(instance.getPayStatus() != null, "columns values must not by empty");
			Assert.isTrue(instance.getTotalAmount() != null, "columns values must not by empty");

		}
		LOGGER.debug("Finish  getCommonInfoFilteredTest method");
	}

	@Test
	public void getDetailsTest() {
		LOGGER.debug("Start getDetailsTest method");
		details = service.getDetails(contractFromDb.getId());
		for (ContractDetail detail : details) {
			LOGGER.debug("Instance from query = {}", detail.toString());
			Assert.notNull(detail, "itemVariantDetail must be saved");
			Assert.isTrue(
					(detail.getContractId() != null) && (detail.getItemName() != null)
							&& (detail.getItemVariantId() != null) && (detail.getItemVariantPrice() != null)
							&& (detail.getItemVariantQuantity() != null) && (detail.getItemVariantTotalPrice() != null),
					"columns values must not by empty");
		}
		LOGGER.debug("Finish  getDetailsTest method");
	}

	@Test
	public void getInvoiceTest() {
		LOGGER.debug("Start getInvoiceTest method");
		commonInfo = service.getCommonInfo(contractFromDb.getId());
		details = new ArrayList<>();
		details = service.getDetails(contractFromDb.getId());
		invoice = new Invoice();
		invoice.setCommonInfo(commonInfo);
		invoice.setDetails(details);
		LOGGER.debug("Invoice is created: {}", invoice.toString());
		Assert.notNull(invoice, "instance must be saved");

		Assert.isTrue((invoice.getCommonInfo() != null) && (invoice.getDetails() != null),
				"columns values must not by empty");

		LOGGER.debug("Finish  getInvoiceTest method");
	}

	/*
	 * method creates a new contract instance & gives it args
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
	 * method creates a new item instance & gives it args
	 */
	private Item getInstance(BigDecimal basicPrice, String description, String name) {
		Item instance = new Item();
		instance.setBasicPrice(basicPrice);
		instance.setDescription(description);
		instance.setName(name);
		return instance;
	}

	/*
	 * method creates a new ItemVariant instance & gives it args
	 */
	private ItemVariant getInstance(Integer itemId, BigDecimal variantPrice) {
		ItemVariant instance = new ItemVariant();
		instance.setItemId(itemId);
		instance.setVariantPrice(variantPrice);
		return instance;
	}

	/*
	 * method creates a new ItemVariantInContract instance & gives it args
	 */
	private ItemVariantInContract getInstance(Integer quantity, Integer contractId, Integer itemVariantId) {
		ItemVariantInContract instance = new ItemVariantInContract();
		instance.setQuantity(quantity);
		instance.setContractId(contractId);
		instance.setItemVariantId(itemVariantId);
		return instance;
	}

}

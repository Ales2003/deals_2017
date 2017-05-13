package ru.mail.ales2003.deals2017.webapp.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.mail.ales2003.deals2017.dao.api.customentities.AuthorizedCustomer;
import ru.mail.ales2003.deals2017.datamodel.Customer;
import ru.mail.ales2003.deals2017.datamodel.CustomerGroup;
import ru.mail.ales2003.deals2017.datamodel.CustomerType;
import ru.mail.ales2003.deals2017.datamodel.Manager;
import ru.mail.ales2003.deals2017.datamodel.Role;
import ru.mail.ales2003.deals2017.datamodel.UserAuth;
import ru.mail.ales2003.deals2017.services.ICustomerGroupService;
import ru.mail.ales2003.deals2017.services.ICustomerService;
import ru.mail.ales2003.deals2017.services.IManagerService;
import ru.mail.ales2003.deals2017.services.impl.UserAuthStorage;
import ru.mail.ales2003.deals2017.services.servicesexceptions.DuplicationKeyInformationException;
import ru.mail.ales2003.deals2017.webapp.models.AuthorizedCustomerModel;
import ru.mail.ales2003.deals2017.webapp.models.CustomerModel;
import ru.mail.ales2003.deals2017.webapp.models.IdModel;
import ru.mail.ales2003.deals2017.webapp.models.UserAuthModel;
import ru.mail.ales2003.deals2017.webapp.util.EnumArrayToMessageConvertor;

@RestController
@RequestMapping("/customers")
public class CustomersController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomersController.class);

	@Inject
	private ApplicationContext context;

	@Inject
	private ICustomerService service;
	@Inject
	private ICustomerGroupService groupService;

	@Inject
	private IManagerService managerService;

	private String thisClassName = CustomersController.class.getSimpleName();

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll() {

		// Start ControllerAuthorization
		Set<Role> validUserRoles = new HashSet<>();
		{
			validUserRoles.add(Role.ADMIN);
			validUserRoles.add(Role.REVENUE_MANAGER);
			validUserRoles.add(Role.SALES_MANAGER);
		}

		String requestName = "getAll";

		LOGGER.info("Start UserAuthorization in {}: Extracting userId.", thisClassName);
		UserAuthStorage userJVMDataStorage = context.getBean(UserAuthStorage.class);
		// Getting authorisedUserID
		Integer authorisedUserId = userJVMDataStorage.getId();
		if (authorisedUserId == null) {
			String msg = String.format("No authorization. Authorization is required to access this section.");
			return new ResponseEntity<String>(msg, HttpStatus.UNAUTHORIZED);
		}
		LOGGER.info("User id is defined as id = [{}].", authorisedUserId);

		LOGGER.info("UserAuthorization in {}: Verification of access rights.", thisClassName);
		// Clarify the userROLE for obtaining permission to use the method
		Role authorisedUserRole = userJVMDataStorage.getRole();
		if (authorisedUserRole == null || !validUserRoles.contains(authorisedUserRole)) {
			String msg = String.format("No access rights. Access is available only to users: %s.",
					EnumArrayToMessageConvertor.validRoleArrayToMessage(validUserRoles));
			return new ResponseEntity<String>(msg, HttpStatus.FORBIDDEN);
		}
		LOGGER.info("User role is defined as role = [{}].", authorisedUserRole);
		LOGGER.info("Finish UserAuthorization in [{}]. User with id = [{}] makes request = [{}]", thisClassName,
				userJVMDataStorage.getId(), requestName);

		// Direct implementation of the method

		List<Customer> allEntitys;
		try {
			allEntitys = service.getAll();
		} catch (EmptyResultDataAccessException e) {
			String msg = String.format("[%s] storage returns incorrect entity count.", Customer.class);
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (allEntitys.isEmpty()) {
			String msg = String.format("An storage store of class [%s] is empty", Customer.class);
			return new ResponseEntity<String>(msg, HttpStatus.OK);
		}
		List<CustomerModel> convertedEntitys = new ArrayList<>();
		for (Customer entity : allEntitys) {
			convertedEntitys.add(entity2model(entity));
		}
		// System.out.println(Locale.getDefault());
		return new ResponseEntity<List<CustomerModel>>(convertedEntitys, HttpStatus.OK);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable(value = "id") Integer entityIdParam) {

		// Start ControllerAuthorization
		Set<Role> validUserRoles = new HashSet<>();
		{
			validUserRoles.add(Role.ADMIN);
			validUserRoles.add(Role.REVENUE_MANAGER);
			validUserRoles.add(Role.SALES_MANAGER);
			validUserRoles.add(Role.CUSTOMER);
		}

		String requestName = "getById";

		LOGGER.info("Start UserAuthorization in {}: Extracting userId.", thisClassName);
		UserAuthStorage userJVMDataStorage = context.getBean(UserAuthStorage.class);
		// Getting authorisedUserID
		Integer authorisedUserId = userJVMDataStorage.getId();
		if (authorisedUserId == null) {
			String msg = String.format("No authorization. Authorization is required to access this section.");
			return new ResponseEntity<String>(msg, HttpStatus.UNAUTHORIZED);
		}
		LOGGER.info("User id is defined as id = [{}].", authorisedUserId);

		LOGGER.info("UserAuthorization in {}: Verification of access rights.", thisClassName);
		// Clarify the userROLE for obtaining permission to use the method
		Role authorisedUserRole = userJVMDataStorage.getRole();
		if ((entityIdParam != authorisedUserId && authorisedUserRole == Role.CUSTOMER) || authorisedUserRole == null
				|| !validUserRoles.contains(authorisedUserRole)) {
			String msg = String.format(
					"No access rights. Access is available only to users: %s (for CUSTOMERS only own profile is available).",
					EnumArrayToMessageConvertor.validRoleArrayToMessage(validUserRoles));
			return new ResponseEntity<String>(msg, HttpStatus.FORBIDDEN);
		}
		LOGGER.info("User role is defined as role = [{}].", authorisedUserRole);
		LOGGER.info("Finish UserAuthorization in [{}]. User with id = [{}] makes request = [{}]", thisClassName,
				userJVMDataStorage.getId(), requestName);

		// Direct implementation of the method

		Customer entity = null;
		try {
			entity = service.get(entityIdParam);
		} catch (Exception e) {
			String msg = e.getMessage();
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}
		CustomerModel entityModel = entity2model(entity);
		return new ResponseEntity<CustomerModel>(entityModel, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody CustomerModel entityModel) {

		// Start ControllerAuthorization
		Set<Role> validUserRoles = new HashSet<>();
		{
			validUserRoles.add(Role.ADMIN);
			validUserRoles.add(Role.SALES_MANAGER);
		}

		String requestName = "create";

		LOGGER.info("Start UserAuthorization in {}: Extracting userId.", thisClassName);
		UserAuthStorage userJVMDataStorage = context.getBean(UserAuthStorage.class);
		// Getting authorisedUserID
		Integer authorisedUserId = userJVMDataStorage.getId();
		if (authorisedUserId == null) {
			String msg = String.format("No authorization. Authorization is required to access this section.");
			return new ResponseEntity<String>(msg, HttpStatus.UNAUTHORIZED);
		}
		LOGGER.info("User id is defined as id = [{}].", authorisedUserId);

		LOGGER.info("UserAuthorization in {}: Verification of access rights.", thisClassName);
		// Clarify the userROLE for obtaining permission to use the method
		Role authorisedUserRole = userJVMDataStorage.getRole();
		if (authorisedUserRole == null || !validUserRoles.contains(authorisedUserRole)) {
			String msg = String.format("No access rights. Access is available only to users: %s.",
					EnumArrayToMessageConvertor.validRoleArrayToMessage(validUserRoles));
			return new ResponseEntity<String>(msg, HttpStatus.FORBIDDEN);
		}
		LOGGER.info("User role is defined as role = [{}].", authorisedUserRole);
		LOGGER.info("Finish UserAuthorization in [{}]. User with id = [{}] makes request = [{}]", thisClassName,
				userJVMDataStorage.getId(), requestName);

		// Direct implementation of the method

		Customer entity = null;

		entity = model2entity(entityModel);

		service.save(entity);

		return new ResponseEntity<IdModel>(new IdModel(entity.getId()), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody CustomerModel entityModel,
			@PathVariable(value = "id") Integer entityIdParam) {
		// Start ControllerAuthorization
		Set<Role> validUserRoles = new HashSet<>();
		{
			validUserRoles.add(Role.ADMIN);
			validUserRoles.add(Role.SALES_MANAGER);
			validUserRoles.add(Role.REVENUE_MANAGER);
		}

		String requestName = "update";

		LOGGER.info("Start UserAuthorization in {}: Extracting userId.", thisClassName);
		UserAuthStorage userJVMDataStorage = context.getBean(UserAuthStorage.class);
		// Getting authorisedUserID
		Integer authorisedUserId = userJVMDataStorage.getId();
		if (authorisedUserId == null) {
			String msg = String.format("No authorization. Authorization is required to access this section.");
			return new ResponseEntity<String>(msg, HttpStatus.UNAUTHORIZED);
		}
		LOGGER.info("User id is defined as id = [{}].", authorisedUserId);

		LOGGER.info("UserAuthorization in {}: Verification of access rights.", thisClassName);
		// Clarify the userROLE for obtaining permission to use the method
		Role authorisedUserRole = userJVMDataStorage.getRole();
		if (authorisedUserRole == null || !validUserRoles.contains(authorisedUserRole)) {
			String msg = String.format(
					"No access rights. Access is available only to users: %s (for MANAGERS only own profile is available).",
					EnumArrayToMessageConvertor.validRoleArrayToMessage(validUserRoles));
			return new ResponseEntity<String>(msg, HttpStatus.FORBIDDEN);
		}
		LOGGER.info("User role is defined as role = [{}].", authorisedUserRole);
		LOGGER.info("Finish UserAuthorization in [{}]. User with id = [{}] makes request = [{}]", thisClassName,
				userJVMDataStorage.getId(), requestName);

		// Direct implementation of the method

		Customer entity = null;

		try {
			entity = service.get(entityIdParam);
		} catch (Exception e) {
			String msg = e.getMessage();
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}

		if (entityModel.getFirstName() != null) {
			entity.setFirstName(entityModel.getFirstName());
		}
		if (entityModel.getPatronymic() != null) {
			entity.setPatronymic(entityModel.getPatronymic());
		}
		if (entityModel.getLastName() != null) {
			entity.setLastName(entityModel.getLastName());
		}
		if (entityModel.getCompanyName() != null) {
			entity.setCompanyName(entityModel.getCompanyName());
		}
		if (entityModel.getAddress() != null) {
			entity.setAddress(entityModel.getAddress());
		}
		if (entityModel.getPhoneNumber() != null) {
			entity.setPhoneNumber(entityModel.getPhoneNumber());
		}

		if (entityModel.getCustomerGroupId() != null) {
			entity.setCustomerGroupId(entityModel.getCustomerGroupId());
		}
		if (entityModel.getManagerId() != null) {
			entity.setManagerId(entityModel.getManagerId());
		}

		service.save(entity);

		return new ResponseEntity<IdModel>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable(value = "id") Integer entityIdParam) {

		// Start ControllerAuthorization
		Set<Role> validUserRoles = new HashSet<>();
		{
			validUserRoles.add(Role.ADMIN);
		}

		String requestName = "delete";

		LOGGER.info("Start UserAuthorization in {}: Extracting userId.", thisClassName);
		UserAuthStorage userJVMDataStorage = context.getBean(UserAuthStorage.class);
		// Getting authorisedUserID
		Integer authorisedUserId = userJVMDataStorage.getId();
		if (authorisedUserId == null) {
			String msg = String.format("No authorization. Authorization is required to access this section.");
			return new ResponseEntity<String>(msg, HttpStatus.UNAUTHORIZED);
		}
		LOGGER.info("User id is defined as id = [{}].", authorisedUserId);

		LOGGER.info("UserAuthorization in {}: Verification of access rights.", thisClassName);
		// Clarify the userROLE for obtaining permission to use the method
		Role authorisedUserRole = userJVMDataStorage.getRole();
		if (!validUserRoles.contains(authorisedUserRole)) {
			String msg = String.format("No access rights. Access is available only to users: %s).",
					EnumArrayToMessageConvertor.validRoleArrayToMessage(validUserRoles));
			return new ResponseEntity<String>(msg, HttpStatus.FORBIDDEN);
		}
		LOGGER.info("User role is defined as role = [{}].", authorisedUserRole);
		LOGGER.info("Finish UserAuthorization in [{}]. User with id = [{}] makes request = [{}]", thisClassName,
				userJVMDataStorage.getId(), requestName);

		// Direct implementation of the method

		// checking of existing
		try {
			service.get(entityIdParam);
		} catch (IllegalArgumentException | EmptyResultDataAccessException e) {
			String msg = String.format("Preparation before DELETE: %s", e.getMessage());
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		service.delete(entityIdParam);

		return new ResponseEntity<IdModel>(HttpStatus.OK);
	}

	@RequestMapping(value = "/auth/customer", method = RequestMethod.POST)
	public ResponseEntity<?> createCustomerWithAuthorizationData(
			@RequestBody AuthorizedCustomerModel authorizedCustomerModel) {

		// Start ControllerAuthorization
		Set<Role> validUserRoles = new HashSet<>();
		{
			validUserRoles.add(Role.ADMIN);
			validUserRoles.add(Role.SALES_MANAGER);
			validUserRoles.add(Role.GUEST);
		}

		String requestName = "createWithAuthData";

		LOGGER.info("Start UserAuthorization in {}: Extracting userId.", thisClassName);
		UserAuthStorage userJVMDataStorage = context.getBean(UserAuthStorage.class);
		// Getting authorisedUserID
		Integer authorisedUserId = userJVMDataStorage.getId();
		if (authorisedUserId == null) {
			String msg = String.format("No authorization. Authorization is required to access this section.");
			return new ResponseEntity<String>(msg, HttpStatus.UNAUTHORIZED);
		}
		LOGGER.info("User id is defined as id = [{}].", authorisedUserId);

		LOGGER.info("UserAuthorization in {}: Verification of access rights.", thisClassName);
		// Clarify the userROLE for obtaining permission to use the method
		Role authorisedUserRole = userJVMDataStorage.getRole();
		if (!validUserRoles.contains(authorisedUserRole)) {
			String msg = String.format("No access rights. Access is available only to users: %s.",
					EnumArrayToMessageConvertor.validRoleArrayToMessage(validUserRoles));
			return new ResponseEntity<String>(msg, HttpStatus.FORBIDDEN);
		}
		LOGGER.info("User role is defined as role = [{}].", authorisedUserRole);
		LOGGER.info("Finish UserAuthorization in [{}]. User with id = [{}] makes request = [{}]", thisClassName,
				userJVMDataStorage.getId(), requestName);

		// Direct implementation of the method

		AuthorizedCustomer authorizedCustomer = new AuthorizedCustomer();
		Customer entity = null;
		UserAuth data = null;

		// requires usually data
		CustomerModel entityModel = authorizedCustomerModel.getCustomerModel();
		entity = model2entity(entityModel);

		// !!! Role = CUSTOMER!!!
		// requires only login&password
		UserAuthModel dataModel = authorizedCustomerModel.getAuthDataModel();
		data = dataModel2data(dataModel);
		data.setRole(Role.CUSTOMER);

		authorizedCustomer.setCustomer(entity);
		authorizedCustomer.setAuthData(data);

		try {
			service.saveWithAuthorization(authorizedCustomer);
		} catch (DuplicationKeyInformationException e) {
			String msg = String.format(e.getAttachedMsg());
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<IdModel>(new IdModel(authorizedCustomer.getCustomerId()), HttpStatus.CREATED);
	}

	private CustomerModel entity2model(Customer entity) {
		CustomerModel entityModel = new CustomerModel();
		entityModel.setId(entity.getId());
		entityModel.setFirstName(entity.getFirstName());
		entityModel.setPatronymic(entity.getPatronymic());
		entityModel.setLastName(entity.getLastName());
		entityModel.setCompanyName(entity.getCompanyName());
		entityModel.setAddress(entity.getAddress());
		entityModel.setPhoneNumber(entity.getPhoneNumber());
		entityModel.setCustomerGroupId(entity.getCustomerGroupId());
		entityModel.setManagerId(entity.getManagerId());
		return entityModel;
	}

	private Customer model2entity(CustomerModel entityModel) {
		Customer entity = new Customer();
		entity.setFirstName(entityModel.getFirstName());
		entity.setPatronymic(entityModel.getPatronymic());
		entity.setLastName(entityModel.getLastName());
		entity.setCompanyName(entityModel.getCompanyName());
		entity.setAddress(entityModel.getAddress());
		entity.setPhoneNumber(entityModel.getPhoneNumber());
		entity.setCustomerGroupId(entityModel.getCustomerGroupId());
		entity.setManagerId(entityModel.getManagerId());

		if (entity.getCustomerGroupId() == null) {
			customerToNullGroup(entity);
		}
		if (entity.getManagerId() == null) {
			customerToNullManager(entity);
		}
		if (entity.getCompanyName() == null) {
			entity.setCompanyName("Private person");
		}

		return entity;
	}

	private UserAuth dataModel2data(UserAuthModel dataModel) {
		UserAuth data = new UserAuth();
		data.setLogin(dataModel.getLogin());
		data.setPassword(dataModel.getPassword());
		return data;
	}

	private void customerToNullGroup(Customer customer) {
		Integer groupId = null;
		List<CustomerGroup> groups = groupService.getAll();
		for (CustomerGroup group : groups) {
			if (group.getName().name().equals(CustomerType.NULL_TYPE.name())) {
				groupId = group.getId();
				customer.setCustomerGroupId(groupId);
				return;
			}
		}
		CustomerGroup nullGroup = new CustomerGroup();
		nullGroup.setName(CustomerType.NULL_TYPE);
		groupService.save(nullGroup);
		groupId = nullGroup.getId();
		customer.setCustomerGroupId(groupId);
	}

	private void customerToNullManager(Customer customer) {
		Manager nullManager = new Manager();
		nullManager.setLastName("NULL_MANAGER");
		managerService.save(nullManager);
		Integer managerId = nullManager.getId();
		customer.setManagerId(managerId);
	}

}

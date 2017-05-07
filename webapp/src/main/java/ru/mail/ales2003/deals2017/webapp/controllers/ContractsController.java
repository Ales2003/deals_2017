package ru.mail.ales2003.deals2017.webapp.controllers;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import ru.mail.ales2003.deals2017.datamodel.Contract;
import ru.mail.ales2003.deals2017.datamodel.ContractStatus;
import ru.mail.ales2003.deals2017.datamodel.PayForm;
import ru.mail.ales2003.deals2017.datamodel.PayStatus;
import ru.mail.ales2003.deals2017.datamodel.Role;
import ru.mail.ales2003.deals2017.services.IContractService;
import ru.mail.ales2003.deals2017.services.IUserAuthService;
import ru.mail.ales2003.deals2017.services.impl.UserAuthStorage;
import ru.mail.ales2003.deals2017.webapp.models.ContractModel;
import ru.mail.ales2003.deals2017.webapp.models.IdModel;
import ru.mail.ales2003.deals2017.webapp.util.EnumArrayToMessageConvertor;

@RestController
@RequestMapping("/contracts")
public class ContractsController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContractsController.class);

	@Inject
	private ApplicationContext context;

	@Inject
	private IContractService service;

	@Inject
	private IUserAuthService authService;

	private String thisClassName = ContractsController.class.getSimpleName();

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
		LOGGER.info("User id is is defined as id = [{}].", authorisedUserId);

		LOGGER.info("UserAuthorization in {}: Verification of access rights.", thisClassName);
		// Clarify the userROLE for obtaining permission to use the method
		Role authorisedUserRole = userJVMDataStorage.getRole();
		if (authorisedUserRole == null || !validUserRoles.contains(authorisedUserRole)) {
			String msg = String.format("No access rights. Access is available only to users: %s.",
					EnumArrayToMessageConvertor.validRoleArrayToMessage(validUserRoles));
			return new ResponseEntity<String>(msg, HttpStatus.FORBIDDEN);
		}
		LOGGER.info("User role is defined as role = [{}].", authorisedUserRole);
		LOGGER.info("Finish UserAuthorization in {}. User with id = {} and role = {} makes requests = {}",
				thisClassName, authorisedUserId, authorisedUserRole, requestName);

		// Direct implementation of the method

		List<Contract> allEntitys;
		try {
			allEntitys = service.getAllContract();
		} catch (EmptyResultDataAccessException e) {
			String msg = String.format("[%s] storage returns incorrect entity count.", Contract.class);
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (allEntitys.isEmpty()) {
			String msg = String.format("An storage store of class [%s] is empty", Contract.class);
			return new ResponseEntity<String>(msg, HttpStatus.OK);
		}
		List<ContractModel> convertedEntitys = new ArrayList<>();
		for (Contract entity : allEntitys) {
			convertedEntitys.add(entity2model(entity));
		}
		// System.out.println(Locale.getDefault());
		return new ResponseEntity<List<ContractModel>>(convertedEntitys, HttpStatus.OK);

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
		LOGGER.info("User id is is defined as id = [{}].", authorisedUserId);

		LOGGER.info("UserAuthorization in {}: Verification of access rights.", thisClassName);

		// Clarify the userROLE for obtaining permission to use the method
		Role authorisedUserRole = userJVMDataStorage.getRole();

		// Clarify the userAuthId for obtaining permission to use the method
		// to get customer Id
		Contract entity = null;
		try {
			entity = service.getContract(entityIdParam);
		} catch (Exception e) {
			String msg = e.getMessage();
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}
		Integer customerId = entity.getCustomerId();
		Integer customerAsUserId = authService.getByCustomerId(customerId).getId();
		/*
		 * System.out.println("authorisedUserId " + authorisedUserId);
		 * System.out.println("customerAsUserId " + customerAsUserId);
		 * System.out.println("authorisedUserRol e" + authorisedUserRole);
		 * System.out.println("Role.CUSTOMER " + Role.CUSTOMER);
		 */
		Boolean isIdEq = customerAsUserId.equals(authorisedUserId);
		Boolean roleEq = authorisedUserRole.equals(Role.CUSTOMER);
		Boolean dataEq = isIdEq && roleEq;

		Boolean b = (isIdEq || authorisedUserRole == null || validUserRoles.contains(authorisedUserRole));
		/*
		 * System.out.println("isIdEq " + isIdEq); System.out.println("roleEq "
		 * + roleEq); System.out.println("dataEq " + dataEq);
		 * System.out.println("b " + b);
		 */
		if (!b) {
			String msg = String.format(
					"No access rights. Access is available only to users: %s (for CUSTOMERS only own profile is available).",
					EnumArrayToMessageConvertor.validRoleArrayToMessage(validUserRoles));
			return new ResponseEntity<String>(msg, HttpStatus.FORBIDDEN);
		}
		LOGGER.info("User role is defined as role = [{}].", authorisedUserRole);
		LOGGER.info("Finish UserAuthorization in {}. User with id = {} and role = {} makes requests = {}",
				thisClassName, authorisedUserId, authorisedUserRole, requestName);

		// Direct implementation of the method

		ContractModel entityModel = entity2model(entity);
		return new ResponseEntity<ContractModel>(entityModel, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody ContractModel entityModel) {

		// Start ControllerAuthorization
		Set<Role> validUserRoles = new HashSet<>();
		{
			validUserRoles.add(Role.ADMIN);
			validUserRoles.add(Role.SALES_MANAGER);
			validUserRoles.add(Role.CUSTOMER);
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
		LOGGER.info("User id is is defined as id = [{}].", authorisedUserId);

		LOGGER.info("UserAuthorization in {}: Verification of access rights.", thisClassName);
		// Clarify the userROLE for obtaining permission to use the method
		Role authorisedUserRole = userJVMDataStorage.getRole();
		if (authorisedUserRole == null || !validUserRoles.contains(authorisedUserRole)) {
			String msg = String.format("No access rights. Access is available only to users: %s.",
					EnumArrayToMessageConvertor.validRoleArrayToMessage(validUserRoles));
			return new ResponseEntity<String>(msg, HttpStatus.FORBIDDEN);
		}
		LOGGER.info("User role is defined as role = [{}].", authorisedUserRole);
		LOGGER.info("Finish UserAuthorization in {}. User with id = {} and role = {} makes requests = {}",
				thisClassName, authorisedUserId, authorisedUserRole, requestName);

		// Direct implementation of the method

		Contract entity = null;

		try {
			entity = model2entity(entityModel);
		} catch (Exception e) {
			String msg = String.format("PayForm [%s] is not allowed for insertion. Please use one of: [%s].",
					entityModel.getPayForm(), EnumArrayToMessageConvertor.payFormArrayToMessage());
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}

		if (entity.getCustomerId() == null) {
			Integer customerIdInContract = authService.get(authorisedUserId).getInOwnTableId();
			entity.setCustomerId(customerIdInContract);
		}

		service.saveContract(entity);

		return new ResponseEntity<IdModel>(new IdModel(entity.getId()), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody ContractModel entityModel,
			@PathVariable(value = "id") Integer entityIdParam) {
		// Start ControllerAuthorization
		Set<Role> validUserRoles = new HashSet<>();
		{
			validUserRoles.add(Role.ADMIN);
			validUserRoles.add(Role.SALES_MANAGER);
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
		LOGGER.info("User id is is defined as id = [{}].", authorisedUserId);

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
		LOGGER.info("Finish UserAuthorization in {}. User with id = {} and role = {} makes requests = {}",
				thisClassName, authorisedUserId, authorisedUserRole, requestName);

		// Direct implementation of the method

		Contract entity = null;

		try {
			entity = service.getContract(entityIdParam);
		} catch (Exception e) {
			String msg = e.getMessage();
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}

		try {
			if (entityModel.getContractStatus() != null) {
				entity.setContractStatus(ContractStatus.valueOf(entityModel.getContractStatus().toUpperCase()));
			}
			if (entityModel.getPayForm() != null) {
				entity.setPayForm(PayForm.valueOf(entityModel.getPayForm().toUpperCase()));
			}
			if (entityModel.getContractStatus() != null) {
				entity.setPayStatus(PayStatus.valueOf(entityModel.getPayStatus().toUpperCase()));
			}
		} catch (Exception e) {
			String msg = String.format(
					"ContractStatus [%s] OR PayForm [%s] OR PayStatus [%s] is not allowed for insertion. Please use"
							+ " for ContractStatus one of: [%s], for PayForm one of: [%s], for PayStatus one of: [%s].",
					entityModel.getContractStatus(), entityModel.getPayForm(), entityModel.getPayStatus(),
					EnumArrayToMessageConvertor.contractStatusArrayToMessage(),
					EnumArrayToMessageConvertor.payFormArrayToMessage(),
					EnumArrayToMessageConvertor.payStatusArrayToMessage());
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}

		if (entityModel.getCustomerId() != null) {
			entity.setCustomerId(entityModel.getCustomerId());
		}

		service.saveContract(entity);

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
		LOGGER.info("User id is is defined as id = [{}].", authorisedUserId);

		LOGGER.info("UserAuthorization in {}: Verification of access rights.", thisClassName);
		// Clarify the userROLE for obtaining permission to use the method
		Role authorisedUserRole = userJVMDataStorage.getRole();
		if (!validUserRoles.contains(authorisedUserRole)) {
			String msg = String.format("No access rights. Access is available only to users: %s).",
					EnumArrayToMessageConvertor.validRoleArrayToMessage(validUserRoles));
			return new ResponseEntity<String>(msg, HttpStatus.FORBIDDEN);
		}
		LOGGER.info("User role is defined as role = [{}].", authorisedUserRole);
		LOGGER.info("Finish UserAuthorization in {}. User with id = {} and role = {} makes requests = {}",
				thisClassName, authorisedUserId, authorisedUserRole, requestName);

		// Direct implementation of the method

		// checking of existing
		try {
			service.getContract(entityIdParam);
		} catch (IllegalArgumentException | EmptyResultDataAccessException e) {
			String msg = String.format("Preparation before DELETE: %s", e.getMessage());
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		service.deleteContract(entityIdParam);

		return new ResponseEntity<IdModel>(HttpStatus.OK);
	}

	private ContractModel entity2model(Contract entity) {
		ContractModel entityModel = new ContractModel();
		entityModel.setId(entity.getId());
		entityModel.setCreated(timestamp2String(entity.getCreated()));
		entityModel.setContractStatus(entity.getContractStatus().name());
		entityModel.setPayForm(entity.getPayForm().name());
		entityModel.setPayStatus(entity.getPayStatus().name());
		entityModel.setCustomerId(entity.getCustomerId());
		entityModel.setTotalPrice(entity.getTotalPrice());
		return entityModel;
	}

	private Contract model2entity(ContractModel entityModel) {
		Contract entity = new Contract();
		entity.setCreated(new Timestamp(new Date().getTime()));
		entity.setTotalPrice(new BigDecimal("0.00"));
		entity.setContractStatus(ContractStatus.CONTRACT_PREPARATION);
		// extract
		entity.setPayForm(PayForm.valueOf(entityModel.getPayForm().toUpperCase()));
		entity.setPayStatus(PayStatus.UNPAID);
		// maybe extract
		entity.setCustomerId(entityModel.getCustomerId());
		return entity;
	}

	private String timestamp2String(Timestamp tstmp) {
		Date date = new Date(tstmp.getTime());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String string = dateFormat.format(date);
		return string;

	}

}

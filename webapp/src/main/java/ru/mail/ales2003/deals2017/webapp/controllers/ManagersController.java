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

import ru.mail.ales2003.deals2017.dao.api.customentities.AuthorizedManager;
import ru.mail.ales2003.deals2017.datamodel.Manager;
import ru.mail.ales2003.deals2017.datamodel.Role;
import ru.mail.ales2003.deals2017.datamodel.UserAuth;
import ru.mail.ales2003.deals2017.services.IManagerService;
import ru.mail.ales2003.deals2017.services.IUserAuthService;
import ru.mail.ales2003.deals2017.services.impl.UserAuthStorage;
import ru.mail.ales2003.deals2017.services.servicesexceptions.DuplicationKeyInformationException;
import ru.mail.ales2003.deals2017.webapp.models.AuthorizedManagerModel;
import ru.mail.ales2003.deals2017.webapp.models.IdModel;
import ru.mail.ales2003.deals2017.webapp.models.ManagerModel;
import ru.mail.ales2003.deals2017.webapp.models.UserAuthModel;
import ru.mail.ales2003.deals2017.webapp.util.EnumArrayToMessageConvertor;

/**
 * @author Aliaksandr Vishneuski ales_2003@mail.ru
 *
 */
@RestController
@RequestMapping("/managers")
public class ManagersController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ManagersController.class);

	@Inject
	private ApplicationContext context;

	@Inject
	private IManagerService service;

	@Inject
	private IUserAuthService authService;

	private String thisClassName = ManagersController.class.getSimpleName();

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll() {

		// Start ControllerAuthorization
		Set<Role> validUserRoles = new HashSet<>();
		{
			validUserRoles.add(Role.ADMIN);
			validUserRoles.add(Role.REVENEUE_MANAGER);
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

		List<Manager> allEntitys;
		try {
			allEntitys = service.getAll();
		} catch (EmptyResultDataAccessException e) {
			String msg = String.format("[%s] storage returns incorrect entity count.", Manager.class);
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (allEntitys.isEmpty()) {
			String msg = String.format("An storage store of class [%s] is empty", Manager.class);
			return new ResponseEntity<String>(msg, HttpStatus.OK);
		}
		List<ManagerModel> convertedEntitys = new ArrayList<>();
		for (Manager entity : allEntitys) {
			convertedEntitys.add(entity2model(entity));
		}
		// System.out.println(Locale.getDefault());
		return new ResponseEntity<List<ManagerModel>>(convertedEntitys, HttpStatus.OK);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable(value = "id") Integer entityIdParam) {

		// Start ControllerAuthorization
		Set<Role> validUserRoles = new HashSet<>();
		{
			validUserRoles.add(Role.ADMIN);
			validUserRoles.add(Role.REVENEUE_MANAGER);
			validUserRoles.add(Role.SALES_MANAGER);
			validUserRoles.add(Role.ITEM_MANAGER);
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
		if ((entityIdParam != authorisedUserId && authorisedUserRole == Role.SALES_MANAGER)
				|| authorisedUserRole == null || !validUserRoles.contains(authorisedUserRole)) {
			String msg = String.format(
					"No access rights. Access is available only to users: %s (for SALES_MANAGERS only own profile is available).",
					EnumArrayToMessageConvertor.validRoleArrayToMessage(validUserRoles));
			return new ResponseEntity<String>(msg, HttpStatus.FORBIDDEN);
		}
		LOGGER.info("User role is defined as role = [{}].", authorisedUserRole);
		LOGGER.info("Finish UserAuthorization in {}. User with id = {} and role = {} makes requests = {}",
				thisClassName, authorisedUserId, authorisedUserRole, requestName);

		// Direct implementation of the method

		Manager entity = null;
		try {
			entity = service.get(entityIdParam);
		} catch (Exception e) {
			String msg = e.getMessage();
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}
		ManagerModel entityModel = entity2model(entity);
		return new ResponseEntity<ManagerModel>(entityModel, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody ManagerModel entityModel) {

		// Start ControllerAuthorization
		Set<Role> validUserRoles = new HashSet<>();
		{
			validUserRoles.add(Role.ADMIN);
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

		Manager entity = null;

		entity = model2entity(entityModel);
		service.save(entity);

		return new ResponseEntity<IdModel>(new IdModel(entity.getId()), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody ManagerModel entityModel,
			@PathVariable(value = "id") Integer entityIdParam) {
		// Start ControllerAuthorization
		Set<Role> validUserRoles = new HashSet<>();
		{
			validUserRoles.add(Role.ADMIN);
			validUserRoles.add(Role.ITEM_MANAGER);
			validUserRoles.add(Role.SALES_MANAGER);
			validUserRoles.add(Role.REVENEUE_MANAGER);
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
		if ((entityIdParam != authorisedUserId && authorisedUserRole == Role.SALES_MANAGER)
				|| (entityIdParam != authorisedUserId && authorisedUserRole == Role.ITEM_MANAGER)
				|| (entityIdParam != authorisedUserId && authorisedUserRole == Role.REVENEUE_MANAGER)
				|| authorisedUserRole == null || !validUserRoles.contains(authorisedUserRole)) {
			String msg = String.format(
					"No access rights. Access is available only to users: %s (for MANAGERS only own profile is available).",
					EnumArrayToMessageConvertor.validRoleArrayToMessage(validUserRoles));
			return new ResponseEntity<String>(msg, HttpStatus.FORBIDDEN);
		}
		LOGGER.info("User role is defined as role = [{}].", authorisedUserRole);
		LOGGER.info("Finish UserAuthorization in {}. User with id = {} and role = {} makes requests = {}",
				thisClassName, authorisedUserId, authorisedUserRole, requestName);

		// Direct implementation of the method

		Manager entity = null;

		try {
			entity = service.get(entityIdParam);
		} catch (Exception e) {
			String msg = e.getMessage();
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}

		entity.setFirstName(entityModel.getFirstName());
		entity.setPatronymic(entityModel.getPatronymic());
		entity.setLastName(entityModel.getLastName());
		entity.setPosition(entityModel.getPosition());

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
			service.get(entityIdParam);
		} catch (IllegalArgumentException | EmptyResultDataAccessException e) {
			String msg = String.format("Preparation before DELETE: %s", e.getMessage());
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// checking of exist account in AuthorisationService
		if (authService.getByManagerId(entityIdParam) != null) {
			UserAuth userAuth = authService.getByManagerId(entityIdParam);
			authService.delete(userAuth.getId());
		}

		service.delete(entityIdParam);

		return new ResponseEntity<IdModel>(HttpStatus.OK);
	}

	@RequestMapping(value = "/auth/manager", method = RequestMethod.POST)
	public ResponseEntity<?> createManagerWithAuthorizationData(
			@RequestBody AuthorizedManagerModel authorizedManagerModel) {

		// Start ControllerAuthorization
		Set<Role> validUserRoles = new HashSet<>();
		{
			validUserRoles.add(Role.ADMIN);
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
		LOGGER.info("User id is is defined as id = [{}].", authorisedUserId);

		LOGGER.info("UserAuthorization in {}: Verification of access rights.", thisClassName);
		// Clarify the userROLE for obtaining permission to use the method
		Role authorisedUserRole = userJVMDataStorage.getRole();
		if (!validUserRoles.contains(authorisedUserRole)) {
			String msg = String.format("No access rights. Access is available only to users: %s.",
					EnumArrayToMessageConvertor.validRoleArrayToMessage(validUserRoles));
			return new ResponseEntity<String>(msg, HttpStatus.FORBIDDEN);
		}
		LOGGER.info("User role is defined as role = [{}].", authorisedUserRole);
		LOGGER.info("Finish UserAuthorization in {}. User with id = {} and role = {} makes requests = {}",
				thisClassName, authorisedUserId, authorisedUserRole, requestName);

		// Direct implementation of the method

		AuthorizedManager authorizedManager = new AuthorizedManager();
		Manager entity = null;
		UserAuth data = null;

		ManagerModel entityModel = authorizedManagerModel.getManagerModel();
		entity = model2entity(entityModel);

		UserAuthModel dataModel = authorizedManagerModel.getAuthDataModel();
		data = dataModel2data(dataModel);

		authorizedManager.setManager(entity);
		authorizedManager.setAuthData(data);

		try {
			service.saveWithAuthorization(authorizedManager);
		} catch (DuplicationKeyInformationException e) {
			String msg = String.format(e.getAttachedMsg());
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<IdModel>(new IdModel(authorizedManager.getManagerId()), HttpStatus.CREATED);
	}

	/*
	 * // ==========================STUB - FOR TESTING=======================
	 * 
	 * @RequestMapping(value = "/auth", method = RequestMethod.GET) public
	 * ResponseEntity<?> getManagerWithAuthorizationData() {
	 * 
	 * Manager m = new Manager(); m.setId(1000); m.setFirstName("firstName");
	 * m.setPatronymic("patronymic");
	 * 
	 * UserAuth u = new UserAuth(); u.setRole(Role.SALES_MANAGER);
	 * u.setLogin("login"); u.setPassword("password");
	 * 
	 * AuthorizedManager entity = new AuthorizedManager();
	 * entity.setManagerId(100); entity.setAuthData(u); entity.setManager(m);
	 * 
	 * ManagerModel mm = entity2model(m); UserAuthModel uam = data2dataModel(u);
	 * 
	 * AuthorizedManagerModel mainModel = new AuthorizedManagerModel();
	 * mainModel.setManagerId(m.getId()); mainModel.setManagerModel(mm);
	 * mainModel.setAuthDataModel(uam);
	 * 
	 * return new ResponseEntity<AuthorizedManagerModel>(mainModel,
	 * HttpStatus.OK); }
	 */

	@RequestMapping(value = "/auth", method = RequestMethod.GET)
	public ResponseEntity<?> getAllAuthorizationData() {

		// Start ControllerAuthorization
		Set<Role> validUserRoles = new HashSet<>();
		{
			validUserRoles.add(Role.ADMIN);
		}

		String requestName = "getAllAuthorizationData";

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
			String msg = String.format("No access rights. Access is available only to users: %s.",
					EnumArrayToMessageConvertor.validRoleArrayToMessage(validUserRoles));
			return new ResponseEntity<String>(msg, HttpStatus.FORBIDDEN);
		}
		LOGGER.info("User role is defined as role = [{}].", authorisedUserRole);
		LOGGER.info("Finish UserAuthorization in {}. User with id = {} and role = {} makes requests = {}",
				thisClassName, authorisedUserId, authorisedUserRole, requestName);

		// Direct implementation of the method

		List<UserAuth> allEntitys;
		try {
			allEntitys = authService.getAll();
		} catch (EmptyResultDataAccessException e) {
			String msg = String.format("[%s] storage returns incorrect entity count.", UserAuth.class);
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (allEntitys.isEmpty()) {
			String msg = String.format("An storage store of class [%s] is empty", UserAuth.class);
			return new ResponseEntity<String>(msg, HttpStatus.OK);
		}
		List<UserAuthModel> convertedEntitys = new ArrayList<>();
		for (UserAuth entity : allEntitys) {
			convertedEntitys.add(data2dataModel(entity));
		}
		// System.out.println(Locale.getDefault());
		return new ResponseEntity<List<UserAuthModel>>(convertedEntitys, HttpStatus.OK);

	}

	@RequestMapping(value = "/auth/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getAuthorizationDataById(@PathVariable(value = "id") Integer entityIdParam) {

		// Start ControllerAuthorization
		Set<Role> validUserRoles = new HashSet<>();
		{
			validUserRoles.add(Role.ADMIN);
		}

		String requestName = "getAuthorizationDataById";

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
			String msg = String.format("No access rights. Access is available only to users: %s.",
					EnumArrayToMessageConvertor.validRoleArrayToMessage(validUserRoles));
			return new ResponseEntity<String>(msg, HttpStatus.FORBIDDEN);
		}
		LOGGER.info("User role is defined as role = [{}].", authorisedUserRole);
		LOGGER.info("Finish UserAuthorization in {}. User with id = {} and role = {} makes requests = {}",
				thisClassName, authorisedUserId, authorisedUserRole, requestName);

		// Direct implementation of the method

		UserAuth entity = null;
		try {
			entity = authService.get(entityIdParam);
		} catch (Exception e) {
			String msg = e.getMessage();
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}
		UserAuthModel authDataModel = data2dataModel(entity);
		return new ResponseEntity<UserAuthModel>(authDataModel, HttpStatus.OK);
	}

	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthorizationData(@RequestBody UserAuthModel entityModel) {

		// Start ControllerAuthorization
		Set<Role> validUserRoles = new HashSet<>();
		{
			validUserRoles.add(Role.ADMIN);
		}

		String requestName = "createAuthorizationData";

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
			String msg = String.format("No access rights. Access is available only to users: %s.",
					EnumArrayToMessageConvertor.validRoleArrayToMessage(validUserRoles));
			return new ResponseEntity<String>(msg, HttpStatus.FORBIDDEN);
		}
		LOGGER.info("User role is defined as role = [{}].", authorisedUserRole);
		LOGGER.info("Finish UserAuthorization in {}. User with id = {} and role = {} makes requests = {}",
				thisClassName, authorisedUserId, authorisedUserRole, requestName);

		// Direct implementation of the method

		UserAuth entity = null;

		entity = dataModel2data(entityModel);

		try {
			authService.save(entity);
		} catch (DuplicationKeyInformationException e) {
			String msg = String.format(e.getAttachedMsg());
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<IdModel>(new IdModel(entity.getId()), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/auth/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateAuthorizationData(@RequestBody UserAuthModel entityModel,
			@PathVariable(value = "id") Integer entityIdParam) {
		// Start ControllerAuthorization
		Set<Role> validUserRoles = new HashSet<>();
		{
			validUserRoles.add(Role.ADMIN);
		}

		String requestName = "updateAuthorizationData";

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
			String msg = String.format("No access rights. Access is available only to users: %s.",
					EnumArrayToMessageConvertor.validRoleArrayToMessage(validUserRoles));
			return new ResponseEntity<String>(msg, HttpStatus.FORBIDDEN);
		}
		LOGGER.info("User role is defined as role = [{}].", authorisedUserRole);
		LOGGER.info("Finish UserAuthorization in {}. User with id = {} and role = {} makes requests = {}",
				thisClassName, authorisedUserId, authorisedUserRole, requestName);

		// Direct implementation of the method

		UserAuth entity = null;
		try {
			entity = authService.get(entityIdParam);
		} catch (Exception e) {
			String msg = e.getMessage();
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}

		entity.setLogin(entityModel.getLogin());

		entity.setPassword(entityModel.getPassword());
		try {
			entity.setRole(Role.valueOf(entityModel.getRole()));

			authService.save(entity);
		} catch (Exception e) {
			String msg = String.format("Role with name [%s] is not allowed for insertion. Please use one of: [%s].",
					entityModel.getRole(), EnumArrayToMessageConvertor.roleArrayToMessage());
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<IdModel>(HttpStatus.OK);
	}

	@RequestMapping(value = "/auth/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteAuthorizationData(@PathVariable(value = "id") Integer entityIdParam) {

		// Start ControllerAuthorization
		Set<Role> validUserRoles = new HashSet<>();
		{
			validUserRoles.add(Role.ADMIN);
		}

		String requestName = "deleteAuthorizationData";

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

		// checking of existing
		try {
			authService.getByManagerId(entityIdParam);
		} catch (IllegalArgumentException | EmptyResultDataAccessException e) {
			String msg = String.format("Preparation before DELETE: %s", e.getMessage());
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		authService.delete(entityIdParam);
		return new ResponseEntity<IdModel>(HttpStatus.OK);
	}

	/**
	 * @param entity
	 *            - the entity of the class Manager
	 * 
	 * @return ManagerModel entityModel converted from entity
	 */
	private ManagerModel entity2model(Manager entity) {
		ManagerModel entityModel = new ManagerModel();
		entityModel.setId(entity.getId());
		entityModel.setFirstName(entity.getFirstName());
		entityModel.setPatronymic(entity.getPatronymic());
		entityModel.setLastName(entity.getLastName());
		entityModel.setPosition(entity.getPosition());
		return entityModel;
	}

	/**
	 * @param entityModel
	 *            the entity model of the class ManagerModel.
	 * @return the entity of the class Manager.
	 */
	private Manager model2entity(ManagerModel entityModel) {
		Manager entity = new Manager();
		entity.setFirstName(entityModel.getFirstName());
		entity.setPatronymic(entityModel.getPatronymic());
		entity.setLastName(entityModel.getLastName());
		entity.setPosition(entityModel.getPosition());
		return entity;
	}

	private UserAuth dataModel2data(UserAuthModel dataModel) {
		UserAuth data = new UserAuth();
		data.setInOwnTableId(dataModel.getInOwnTableId());
		data.setRole(Role.valueOf(dataModel.getRole()));
		data.setLogin(dataModel.getLogin());
		data.setPassword(dataModel.getPassword());
		return data;
	}

	private UserAuthModel data2dataModel(UserAuth data) {
		UserAuthModel dataModel = new UserAuthModel();
		dataModel.setId(data.getId());
		dataModel.setInOwnTableId(data.getInOwnTableId());
		dataModel.setRole(data.getRole().name());
		dataModel.setLogin(data.getLogin());
		dataModel.setPassword(data.getPassword());
		return dataModel;

	}

}

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

import ru.mail.ales2003.deals2017.datamodel.Manager;
import ru.mail.ales2003.deals2017.datamodel.Role;
import ru.mail.ales2003.deals2017.services.IManagerService;
import ru.mail.ales2003.deals2017.services.IUserAuthService;
import ru.mail.ales2003.deals2017.services.impl.UserAuthStorage;
import ru.mail.ales2003.deals2017.webapp.models.IdModel;
import ru.mail.ales2003.deals2017.webapp.models.ManagerModel;
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
		UserAuthStorage userAuthStorage = context.getBean(UserAuthStorage.class);
		// Getting authorisedUserID
		Integer authorisedUserId = userAuthStorage.getId();
		if (authorisedUserId == null) {
			String msg = String.format("No authorization. Authorization is required to access this section.");
			return new ResponseEntity<String>(msg, HttpStatus.UNAUTHORIZED);
		}
		LOGGER.info("User with id = [{}] makes requests [{}] in [{}]", authorisedUserId, requestName, thisClassName);
		LOGGER.info("UserAuthorizationin {}: Verification of access rights.", thisClassName);
		// Clarify the userROLE for obtaining permission to use the method
		if (authService.get(authorisedUserId).getRole() == null
				|| !validUserRoles.contains(authService.get(authorisedUserId).getRole())) {
			String msg = String.format("No access rights. Access is available only to users: %s.",
					EnumArrayToMessageConvertor.validRoleArrayToMessage(validUserRoles));
			return new ResponseEntity<String>(msg, HttpStatus.FORBIDDEN);
		}
		LOGGER.info("Finish UserAuthorization in {}. User with id = {} makes requests = {}", thisClassName,
				userAuthStorage.getId(), requestName);

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
		UserAuthStorage userAuthStorage = context.getBean(UserAuthStorage.class);
		// Getting authorisedUserID
		Integer authorisedUserId = userAuthStorage.getId();
		if (authorisedUserId == null) {
			String msg = String.format("No authorization. Authorization is required to access this section.");
			return new ResponseEntity<String>(msg, HttpStatus.UNAUTHORIZED);
		}
		LOGGER.info("User with id = [{}] makes requests [{}] in [{}]", authorisedUserId, requestName, thisClassName);
		LOGGER.info("UserAuthorizationin {}: Verification of access rights.", thisClassName);
		// Clarify the userROLE for obtaining permission to use the method
		if ((entityIdParam != authorisedUserId && authService.get(authorisedUserId).getRole() == Role.SALES_MANAGER)
				|| authService.get(authorisedUserId).getRole() == null
				|| !validUserRoles.contains(authService.get(authorisedUserId).getRole())) {
			String msg = String.format(
					"No access rights. Access is available only to users: %s (for SALES_MANAGERS only own profile is available).",
					EnumArrayToMessageConvertor.validRoleArrayToMessage(validUserRoles));
			return new ResponseEntity<String>(msg, HttpStatus.FORBIDDEN);
		}
		LOGGER.info("Finish UserAuthorization in {}. User with id = {} makes requests = {}", thisClassName,
				userAuthStorage.getId(), requestName);

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
		UserAuthStorage userAuthStorage = context.getBean(UserAuthStorage.class);
		// Getting authorisedUserID
		Integer authorisedUserId = userAuthStorage.getId();
		if (authorisedUserId == null) {
			String msg = String.format("No authorization. Authorization is required to access this section.");
			return new ResponseEntity<String>(msg, HttpStatus.UNAUTHORIZED);
		}
		LOGGER.info("User with id = [{}] makes requests [{}] in [{}]", authorisedUserId, requestName, thisClassName);
		LOGGER.info("UserAuthorizationin {}: Verification of access rights.", thisClassName);
		// Clarify the userROLE for obtaining permission to use the method
		if (authService.get(authorisedUserId).getRole() == null
				|| !validUserRoles.contains(authService.get(authorisedUserId).getRole())) {
			String msg = String.format("No access rights. Access is available only to users: %s.",
					EnumArrayToMessageConvertor.validRoleArrayToMessage(validUserRoles));
			return new ResponseEntity<String>(msg, HttpStatus.FORBIDDEN);
		}
		LOGGER.info("Finish UserAuthorization in {}. User with id = {} makes requests = {}", thisClassName,
				userAuthStorage.getId(), requestName);

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

		String requestName = "getAll()";

		LOGGER.info("Start UserAuthorization in {}: Extracting userId.", thisClassName);
		UserAuthStorage userAuthStorage = context.getBean(UserAuthStorage.class);
		// Getting authorisedUserID
		Integer authorisedUserId = userAuthStorage.getId();
		if (authorisedUserId == null) {
			String msg = String.format("No authorization. Authorization is required to access this section.");
			return new ResponseEntity<String>(msg, HttpStatus.UNAUTHORIZED);
		}
		LOGGER.info("User with id = [{}] makes requests [{}] in [{}]", authorisedUserId, requestName, thisClassName);
		LOGGER.info("UserAuthorizationin {}: Verification of access rights.", thisClassName);
		// Clarify the userROLE for obtaining permission to use the method
		if ((entityIdParam != authorisedUserId && authService.get(authorisedUserId).getRole() == Role.SALES_MANAGER)
				|| (entityIdParam != authorisedUserId
						&& authService.get(authorisedUserId).getRole() == Role.ITEM_MANAGER)
				|| (entityIdParam != authorisedUserId
						&& authService.get(authorisedUserId).getRole() == Role.REVENEUE_MANAGER)
				|| authService.get(authorisedUserId).getRole() == null
				|| !validUserRoles.contains(authService.get(authorisedUserId).getRole())) {
			String msg = String.format(
					"No access rights. Access is available only to users: %s (for MANAGERS only own profile is available).",
					EnumArrayToMessageConvertor.validRoleArrayToMessage(validUserRoles));
			return new ResponseEntity<String>(msg, HttpStatus.FORBIDDEN);
		}
		LOGGER.info("Finish UserAuthorization in {}. User with id = {} makes requests = {}", thisClassName,
				userAuthStorage.getId(), requestName);

		Manager entity = service.get(entityIdParam);

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

		String requestName = "getAll()";

		LOGGER.info("Start UserAuthorization in {}: Extracting userId.", thisClassName);
		UserAuthStorage userAuthStorage = context.getBean(UserAuthStorage.class);
		// Getting authorisedUserID
		Integer authorisedUserId = userAuthStorage.getId();
		if (authorisedUserId == null) {
			String msg = String.format("No authorization. Authorization is required to access this section.");
			return new ResponseEntity<String>(msg, HttpStatus.UNAUTHORIZED);
		}
		LOGGER.info("User with id = [{}] makes requests [{}] in [{}]", authorisedUserId, requestName, thisClassName);
		LOGGER.info("UserAuthorizationin {}: Verification of access rights.", thisClassName);
		// Clarify the userROLE for obtaining permission to use the method
		if (!validUserRoles.contains(authService.get(authorisedUserId).getRole())) {
			String msg = String.format("No access rights. Access is available only to users: %s).",
					EnumArrayToMessageConvertor.validRoleArrayToMessage(validUserRoles));
			return new ResponseEntity<String>(msg, HttpStatus.FORBIDDEN);
		}
		LOGGER.info("Finish UserAuthorization in {}. User with id = {} makes requests = {}", thisClassName,
				userAuthStorage.getId(), requestName);

		service.delete(entityIdParam);
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

}

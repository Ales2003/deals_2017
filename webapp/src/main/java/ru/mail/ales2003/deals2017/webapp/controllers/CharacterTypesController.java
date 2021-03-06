package ru.mail.ales2003.deals2017.webapp.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
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

import ru.mail.ales2003.deals2017.datamodel.CharacterType;
import ru.mail.ales2003.deals2017.datamodel.Measure;
import ru.mail.ales2003.deals2017.datamodel.Role;
import ru.mail.ales2003.deals2017.services.ICharacterTypeService;
import ru.mail.ales2003.deals2017.services.IUserAuthService;
import ru.mail.ales2003.deals2017.services.impl.UserAuthStorage;
import ru.mail.ales2003.deals2017.services.servicesexceptions.DuplicationKeyInformationException;
import ru.mail.ales2003.deals2017.webapp.models.CharacterTypeModel;
import ru.mail.ales2003.deals2017.webapp.models.IdModel;
import ru.mail.ales2003.deals2017.webapp.translate.StaticTranslator;
import ru.mail.ales2003.deals2017.webapp.util.EnumArrayToMessageConvertor;

/**
 * @author Aliaksandr Vishneuski ales_2003@mail.ru
 *
 */
@RestController
@RequestMapping("/charactertypes")
public class CharacterTypesController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CharacterTypesController.class);

	@Inject
	private ApplicationContext context;

	// this variable need to get his value instead hard but dynamically - from a
	// request header.
	private Locale locale = new Locale("ru_RU");

	@Inject
	private StaticTranslator translator;

	@Inject
	private ICharacterTypeService service;

	@Inject
	private IUserAuthService authService;

	private String thisClassName = CharacterTypesController.class.getSimpleName();

	/**
	 * @return List&ltCharacterTypeModel&gt convertedEntitys
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll() {

		// Start ControllerAuthorization
		
		Set<Role> validUserRoles = new HashSet<>();
		{
			validUserRoles.add(Role.ADMIN);
			validUserRoles.add(Role.ITEM_MANAGER);
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

		List<CharacterType> allEntitys;
		try {
			allEntitys = service.getAll();
		} catch (EmptyResultDataAccessException e) {
			String msg = String.format("[%s] storage returns incorrect entity count.", CharacterType.class);
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (allEntitys.isEmpty()) {
			String msg = String.format("An entity storage of class [%s] is empty", CharacterType.class);
			return new ResponseEntity<String>(msg, HttpStatus.OK);
		}
		List<CharacterTypeModel> convertedEntitys = new ArrayList<>();
		for (CharacterType entity : allEntitys) {
			convertedEntitys.add(entity2model(entity));
		}
		// System.out.println(Locale.getDefault());
		return new ResponseEntity<List<CharacterTypeModel>>(convertedEntitys, HttpStatus.OK);
	}

	/**
	 * @param entityIdParam
	 *            = entity id
	 * @return CharacterTypeModel entity
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable(value = "id") Integer entityIdParam) {

		// Start ControllerAuthorization
		Set<Role> validUserRoles = new HashSet<>();
		{
			validUserRoles.add(Role.ADMIN);
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

		CharacterType entity = null;
		try {
			entity = service.get(entityIdParam);
		} catch (Exception e) {
			String msg = e.getMessage();
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}
		CharacterTypeModel entityModel = entity2model(entity);
		return new ResponseEntity<CharacterTypeModel>(entityModel, HttpStatus.OK);
	}

	/**
	 * @param entityModel
	 * @return saved entity. The method only worries about the name and ignores
	 *         Id: method can't update - only it can create.
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody CharacterTypeModel entityModel) {

		// Start ControllerAuthorization
		Set<Role> validUserRoles = new HashSet<>();
		{
			validUserRoles.add(Role.ADMIN);
			validUserRoles.add(Role.ITEM_MANAGER);
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

		CharacterType entity = null;
		try {
			entity = model2entity(entityModel);
		} catch (Exception e) {
			String msg = String.format(
					"Character type with name [%s] is not allowed for insertion. Please use one of: [%s].",
					entityModel.getName(), EnumArrayToMessageConvertor.measureArrayToMessage());
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}
		try {
			service.save(entity);
		} catch (DuplicationKeyInformationException e) {
			String msg = e.getAttachedMsg() + " " + e.getMessage();
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<IdModel>(new IdModel(entity.getId()), HttpStatus.CREATED);
	}

	/**
	 * @param entityModel
	 * @param entityIdParam
	 * @return id saved in Db CharacterType entity.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody CharacterTypeModel entityModel,
			@PathVariable(value = "id") Integer entityIdParam) {

		// Start ControllerAuthorization
		Set<Role> validUserRoles = new HashSet<>();
		{
			validUserRoles.add(Role.ADMIN);
			validUserRoles.add(Role.ITEM_MANAGER);
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
			String msg = String.format("No access rights. Access is available only to users: %s.",
					EnumArrayToMessageConvertor.validRoleArrayToMessage(validUserRoles));
			return new ResponseEntity<String>(msg, HttpStatus.FORBIDDEN);
		}
		LOGGER.info("User role is defined as role = [{}].", authorisedUserRole);
		LOGGER.info("Finish UserAuthorization in [{}]. User with id = [{}] makes request = [{}]", thisClassName,
				userJVMDataStorage.getId(), requestName);

		// Direct implementation of the method

		CharacterType entity = null;

		entity = service.get(entityIdParam);

		try {
			// method toUpperCase() allows to insert in any register
			entity.setName(Measure.valueOf(entityModel.getName().toUpperCase()));
		} catch (Exception e) {
			String msg = String.format(
					"Character type with name [%s] is not allowed for insertion. Please use one of: [%s].",
					entityModel.getName(), EnumArrayToMessageConvertor.measureArrayToMessage());
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}
		service.save(entity);
		return new ResponseEntity<IdModel>(HttpStatus.OK);
	}

	/**
	 * @param entityIdParam
	 * @return IdModel of deleted entity.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable(value = "id") Integer entityIdParam) {

		// Start ControllerAuthorization
		Set<Role> validUserRoles = new HashSet<>();
		{
			validUserRoles.add(Role.ADMIN);
			validUserRoles.add(Role.ITEM_MANAGER);
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
		if (authorisedUserRole == null || !validUserRoles.contains(authorisedUserRole)) {
			String msg = String.format("No access rights. Access is available only to users: %s.",
					EnumArrayToMessageConvertor.validRoleArrayToMessage(validUserRoles));
			return new ResponseEntity<String>(msg, HttpStatus.FORBIDDEN);
		}
		LOGGER.info("User role is defined as role = [{}].", authorisedUserRole);
		LOGGER.info("Finish UserAuthorization in [{}]. User with id = [{}] makes request = [{}]", thisClassName,
				userJVMDataStorage.getId(), requestName);

		// Direct implementation of the method

		service.delete(entityIdParam);
		return new ResponseEntity<IdModel>(HttpStatus.OK);
	}

	/**
	 * @param entity
	 *            - the entity of the class CharacterType
	 * 
	 * @return CharacterTypeModel entityModel converted from entity
	 */
	private CharacterTypeModel entity2model(CharacterType entity) {
		CharacterTypeModel entityModel = new CharacterTypeModel();
		entityModel.setId(entity.getId());
		entityModel.setName(entity.getName() == null ? null : StaticTranslator.translate(entity.getName().name(), locale));
		return entityModel;
	}

	/**
	 * @param entityModel
	 *            the entity model of the class CharacterType.
	 * @return the entity of the class CharacterType.
	 */
	private CharacterType model2entity(CharacterTypeModel entityModel) {
		CharacterType entity = new CharacterType();
		// method toUpperCase() allows to insert in any register
		entity.setName(Measure.valueOf(entityModel.getName().toUpperCase()));
		return entity;
	}

}

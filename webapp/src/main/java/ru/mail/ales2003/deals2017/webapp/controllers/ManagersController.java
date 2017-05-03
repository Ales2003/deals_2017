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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.mail.ales2003.deals2017.datamodel.CharacterType;
import ru.mail.ales2003.deals2017.datamodel.Manager;
import ru.mail.ales2003.deals2017.datamodel.Measure;
import ru.mail.ales2003.deals2017.datamodel.Role;
import ru.mail.ales2003.deals2017.services.IManagerService;
import ru.mail.ales2003.deals2017.services.IUserAuthService;
import ru.mail.ales2003.deals2017.services.impl.UserAuthStorage;
import ru.mail.ales2003.deals2017.webapp.models.CharacterTypeModel;
import ru.mail.ales2003.deals2017.webapp.models.ManagerModel;

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
	IUserAuthService authService;

	private String thisClassName = ManagersController.class.getSimpleName();

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll() {

		// Start ControllerAuthorization
		Set<Role> validUserRoles = new HashSet<>();
		{
			validUserRoles.add(Role.ADMIN);
			validUserRoles.add(Role.REVENEUE_MANAGER);
		}

		String requestName = "getAll()";

		LOGGER.info("Start UserAuthorization in {}: Extracting userId.", thisClassName);
		UserAuthStorage userAuthStorage = context.getBean(UserAuthStorage.class);
		// Getting authorisedUserId
		Integer authorisedUserId = userAuthStorage.getId();
		if (authorisedUserId == null) {
			String msg = String.format("No authorization. Authorization is required to access this section.");
			return new ResponseEntity<String>(msg, HttpStatus.UNAUTHORIZED);

		}
		LOGGER.info("User with id = [{}] makes requests [{}] in [{}]", authorisedUserId, requestName, thisClassName);

		LOGGER.info("UserAuthorizationin {}: Verification of access rights.", thisClassName);
		// Obtaining permission to use the method
		if (!validUserRoles.contains(authService.get(authorisedUserId).getRole())) {
			String msg = String.format("No access rights. Access is available onley to users: {}.",
					validUserRoles.toString());
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

	/**
	 * @param entity
	 *            - the entity of the class CharacterType
	 * 
	 * @return CharacterTypeModel entityModel converted from entity
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

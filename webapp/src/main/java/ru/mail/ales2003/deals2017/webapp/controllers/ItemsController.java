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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.mail.ales2003.deals2017.datamodel.Item;
import ru.mail.ales2003.deals2017.datamodel.Role;
import ru.mail.ales2003.deals2017.services.IItemService;
import ru.mail.ales2003.deals2017.services.IUserAuthService;
import ru.mail.ales2003.deals2017.services.impl.UserAuthStorage;
import ru.mail.ales2003.deals2017.webapp.models.IdModel;
import ru.mail.ales2003.deals2017.webapp.models.ItemModel;
import ru.mail.ales2003.deals2017.webapp.translate.StaticTranslator;
import ru.mail.ales2003.deals2017.webapp.util.EnumArrayToMessageConvertor;

@RestController
@RequestMapping("/items")
public class ItemsController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemsController.class);

	@Inject
	private ApplicationContext context;

	// this variable need to get his value instead hard but dynamically - from a
	// request header.
	private Locale locale = new Locale("ru_RU");

	private Locale locFromRequest;
	
	@Inject
	private StaticTranslator translator;

	@Inject
	private IItemService service;

	@Inject
	private IUserAuthService authService;

	private String thisClassName = ItemsController.class.getSimpleName();

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll(@RequestParam(required = false) String name, Locale locFromRequest) {

		
		
		// Start ControllerAuthorization

		Set<Role> validUserRoles = new HashSet<>();
		{
			validUserRoles.add(Role.ADMIN);
			//validUserRoles.add(Role.REVENUE_MANAGER);
			validUserRoles.add(Role.ITEM_MANAGER);
			//validUserRoles.add(Role.SALES_MANAGER);
			//validUserRoles.add(Role.CUSTOMER);
			//validUserRoles.add(Role.GUEST);
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
		LOGGER.info("User id is  defined as id = [{}].", authorisedUserId);

		LOGGER.info("UserAuthorization in {}: Verification of access rights.", thisClassName);
		// Clarify the userROLE for obtaining permission to use the method
		Role authorisedUserRole = userJVMDataStorage.getRole();
		if (authorisedUserRole == null || !validUserRoles.contains(authorisedUserRole)) {
			String msg = String.format("No access rights. Access is available only to users: %s.",
					EnumArrayToMessageConvertor.validRoleArrayToMessage(validUserRoles));
			return new ResponseEntity<String>(msg, HttpStatus.FORBIDDEN);
		}
		LOGGER.info("User role is defined as role = [{}].", authorisedUserRole);
		LOGGER.info("Finish UserAuthorization in {}. User with id = {} makes request = {}", thisClassName,
				userJVMDataStorage.getId(), requestName);

		// Direct implementation of the method

		List<Item> allItems;

		allItems = service.getAll();

		List<ItemModel> convertedItems = new ArrayList<>();
		for (Item item : allItems) {
			convertedItems.add(entity2model(item));
		}

		return new ResponseEntity<List<ItemModel>>(convertedItems, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable(value = "id") Integer entityIdParam) {

		// Start ControllerAuthorization
		Set<Role> validUserRoles = new HashSet<>();
		{
			validUserRoles.add(Role.ADMIN);
			//validUserRoles.add(Role.REVENUE_MANAGER);
			validUserRoles.add(Role.ITEM_MANAGER);
			//validUserRoles.add(Role.SALES_MANAGER);
			// validUserRoles.add(Role.CUSTOMER);
			// validUserRoles.add(Role.GUEST);
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

		Item entity = null;
		try {
			entity = service.get(entityIdParam);
		} catch (Exception e) {
			String msg = e.getMessage();
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}
		ItemModel entityModel = entity2model(entity);
		return new ResponseEntity<ItemModel>(entityModel, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody ItemModel entityModel) {

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

		Item entity = null;

		entity = model2entity(entityModel);

		service.save(entity);

		return new ResponseEntity<IdModel>(new IdModel(entity.getId()), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody ItemModel entityModel,
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

		Item entity = null;

		entity = service.get(entityIdParam);

		entity.setName(entityModel.getName());
		entity.setDescription(entityModel.getDescription());
		entity.setBasicPrice(entityModel.getBasicPrice());

		service.save(entity);
		return new ResponseEntity<IdModel>(HttpStatus.OK);
	}

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

	private ItemModel entity2model(Item item) {
		ItemModel itemModel = new ItemModel();

		itemModel.setId(item.getId());
		
		String translatedItemName = StaticTranslator.translate(item.getName(), locFromRequest);
		
		itemModel.setName(item.getName());
		itemModel.setDescription(item.getDescription());
		itemModel.setBasicPrice(item.getBasicPrice());

		return itemModel;
	}

	private Item model2entity(ItemModel itemModel) {
		Item item = new Item();

		item.setName(itemModel.getName());
		item.setDescription(itemModel.getDescription());
		item.setBasicPrice(itemModel.getBasicPrice());
		return item;
	}

}

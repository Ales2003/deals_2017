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

import ru.mail.ales2003.deals2017.datamodel.Attribute;
import ru.mail.ales2003.deals2017.datamodel.CharacterTypeInItemVariant;
import ru.mail.ales2003.deals2017.datamodel.ItemVariant;
import ru.mail.ales2003.deals2017.datamodel.Role;
import ru.mail.ales2003.deals2017.services.IItemVariantService;
import ru.mail.ales2003.deals2017.services.IUserAuthService;
import ru.mail.ales2003.deals2017.services.impl.UserAuthStorage;
import ru.mail.ales2003.deals2017.webapp.models.CharacterTypeInItemVariantModel;
import ru.mail.ales2003.deals2017.webapp.models.IdModel;
import ru.mail.ales2003.deals2017.webapp.models.ItemVariantModel;
import ru.mail.ales2003.deals2017.webapp.translate.StaticTranslator;
import ru.mail.ales2003.deals2017.webapp.util.EnumArrayToMessageConvertor;

@RestController
@RequestMapping("/itemvariants")
public class ItemVariantsController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemVariantsController.class);

	@Inject
	private ApplicationContext context;

	// this variable need to get his value instead hard but dynamically - from a
	// request header.
	private Locale locale = new Locale("ru_RU");

	@Inject
	private StaticTranslator translator;

	@Inject
	private IItemVariantService service;

	@Inject
	private IUserAuthService authService;

	private String thisClassName = ItemVariantsController.class.getSimpleName();

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll(@RequestParam(required = false) String name) {

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
		LOGGER.info("Finish UserAuthorization in {}. User with id = {} makes request = {}", thisClassName,
				userJVMDataStorage.getId(), requestName);

		// Direct implementation of the method

		List<ItemVariant> allItems;

		allItems = service.getAllItemVariants();

		List<ItemVariantModel> convertedItems = new ArrayList<>();
		for (ItemVariant item : allItems) {
			convertedItems.add(entity2model(item));
		}

		return new ResponseEntity<List<ItemVariantModel>>(convertedItems, HttpStatus.OK);
	}

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
		LOGGER.info("Finish UserAuthorization in [{}]. User with id = [{}] makes request = [{}]", thisClassName,
				userJVMDataStorage.getId(), requestName);

		// Direct implementation of the method

		ItemVariant entity = null;
		try {
			entity = service.getItemVariant(entityIdParam);
		} catch (Exception e) {
			String msg = e.getMessage();
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}
		ItemVariantModel entityModel = entity2model(entity);
		return new ResponseEntity<ItemVariantModel>(entityModel, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody ItemVariantModel entityModel) {

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
		LOGGER.info("Finish UserAuthorization in [{}]. User with id = [{}] makes request = [{}]", thisClassName,
				userJVMDataStorage.getId(), requestName);

		// Direct implementation of the method

		ItemVariant entity = null;

		entity = model2entity(entityModel);

		service.saveItemVariant(entity);

		return new ResponseEntity<IdModel>(new IdModel(entity.getId()), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody ItemVariantModel entityModel,
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
		LOGGER.info("Finish UserAuthorization in [{}]. User with id = [{}] makes request = [{}]", thisClassName,
				userJVMDataStorage.getId(), requestName);

		// Direct implementation of the method

		ItemVariant entity = null;

		entity = service.getItemVariant(entityIdParam);

		entity.setItemId(entityModel.getItemId());
		entity.setVariantPrice(entityModel.getVariantPrice());

		service.saveItemVariant(entity);

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
		LOGGER.info("Finish UserAuthorization in [{}]. User with id = [{}] makes request = [{}]", thisClassName,
				userJVMDataStorage.getId(), requestName);

		// Direct implementation of the method

		service.deleteItemVariant(entityIdParam);

		return new ResponseEntity<IdModel>(HttpStatus.OK);

	}

	@RequestMapping(value = "/attributes", method = RequestMethod.GET)
	public ResponseEntity<?> getAllAttributes(@RequestParam(required = false) String name) {

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
		LOGGER.info("Finish UserAuthorization in {}. User with id = {} makes request = {}", thisClassName,
				userJVMDataStorage.getId(), requestName);

		// Direct implementation of the method

		List<CharacterTypeInItemVariant> allItems;

		allItems = service.getAllAttributes();

		List<CharacterTypeInItemVariantModel> convertedItems = new ArrayList<>();
		for (CharacterTypeInItemVariant item : allItems) {
			convertedItems.add(attribute2model(item));
		}

		return new ResponseEntity<List<CharacterTypeInItemVariantModel>>(convertedItems, HttpStatus.OK);
	}

	@RequestMapping(value = "/attributes/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getAttributeById(@PathVariable(value = "id") Integer entityIdParam) {

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
		LOGGER.info("Finish UserAuthorization in [{}]. User with id = [{}] makes request = [{}]", thisClassName,
				userJVMDataStorage.getId(), requestName);

		// Direct implementation of the method

		CharacterTypeInItemVariant entity = null;
		try {
			entity = service.getAttribute(entityIdParam);
		} catch (Exception e) {
			String msg = e.getMessage();
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}
		CharacterTypeInItemVariantModel entityModel = attribute2model(entity);

		return new ResponseEntity<CharacterTypeInItemVariantModel>(entityModel, HttpStatus.OK);
	}

	@RequestMapping(value = "/attributes", method = RequestMethod.POST)
	public ResponseEntity<?> createAttribute(@RequestBody CharacterTypeInItemVariantModel entityModel) {

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
		LOGGER.info("Finish UserAuthorization in [{}]. User with id = [{}] makes request = [{}]", thisClassName,
				userJVMDataStorage.getId(), requestName);

		// Direct implementation of the method

		CharacterTypeInItemVariant entity = null;

		entity = model2attribute(entityModel);
		
		
		try {
			service.saveAttribute(entity);
		} catch (Exception e) {
			String msg = String.format(
					"Character type with name [%s] is not allowed for insertion. Please use one of: [%s].",
					entityModel.getAttribute(), EnumArrayToMessageConvertor.itemsAttributeArrayToMessage());
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<IdModel>(new IdModel(entity.getId()), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/attributes/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateAttribute(@RequestBody CharacterTypeInItemVariantModel entityModel,
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
		LOGGER.info("Finish UserAuthorization in [{}]. User with id = [{}] makes request = [{}]", thisClassName,
				userJVMDataStorage.getId(), requestName);

		// Direct implementation of the method

		CharacterTypeInItemVariant entity = null;

		entity = service.getAttribute(entityIdParam);

		entity.setItemVariantId(entityModel.getItemVariantId());

		try {
			entity.setAttribute(Attribute.valueOf(entityModel.getAttribute().toUpperCase()));
		} catch (Exception e) {
			String msg = String.format(
					"Character type with name [%s] is not allowed for insertion. Please use one of: [%s].",
					entityModel.getAttribute(), EnumArrayToMessageConvertor.itemsAttributeArrayToMessage());
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);

		}

		entity.setValue(entityModel.getValue());
		entity.setCharacterTypeId(entityModel.getCharacterTypeId());

		service.saveAttribute(entity);

		return new ResponseEntity<IdModel>(HttpStatus.OK);
	}

	@RequestMapping(value = "/attributes/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteAttribute(@PathVariable(value = "id") Integer entityIdParam) {

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
		LOGGER.info("Finish UserAuthorization in [{}]. User with id = [{}] makes request = [{}]", thisClassName,
				userJVMDataStorage.getId(), requestName);

		// Direct implementation of the method

		service.deleteAttribute(entityIdParam);

		return new ResponseEntity<IdModel>(HttpStatus.OK);

	}

	private ItemVariantModel entity2model(ItemVariant itemVariant) {

		ItemVariantModel itemVariantModel = new ItemVariantModel();

		itemVariantModel.setId(itemVariant.getId());
		itemVariantModel.setItemId(itemVariant.getItemId());
		itemVariantModel.setVariantPrice(itemVariant.getVariantPrice());

		return itemVariantModel;
	}

	private ItemVariant model2entity(ItemVariantModel itemVariantModel) {

		ItemVariant itemVariant = new ItemVariant();

		itemVariant.setItemId(itemVariantModel.getItemId());
		itemVariant.setVariantPrice(itemVariantModel.getVariantPrice());

		return itemVariant;
	}

	private CharacterTypeInItemVariantModel attribute2model(CharacterTypeInItemVariant item) {

		CharacterTypeInItemVariantModel model = new CharacterTypeInItemVariantModel();

		model.setId(item.getId());
		model.setItemVariantId(item.getItemVariantId());
		model.setAttribute(item.getAttribute().name());
		model.setValue(item.getValue());
		model.setCharacterTypeId(item.getCharacterTypeId());

		return model;
	}

	private CharacterTypeInItemVariant model2attribute(CharacterTypeInItemVariantModel model) {

		CharacterTypeInItemVariant item = new CharacterTypeInItemVariant();

		item.setItemVariantId(model.getItemVariantId());
		item.setAttribute(Attribute.valueOf(model.getAttribute().toUpperCase()));
		item.setValue(model.getValue());
		item.setCharacterTypeId(model.getCharacterTypeId());

		return item;
	}

}

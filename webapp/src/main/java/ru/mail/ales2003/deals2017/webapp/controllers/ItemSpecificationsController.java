package ru.mail.ales2003.deals2017.webapp.controllers;

import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.mail.ales2003.deals2017.dao.api.customentities.ItemVariantCommonInfo;
import ru.mail.ales2003.deals2017.dao.api.customentities.ItemVariantDetail;
import ru.mail.ales2003.deals2017.dao.api.customentities.ItemVariantSpecification;
import ru.mail.ales2003.deals2017.dao.api.filters.IItemVariantFilter;
import ru.mail.ales2003.deals2017.dao.api.filters.ItemColumnNamesForSortingParams;
import ru.mail.ales2003.deals2017.dao.api.filters.OrderDirectionForSortingParams;
import ru.mail.ales2003.deals2017.dao.api.filters.PaginationParams;
import ru.mail.ales2003.deals2017.dao.api.filters.SortingParams;
import ru.mail.ales2003.deals2017.datamodel.ItemVariant;
import ru.mail.ales2003.deals2017.datamodel.Role;
import ru.mail.ales2003.deals2017.services.IItemVariantSpecificationService;
import ru.mail.ales2003.deals2017.services.IUserAuthService;
import ru.mail.ales2003.deals2017.services.impl.UserAuthStorage;
import ru.mail.ales2003.deals2017.webapp.models.ItemVariantCommonInfoModel;
import ru.mail.ales2003.deals2017.webapp.models.ItemVariantDetailModel;
import ru.mail.ales2003.deals2017.webapp.models.ItemVariantSpecificationModel;
import ru.mail.ales2003.deals2017.webapp.translate.StaticTranslator;
import ru.mail.ales2003.deals2017.webapp.util.EnumArrayToMessageConvertor;

/**
 * @author Aliaksandr Vishneuski ales_2003@mail.ru
 *
 */
@RestController
@RequestMapping("/itemspecifications")
public class ItemSpecificationsController {

	@Inject
	private ApplicationContext context;

	@Inject
	private StaticTranslator translator;

	@Inject
	private IUserAuthService authService;

	@Inject
	private IItemVariantFilter filter;

	private String thisClassName = ItemSpecificationsController.class.getSimpleName();

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemSpecificationsController.class);

	private String className = ItemVariant.class.getSimpleName();

	// this variable need to get his value instead hard but dynamically - from a
	// request header.
	private Locale locale = new Locale("ru_RU");

	private Locale localeFromRequest;
	// private PropertyResourceBundle pr = null;

	@Inject
	private IItemVariantSpecificationService service;

	@RequestMapping(value = "/commoninfos", method = RequestMethod.GET)
	public ResponseEntity<?> getCommonInfoFiltered(@RequestParam(required = false) Locale loc, String name,
			String description, BigDecimal minprice, BigDecimal maxprice, String column, String direction,
			Integer limit, Integer offset) {
		LOGGER.info("Locale localeFromRequest : {}.", loc);

		if (loc != null) {
			localeFromRequest = loc;
		}

		// Start ControllerAuthorization

		Set<Role> validUserRoles = new HashSet<>();
		{
			validUserRoles.add(Role.ADMIN);
			validUserRoles.add(Role.REVENUE_MANAGER);
			validUserRoles.add(Role.ITEM_MANAGER);
			validUserRoles.add(Role.SALES_MANAGER);
			validUserRoles.add(Role.CUSTOMER);
			validUserRoles.add(Role.GUEST);

		}

		String requestName = "getCommonInfoFiltered";

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

		List<ItemVariantCommonInfo> commonInfos;

		// IItemVariantFilter filter = new ItemVariantCommonInfoFilter();

		PaginationParams pParams = new PaginationParams();

		SortingParams sParams = new SortingParams();

		filter.setItemVariantName(name);

		filter.setItemVariantDescription(description);

		if (maxprice == null) {
			LOGGER.info("Because maxprice in customerquery ==null, maxprice=1000000");
			maxprice = new BigDecimal("1000000.00");
		}

		if (minprice == null) {
			LOGGER.info("Because minprice in customerquery ==null, minprice=0");
			minprice = new BigDecimal("0.00");
		}

		if ((maxprice.subtract(minprice)).intValue() < 0) {

			String msg = String.format("Incorrect arguments: the MIN price should be LOWER than the MAX price");
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}

		filter.setItemVariantPriceMIN(minprice);

		filter.setItemVariantPriceMAX(maxprice);

		// default 10 rows

		if (limit == null) {
			pParams.setLimit(10);
		} else {
			pParams.setLimit(limit);
		}

		pParams.setOffset(offset);
		filter.setPaginationParams(pParams);

		if (column != null) {
			try {
				sParams.setSortColumn(ItemColumnNamesForSortingParams.valueOf(column));
			} catch (IllegalArgumentException | NullPointerException e) {
				String msg = String.format("Column [%s] is not supported. Please use one of: %s", column,
						EnumArrayToMessageConvertor.itemColumnNameEnumArrayToMessage());
				return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
			}
		}
		if (direction != null) {
			try {
				sParams.setSortOrder(OrderDirectionForSortingParams.valueOf(direction));
			} catch (IllegalArgumentException | NullPointerException e) {
				String msg = String.format("Order direction [%s] is not supported. Please use one of: %s", direction,
						EnumArrayToMessageConvertor.orderDirectionEnumArrayToMessage());
				return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
			}
		}
		filter.setSortingParams(sParams);
		commonInfos = service.getCommonInfoFiltered(filter);

		if (commonInfos == null) {
			String msg = String.format("Information about the requested products is missing");
			LOGGER.error("Error: {}s storage is empty. Message was sent to the user: {}", className, msg);
			return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		}

		List<ItemVariantCommonInfoModel> convertedCommonInfos = new ArrayList<>();
		for (ItemVariantCommonInfo info : commonInfos) {
			convertedCommonInfos.add(info2model(info));
		}

		return new ResponseEntity<List<ItemVariantCommonInfoModel>>(convertedCommonInfos, HttpStatus.OK);
	}

	@RequestMapping(value = "/commoninfos/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getCommonInfoById(@PathVariable(value = "id") Integer entityIdParam,
			@RequestParam(required = false) Locale loc) {

		if (loc != null) {
			localeFromRequest = loc;
		}
		// Start ControllerAuthorization

		Set<Role> validUserRoles = new HashSet<>();
		{
			validUserRoles.add(Role.ADMIN);
			validUserRoles.add(Role.REVENUE_MANAGER);
			validUserRoles.add(Role.ITEM_MANAGER);
			validUserRoles.add(Role.SALES_MANAGER);
			validUserRoles.add(Role.CUSTOMER);
			validUserRoles.add(Role.GUEST);

		}

		String requestName = "getCommonInfoById";

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

		ItemVariantCommonInfo entity = null;
		try {
			entity = service.getCommonInfo(entityIdParam);
		} catch (Exception e) {
			String msg = e.getMessage();
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}

		if (entity == null) {
			String msg = String.format("Information about the requested products is missing");
			LOGGER.error("Error: {}s storage is empty. Message was sent to the user: {}", className, msg);
			return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		}

		ItemVariantCommonInfoModel model = info2model(entity);

		return new ResponseEntity<ItemVariantCommonInfoModel>(model, HttpStatus.OK);
	}

	@RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getDetailsById(@PathVariable(value = "id") Integer entityIdParam,
			@RequestParam(required = false) Locale loc) {

		if (loc != null) {
			localeFromRequest = loc;
		}

		// Start ControllerAuthorization

		Set<Role> validUserRoles = new HashSet<>();
		{
			validUserRoles.add(Role.ADMIN);
			validUserRoles.add(Role.REVENUE_MANAGER);
			validUserRoles.add(Role.ITEM_MANAGER);
			validUserRoles.add(Role.SALES_MANAGER);
			validUserRoles.add(Role.CUSTOMER);
			validUserRoles.add(Role.GUEST);

		}

		String requestName = "getDetailsById";

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

		List<ItemVariantDetail> details = null;
		try {
			details = service.getDetails(entityIdParam);
		} catch (Exception e) {
			String msg = e.getMessage();
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}

		if (details == null) {
			String msg = String.format("Information about the requested products is missing");
			LOGGER.error("Error: {}s storage is empty. Message was sent to the user: {}", className, msg);
			return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		}

		List<ItemVariantDetailModel> detailsModel = new ArrayList<>();
		for (ItemVariantDetail detail : details) {
			detailsModel.add(detail2detailModel(detail));
		}

		return new ResponseEntity<List<ItemVariantDetailModel>>(detailsModel, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getItemSpecificationById(@PathVariable(value = "id") Integer entityIdParam,
			@RequestParam(required = false) Locale loc) {

		if (loc != null) {
			localeFromRequest = loc;
		}

		// Start ControllerAuthorization

		Set<Role> validUserRoles = new HashSet<>();
		{
			validUserRoles.add(Role.ADMIN);
			validUserRoles.add(Role.REVENUE_MANAGER);
			validUserRoles.add(Role.ITEM_MANAGER);
			validUserRoles.add(Role.SALES_MANAGER);
			validUserRoles.add(Role.CUSTOMER);
			validUserRoles.add(Role.GUEST);

		}

		String requestName = "getCommonInfoById";

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

		ItemVariantSpecification entity = null;
		try {
			entity = service.getSpecification(entityIdParam);
		} catch (Exception e) {
			String msg = e.getMessage();
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}

		if (entity == null) {
			String msg = String.format("Information about the requested products is missing");
			LOGGER.error("Error: {}s storage is empty. Message was sent to the user: {}", className, msg);
			return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		}

		ItemVariantSpecificationModel model = specification2model(entity);

		return new ResponseEntity<ItemVariantSpecificationModel>(model, HttpStatus.OK);
	}

	private ItemVariantDetailModel detail2detailModel(ItemVariantDetail detail) {
		ItemVariantDetailModel detailModel = new ItemVariantDetailModel();

		Locale givenLocale;
		if (localeFromRequest != null) {
			givenLocale = localeFromRequest;
		} else {
			givenLocale = locale;
		}
		String translatedAttribute = StaticTranslator.translate(detail.getAttributeName().name(), givenLocale);

		detailModel.setAttributeName(translatedAttribute);
		detailModel.setAttributeValue(detail.getAttributeValue());

		String translatedMeasureUnit = StaticTranslator.translate(detail.getAttributeMeasure().name(), givenLocale);

		detailModel.setAttributeMeasure(translatedMeasureUnit);
		return detailModel;
	}

	private ItemVariantCommonInfoModel info2model(ItemVariantCommonInfo commonInfo) {
		ItemVariantCommonInfoModel model = new ItemVariantCommonInfoModel();

		model.setItemVariantId(commonInfo.getItemVariantId());

		LOGGER.info("Getting locale from request in Controller= {}", localeFromRequest);

		Locale givenLocale;
		if (localeFromRequest != null) {
			givenLocale = localeFromRequest;
		} else {
			givenLocale = locale;
		}
		String translatedItemName = StaticTranslator.translate(commonInfo.getItemName(), givenLocale);

		model.setItemName(translatedItemName);
		model.setItemDescription(commonInfo.getItemDescription());
		model.setItemVariantPrice(commonInfo.getItemVariantPrice());
		return model;
	}

	private ItemVariantSpecificationModel specification2model(ItemVariantSpecification specification) {

		ItemVariantSpecificationModel model = new ItemVariantSpecificationModel();

		ItemVariantCommonInfo commonInfo = specification.getInfo();
		ItemVariantCommonInfoModel commonInfoModel = info2model(commonInfo);

		List<ItemVariantDetailModel> detailModel = new ArrayList<ItemVariantDetailModel>();
		List<ItemVariantDetail> details = specification.getDetails();
		for (ItemVariantDetail detail : details) {
			detailModel.add(detail2detailModel(detail));
		}

		model.setItemVariantId(specification.getId());
		model.setCommonInfoModel(commonInfoModel);
		model.setDetailModel(detailModel);

		return model;
	}

	/*
	 * private String translate(String name, Locale locale) { pr =
	 * (PropertyResourceBundle) PropertyResourceBundle .getBundle(
	 * "ru.mail.ales2003.deals2017.webapp.controllers.i18n.entitieNames",
	 * locale); String key = pr.getString(name); return key; }
	 */

}

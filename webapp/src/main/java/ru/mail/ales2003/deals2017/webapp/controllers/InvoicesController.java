package ru.mail.ales2003.deals2017.webapp.controllers;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import ru.mail.ales2003.deals2017.dao.api.customentities.ContractCommonInfo;
import ru.mail.ales2003.deals2017.dao.api.customentities.ContractDetail;
import ru.mail.ales2003.deals2017.dao.api.customentities.Invoice;
import ru.mail.ales2003.deals2017.dao.api.filters.ContractColumnNamesForSortingParams;
import ru.mail.ales2003.deals2017.dao.api.filters.IContractFilter;
import ru.mail.ales2003.deals2017.dao.api.filters.OrderDirectionForSortingParams;
import ru.mail.ales2003.deals2017.dao.api.filters.PaginationParams;
import ru.mail.ales2003.deals2017.dao.api.filters.SortingParams;
import ru.mail.ales2003.deals2017.datamodel.Contract;
import ru.mail.ales2003.deals2017.datamodel.ContractStatus;
import ru.mail.ales2003.deals2017.datamodel.CustomerType;
import ru.mail.ales2003.deals2017.datamodel.PayForm;
import ru.mail.ales2003.deals2017.datamodel.PayStatus;
import ru.mail.ales2003.deals2017.datamodel.Role;
import ru.mail.ales2003.deals2017.services.IInvoiceService;
import ru.mail.ales2003.deals2017.services.IUserAuthService;
import ru.mail.ales2003.deals2017.services.impl.UserAuthStorage;
import ru.mail.ales2003.deals2017.webapp.models.ContractCommonInfoModel;
import ru.mail.ales2003.deals2017.webapp.models.ContractDetailModel;
import ru.mail.ales2003.deals2017.webapp.models.InvoiceModel;
import ru.mail.ales2003.deals2017.webapp.translate.StaticTranslator;
import ru.mail.ales2003.deals2017.webapp.util.EnumArrayToMessageConvertor;

/**
 * @author Aliaksandr Vishneuski ales_2003@mail.ru
 *
 */
@RestController
@RequestMapping("/invoices")
public class InvoicesController {

	@Inject
	private ApplicationContext context;

	@Inject
	private StaticTranslator translator;

	@Inject
	private IUserAuthService authService;

	@Inject
	IContractFilter filter;

	private String thisClassName = InvoicesController.class.getSimpleName();

	private static final Logger LOGGER = LoggerFactory.getLogger(InvoicesController.class);

	private String className = Contract.class.getSimpleName();

	// this variable need to get his value instead hard but dynamically - from a
	// request header.
	private Locale locale = new Locale("ru_RU");

	// private PropertyResourceBundle pr = null;

	@Inject
	private IInvoiceService service;

	@RequestMapping(value = "/commoninfos", method = RequestMethod.GET)
	public ResponseEntity<?> getCommonInfoFiltered(@RequestParam(required = false) Integer customer_id,
			String customer_last_name, String customer_company, String customer_type, Integer manager_id,
			String manager_last_name, String created_from, String created_to, BigDecimal amount_min,
			BigDecimal amount_max, String contract_status, String pay_form, String pay_status, String column,
			String direction, Integer limit, Integer offset) {

		// Start ControllerAuthorization

		Set<Role> validUserRoles = new HashSet<>();
		{
			validUserRoles.add(Role.ADMIN);
			validUserRoles.add(Role.REVENUE_MANAGER);
			validUserRoles.add(Role.SALES_MANAGER);
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

		List<ContractCommonInfo> commonInfos;

		// ContractCommonInfoFilter filter = new ContractCommonInfoFilter();

		PaginationParams pParams = new PaginationParams();

		SortingParams sParams = new SortingParams();

		filter.setCustomerId(customer_id);
		filter.setCustomerLastName(manager_last_name);
		filter.setCustomerCompany(customer_company);

		if (customer_type != null) {
			try {
				filter.setCustomerType(CustomerType.valueOf(customer_type));
			} catch (IllegalArgumentException | NullPointerException e) {
				String msg = String.format("CustomerType [%s] is not supported. Please use one of: %s", customer_type,
						EnumArrayToMessageConvertor.customerTypeArrayToMessage());
				return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
			}
		}

		if (contract_status != null) {
			try {
				filter.setContractStatus(ContractStatus.valueOf(contract_status));
			} catch (IllegalArgumentException | NullPointerException e) {
				String msg = String.format("ContractStatus [%s] is not supported. Please use one of: %s",
						contract_status, EnumArrayToMessageConvertor.contractStatusArrayToMessage());
				return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
			}
		}

		if (pay_form != null) {
			try {
				filter.setPayForm(PayForm.valueOf(pay_form));
			} catch (IllegalArgumentException | NullPointerException e) {
				String msg = String.format("ContractStatus [%s] is not supported. Please use one of: %s", pay_form,
						EnumArrayToMessageConvertor.payFormArrayToMessage());
				return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
			}
		}
		if (pay_status != null) {
			try {
				filter.setPayStatus(PayStatus.valueOf(pay_status));
			} catch (IllegalArgumentException | NullPointerException e) {
				String msg = String.format("setPayStatus [%s] is not supported. Please use one of: %s", pay_status,
						EnumArrayToMessageConvertor.payStatusArrayToMessage());
				return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
			}
		}

		filter.setManagerId(manager_id);
		filter.setManagerLastName(manager_last_name);

		// for compare from & to
		Long fromL = null;
		Long toL = null;

		if (created_from != null) {
			try {
				Timestamp from = fromStringToTimestamp(created_from);
				filter.setCreatedFROM(from);
				fromL = from.getTime();
			} catch (Exception e) {
				String msg = String.format("Format: [%s] is not supported format. Please use: %s", created_from,
						"yyyy-MM-dd");
				return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
			}
		}

		if (created_to != null) {
			try {
				Timestamp to = fromStringToTimestamp(created_to);
				filter.setCreatedTO(to);
				toL = to.getTime();
			} catch (Exception e) {
				String msg = String.format("Format: [%s] is not supported format. Please use: %s", created_to,
						"yyyy-MM-dd");
				return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
			}
		}

		if (fromL != null && toL != null) {
			if (toL - fromL < 0) {
				String msg = String.format(
						"Incorrect arguments: the DATE [created_from] should be EARLY than the DATE [created_to]. You requested: created_from = [%s] and created_to = [%s]",
						created_from, created_to);
				return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
			}
		}

		if (amount_max == null) {
			LOGGER.info("Because maxprice in customerquery ==null, maxprice=1000000");
			amount_max = new BigDecimal("1000000.00");
		}

		if (amount_min == null) {
			LOGGER.info("Because minprice in customerquery ==null, minprice=0");
			amount_min = new BigDecimal("0.00");
		}

		if ((amount_max.subtract(amount_min)).intValue() < 0) {

			String msg = String.format("Incorrect arguments: the MIN amount should be LOWER than the MAX amount");
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}

		filter.setAmountMIN(amount_min);
		filter.setAmountMAX(amount_max);

		// default 10 rows

		if (limit == null) {
			pParams.setLimit(5);
		} else {
			pParams.setLimit(limit);
		}

		pParams.setOffset(offset);
		filter.setPaginationParams(pParams);

		if (column != null) {
			try {
				sParams.setContractSortColumn(ContractColumnNamesForSortingParams.valueOf(column));
			} catch (IllegalArgumentException | NullPointerException e) {
				String msg = String.format("Column [%s] is not supported. Please use one of: %s", column,
						EnumArrayToMessageConvertor.contractColumnNameEnumArrayToMessage());
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
			String msg = String.format("Information about the requested invoice is missing");
			LOGGER.error("Error: {}s storage is empty. Message was sent to the user: {}", className, msg);
			return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		}

		List<ContractCommonInfoModel> convertedCommonInfos = new ArrayList<>();
		for (ContractCommonInfo info : commonInfos) {
			convertedCommonInfos.add(commonInfo2model(info));
		}

		return new ResponseEntity<List<ContractCommonInfoModel>>(convertedCommonInfos, HttpStatus.OK);
	}

	@RequestMapping(value = "/commoninfos/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getCommonInfoById(@PathVariable(value = "id") Integer entityIdParam) {

		// Start ControllerAuthorization

		Set<Role> validUserRoles = new HashSet<>();
		{
			validUserRoles.add(Role.ADMIN);
			validUserRoles.add(Role.REVENUE_MANAGER);
			validUserRoles.add(Role.SALES_MANAGER);
			validUserRoles.add(Role.CUSTOMER);

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

		ContractCommonInfo entity = null;
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

		ContractCommonInfoModel model = commonInfo2model(entity);

		return new ResponseEntity<ContractCommonInfoModel>(model, HttpStatus.OK);
	}

	@RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getDetailsById(@PathVariable(value = "id") Integer entityIdParam) {

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

		List<ContractDetail> details = null;
		try {
			details = service.getDetails(entityIdParam);
		} catch (Exception e) {
			String msg = e.getMessage();
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}

		if (details == null) {
			String msg = String.format("Information about the requested invoice is missing");
			LOGGER.error("Error: {}s storage is empty. Message was sent to the user: {}", className, msg);
			return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		}

		List<ContractDetailModel> detailsModel = new ArrayList<>();
		for (ContractDetail detail : details) {
			detailsModel.add(detail2detailModel(detail));
		}

		return new ResponseEntity<List<ContractDetailModel>>(detailsModel, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getInvoiceById(@PathVariable(value = "id") Integer entityIdParam) {

		// Start ControllerAuthorization

		Set<Role> validUserRoles = new HashSet<>();
		{
			validUserRoles.add(Role.ADMIN);
			validUserRoles.add(Role.REVENUE_MANAGER);
			validUserRoles.add(Role.SALES_MANAGER);
			validUserRoles.add(Role.CUSTOMER);
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

		Invoice entity = null;
		try {
			entity = service.getInvoice(entityIdParam);
		} catch (Exception e) {
			String msg = e.getMessage();
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}

		if (entity == null) {
			String msg = String.format("Information about the requested invoice is missing");
			LOGGER.error("Error: {}s storage is empty. Message was sent to the user: {}", className, msg);
			return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		}

		InvoiceModel model = invoice2model(entity);

		return new ResponseEntity<InvoiceModel>(model, HttpStatus.OK);
	}

	private InvoiceModel invoice2model(Invoice invoice) {

		InvoiceModel model = new InvoiceModel();

		ContractCommonInfo commonInfo = invoice.getCommonInfo();
		ContractCommonInfoModel commonInfoModel = commonInfo2model(commonInfo);

		List<ContractDetailModel> detailModels = new ArrayList<>();
		List<ContractDetail> details = invoice.getDetails();
		for (ContractDetail detail : details) {
			detailModels.add(detail2detailModel(detail));
		}

		model.setInvoiceNumber(invoice.getInvoiceNumber());
		model.setCommonInfo(commonInfoModel);
		model.setDetails(detailModels);

		return model;
	}

	private ContractCommonInfoModel commonInfo2model(ContractCommonInfo commonInfo) {

		ContractCommonInfoModel model = new ContractCommonInfoModel();

		model.setCustomerId(commonInfo.getCustomerId());
		model.setCustomerLastName(commonInfo.getCustomerLastName());
		model.setCustomerCompanyName(commonInfo.getCustomerLastName());
		model.setCustomerType(commonInfo.getCustomerType().name());

		model.setManagerId(commonInfo.getManagerId());
		model.setManagerLastName(commonInfo.getManagerLastName());

		model.setContractId(commonInfo.getContractId());
		model.setContractStatus(commonInfo.getContractStatus().name());
		model.setPayForm(commonInfo.getPayForm().name());
		model.setPayStatus(commonInfo.getPayStatus().name());
		model.setCreated(timestamp2String(commonInfo.getCreated()));
		model.setTotalAmount(commonInfo.getTotalAmount());
		return model;

		// String translatedItemName =
		// Translator.translate(commonInfo.getItemName(), locale);
	}

	private ContractDetailModel detail2detailModel(ContractDetail detail) {

		ContractDetailModel detailModel = new ContractDetailModel();
		detailModel.setContractId(detail.getContractId());
		detailModel.setItemVariantId(detail.getItemVariantId());
		detailModel.setItemName(detail.getItemName());
		detailModel.setItemVariantPrice(detail.getItemVariantPrice());
		detailModel.setItemVariantQuantity(detail.getItemVariantQuantity());
		detailModel.setItemVariantTotalPrice(detail.getItemVariantTotalPrice());

		return detailModel;
	}

	private String timestamp2String(Timestamp tstmp) {
		Date date = new Date(tstmp.getTime());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String string = dateFormat.format(date);
		return string;

	}

	private Timestamp fromStringToTimestamp(String date) throws Exception {
		Timestamp timestamp = null;

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date parsedDate = dateFormat.parse(date);
		timestamp = new java.sql.Timestamp(parsedDate.getTime());

		return timestamp;

	}

}

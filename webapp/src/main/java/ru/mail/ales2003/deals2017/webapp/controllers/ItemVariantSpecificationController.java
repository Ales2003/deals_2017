package ru.mail.ales2003.deals2017.webapp.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.mail.ales2003.deals2017.dao.api.customentities.ItemVariantCommonInfo;
import ru.mail.ales2003.deals2017.dao.api.customentities.ItemVariantDetail;
import ru.mail.ales2003.deals2017.dao.api.filters.ColumnNamesForSortingParams;
import ru.mail.ales2003.deals2017.dao.api.filters.IItemVariantFilter;
import ru.mail.ales2003.deals2017.dao.api.filters.OrderDirectionForSortingParams;
import ru.mail.ales2003.deals2017.dao.api.filters.PaginationParams;
import ru.mail.ales2003.deals2017.dao.api.filters.SortingParams;
import ru.mail.ales2003.deals2017.dao.db.filters.impl.ItemVariantCommonInfoFilter;
import ru.mail.ales2003.deals2017.datamodel.ItemVariant;
import ru.mail.ales2003.deals2017.services.IItemVariantSpecificationService;
import ru.mail.ales2003.deals2017.webapp.models.ItemVariantCommonInfoModel;
import ru.mail.ales2003.deals2017.webapp.models.ItemVariantDetailModel;
import ru.mail.ales2003.deals2017.webapp.translate.Translator;
import ru.mail.ales2003.deals2017.webapp.util.EnumArrayToMessageConvertor;

/**
 * @author Aliaksandr Vishneuski ales_2003@mail.ru
 *
 */
@RestController
@RequestMapping("/itemvariants")
public class ItemVariantSpecificationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemVariantSpecificationController.class);

	private String className = ItemVariant.class.getSimpleName();

	// this variable need to get his value instead hard but dynamically - from a
	// request header.
	private Locale locale = new Locale("ru_RU");

	// private PropertyResourceBundle pr = null;

	@Inject
	private IItemVariantSpecificationService service;

	@RequestMapping(value = "/commoninfo", method = RequestMethod.GET)
	public ResponseEntity<?> getCommonInfoForAll(@RequestParam(required = false) String name, String description,
			BigDecimal minprice, BigDecimal maxprice, String column, String order, Integer limit, Integer offset) {

		List<ItemVariantCommonInfo> commonInfos;

		IItemVariantFilter filter = new ItemVariantCommonInfoFilter();

		PaginationParams pParams = new PaginationParams();

		SortingParams sParams = new SortingParams();

		filter.setItemVariantName(name);

		filter.setItemVariantDescription(description);

		if ((maxprice.subtract(minprice)).intValue() < 0) {

			String msg = String.format("Incorrect arguments: the MIN price should be LOWER than the MAX price");
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}

		filter.setItemVariantPriceMIN(minprice);

		filter.setItemVariantPriceMAX(maxprice);

		if (limit == null) {
			pParams.setLimit(5);
		} else {
			pParams.setLimit(limit);
		}

		pParams.setOffset(offset);
		filter.setPaginationParams(pParams);

		if (column != null) {
			try {
				sParams.setSortColumn(ColumnNamesForSortingParams.valueOf(column));
			} catch (IllegalArgumentException | NullPointerException e) {
				String msg = String.format("Column [%s] is not supported. Please use one of: %s", column,
						EnumArrayToMessageConvertor.itemColumnNameEnumArrayToMessage());
				return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
			}
		}
		if (order != null) {
			try {
				sParams.setSortOrder(OrderDirectionForSortingParams.valueOf(column));
			} catch (IllegalArgumentException | NullPointerException e) {
				String msg = String.format("Order direction [%s] is not supported. Please use one of: %s", order,
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
			convertedCommonInfos.add(commonInfoEntity2basicInfoModel(info));
		}

		return new ResponseEntity<List<ItemVariantCommonInfoModel>>(convertedCommonInfos, HttpStatus.OK);
	}

	/*
	 * @RequestMapping(value = "/details", method = RequestMethod.GET) public
	 * ResponseEntity<?> getAllInDetails(@RequestParam(required = false) String
	 * name, BigDecimal price) { List<ItemVariantSpecification> specifications =
	 * service.getSpecifications(); if (specifications == null) { String msg =
	 * String.format("All item variants don't exist in store"); return new
	 * ResponseEntity<String>(msg, HttpStatus.NOT_FOUND); } if (name != null ||
	 * price != null) { IItemVariantFilter filter = new
	 * ItemVariantCommonInfoFilter(); filter.setItemVariantName(name);
	 * filter.setItemVariantPrice(price);
	 * 
	 * specifications = service.getSpecificationsFiltered(filter); if
	 * (specifications == null) { // maybe to offer all available combinations
	 * String msg = String.format(
	 * "Item variants with name [%s] and price  [%s] don't exist in store. Please use another combination."
	 * , name, price); return new ResponseEntity<String>(msg,
	 * HttpStatus.NOT_FOUND); } }
	 * 
	 * return new ResponseEntity<List<ItemVariantSpecification>>(specifications,
	 * HttpStatus.OK);
	 * 
	 */

	// commited for finding out the cause for failure
	/*
	 * List<ItemVariantSpecificationModel> convertedSpecifications = new
	 * ArrayList<>(); for (ItemVariantSpecification specification :
	 * specifications) {
	 * 
	 * ItemVariantSpecificationModel specificationModel = new
	 * ItemVariantSpecificationModel();
	 * 
	 * specificationModel.setBasicInfoModel(basicInfoEntity2basicInfoModel(
	 * specification.getInfo()));
	 * 
	 * List<ItemVariantDetailModel> detailModel = new ArrayList<>(); for
	 * (ItemVariantDetail detail : specification.getDetails()) {
	 * detailModel.add(detailEntity2detailModel(detail)); }
	 * 
	 * //ItemVariantDetailModel[] detailArray = detailModel.toArray(new
	 * ItemVariantDetailModel[detailModel.size()]);
	 * //specificationModel.setDetailModel(detailArray);
	 * specificationModel.setDetailModel(detailModel);
	 * 
	 * convertedSpecifications.add(specificationModel); } return new
	 * ResponseEntity<List<ItemVariantSpecificationModel>>(
	 * convertedSpecifications, HttpStatus.OK);
	 */

	// Successful test
	/*
	 * ItemVariantBasicInfoModel b = new ItemVariantBasicInfoModel();
	 * b.setItemName("ItemName"); b.setItemDescription("itemDescription");
	 * b.setItemVariantPrice(new BigDecimal("200.00"));
	 * 
	 * ItemVariantDetailModel d_1 =new ItemVariantDetailModel();
	 * d_1.setAttributeName("attributeName_1");
	 * d_1.setAttributeValue("attributeValue_1");
	 * d_1.setAttributeMeasure("attributeMeasure_1"); ItemVariantDetailModel d_2
	 * =new ItemVariantDetailModel(); d_2.setAttributeName("attributeName_2");
	 * d_2.setAttributeValue("attributeValue_2");
	 * d_2.setAttributeMeasure("attributeMeasure_2");
	 * List<ItemVariantDetailModel> d = new ArrayList<>(); d.add(d_1);
	 * d.add(d_2);
	 * 
	 * ItemVariantSpecificationModel m_1= new ItemVariantSpecificationModel();
	 * m_1.setBasicInfoModel(b); m_1.setDetailModel(d);
	 * ItemVariantSpecificationModel m_2= new ItemVariantSpecificationModel();
	 * m_2.setBasicInfoModel(b); m_2.setDetailModel(d);
	 * 
	 * List<ItemVariantSpecificationModel> ss = new ArrayList<>(); ss.add(m_1);
	 * ss.add(m_2);
	 * 
	 * return new ResponseEntity<List<ItemVariantSpecificationModel>>(ss,
	 * HttpStatus.OK);
	 */

	private ItemVariantDetailModel detailEntity2detailModel(ItemVariantDetail detail) {
		ItemVariantDetailModel detailModel = new ItemVariantDetailModel();
		detailModel.setAttributeName(detail.getAttributeName().name());
		detailModel.setAttributeValue(detail.getAttributeValue());
		detailModel.setAttributeMeasure(detail.getAttributeMeasure().name());
		return detailModel;
	}

	private ItemVariantCommonInfoModel commonInfoEntity2basicInfoModel(ItemVariantCommonInfo commonInfo) {
		ItemVariantCommonInfoModel basicInfokModel = new ItemVariantCommonInfoModel();

		String translatedItemName = Translator.translate(commonInfo.getItemName(), locale);

		basicInfokModel.setItemName(translatedItemName);
		basicInfokModel.setItemDescription(commonInfo.getItemDescription());
		basicInfokModel.setItemVariantPrice(commonInfo.getItemVariantPrice());
		return basicInfokModel;
	}
	/*
	 * private String translate(String name, Locale locale) { pr =
	 * (PropertyResourceBundle) PropertyResourceBundle .getBundle(
	 * "ru.mail.ales2003.deals2017.webapp.controllers.i18n.entitieNames",
	 * locale); String key = pr.getString(name); return key; }
	 */

}

package ru.mail.ales2003.deals2017.webapp.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.PropertyResourceBundle;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.mail.ales2003.deals2017.dao.api.customentities.ItemVariantCommonInfo;
import ru.mail.ales2003.deals2017.dao.api.customentities.ItemVariantDetail;
import ru.mail.ales2003.deals2017.dao.api.customentities.ItemVariantSpecification;
import ru.mail.ales2003.deals2017.dao.api.filters.IItemVariantFilter;
import ru.mail.ales2003.deals2017.dao.db.filters.impl.ItemVariantCommonInfoFilter;
import ru.mail.ales2003.deals2017.services.IItemVariantService;
import ru.mail.ales2003.deals2017.webapp.models.ItemVariantBasicInfoModel;
import ru.mail.ales2003.deals2017.webapp.models.ItemVariantDetailModel;

@RestController
@RequestMapping("/itemvariants")
public class ItemVariantBasicInfosController {

	// this variable need to get his value instead hard but dynamically - from a request header.
	Locale locale = new Locale("ru_RU");

	PropertyResourceBundle pr = null;

	@Inject
	private IItemVariantService itemVariantService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllInShortFormat(@RequestParam(required = false) String name, BigDecimal price) {
		List<ItemVariantCommonInfo> allBasicInfos = itemVariantService.getCommonInfoForAll();
		if (allBasicInfos == null) {
			String msg = String.format("All item variants don't exist in store");
			return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		}

		// allBasicInfos = itemVariantService.getBasicInfoForEach();
		if (name != null || price != null) {
			IItemVariantFilter filter = new ItemVariantCommonInfoFilter();
			filter.setItemVariantName(name);
			filter.setItemVariantPrice(price);
			// basicInfoFilter.filterInitialize();
			allBasicInfos = itemVariantService.getCommonInfoFiltered(filter);
			if (allBasicInfos == null) {
				// maybe to offer all available combinations
				String msg = String.format(
						"Item variant with name [%s] and price  [%s] doesn't exist in storage. Please use another combination.",
						name, price);
				return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
			}

			// allBasicInfos =
			// itemVariantService.getFilteredBasicInfo(basicInfoFilter);
		}

		// return new ResponseEntity<List<ItemVariantBasicInfo>>(allBasicInfos,
		// HttpStatus.OK);

		List<ItemVariantBasicInfoModel> convertedBasicInfos = new ArrayList<>();
		for (ItemVariantCommonInfo info : allBasicInfos) {
			convertedBasicInfos.add(basicInfoEntity2basicInfoModel(info));
		}

		return new ResponseEntity<List<ItemVariantBasicInfoModel>>(convertedBasicInfos, HttpStatus.OK);
	}

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ResponseEntity<?> getAllInDetails(@RequestParam(required = false) String name, BigDecimal price) {
		List<ItemVariantSpecification> specifications = itemVariantService.getSpecifications();
		if (specifications == null) {
			String msg = String.format("All item variants don't exist in store");
			return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		}
		if (name != null || price != null) {
			IItemVariantFilter filter = new ItemVariantCommonInfoFilter();
			filter.setItemVariantName(name);
			filter.setItemVariantPrice(price);

			specifications = itemVariantService.getSpecificationsFiltered(filter);
			if (specifications == null) {
				// maybe to offer all available combinations
				String msg = String.format(
						"Item variants with name [%s] and price  [%s] don't exist in store. Please use another combination.",
						name, price);
				return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
			}
		}

		return new ResponseEntity<List<ItemVariantSpecification>>(specifications, HttpStatus.OK);
		
		//commited for finding out the cause for failure
/*		List<ItemVariantSpecificationModel> convertedSpecifications = new ArrayList<>();
		for (ItemVariantSpecification specification : specifications) {

			ItemVariantSpecificationModel specificationModel = new ItemVariantSpecificationModel();

			specificationModel.setBasicInfoModel(basicInfoEntity2basicInfoModel(specification.getInfo()));

			List<ItemVariantDetailModel> detailModel = new ArrayList<>();
			for (ItemVariantDetail detail : specification.getDetails()) {
				detailModel.add(detailEntity2detailModel(detail));
			}

			//ItemVariantDetailModel[] detailArray = detailModel.toArray(new ItemVariantDetailModel[detailModel.size()]);
			//specificationModel.setDetailModel(detailArray);
			specificationModel.setDetailModel(detailModel);

			convertedSpecifications.add(specificationModel);
		}
		return new ResponseEntity<List<ItemVariantSpecificationModel>>(convertedSpecifications, HttpStatus.OK);
		*/
		
		
		
		
		//Successful test
/*		ItemVariantBasicInfoModel b = new ItemVariantBasicInfoModel();
		b.setItemName("ItemName");
		b.setItemDescription("itemDescription");
		b.setItemVariantPrice(new BigDecimal("200.00"));
		
		ItemVariantDetailModel d_1 =new ItemVariantDetailModel();
		d_1.setAttributeName("attributeName_1");
		d_1.setAttributeValue("attributeValue_1");
		d_1.setAttributeMeasure("attributeMeasure_1");
		ItemVariantDetailModel d_2 =new ItemVariantDetailModel();
		d_2.setAttributeName("attributeName_2");
		d_2.setAttributeValue("attributeValue_2");
		d_2.setAttributeMeasure("attributeMeasure_2");
		List<ItemVariantDetailModel> d = new ArrayList<>();
		d.add(d_1);
		d.add(d_2);
		
		ItemVariantSpecificationModel m_1= new ItemVariantSpecificationModel();
		m_1.setBasicInfoModel(b);
		m_1.setDetailModel(d);
		ItemVariantSpecificationModel m_2= new ItemVariantSpecificationModel();
		m_2.setBasicInfoModel(b);
		m_2.setDetailModel(d);
		
		List<ItemVariantSpecificationModel> ss = new ArrayList<>();
		ss.add(m_1);
		ss.add(m_2);
		
		return new ResponseEntity<List<ItemVariantSpecificationModel>>(ss, HttpStatus.OK);
*/
		
	}

	private ItemVariantDetailModel detailEntity2detailModel(ItemVariantDetail detail) {
		ItemVariantDetailModel detailModel = new ItemVariantDetailModel();
		detailModel.setAttributeName(detail.getAttributeName().name());
		detailModel.setAttributeValue(detail.getAttributeValue());
		detailModel.setAttributeMeasure(detail.getAttributeMeasure().name());
		return detailModel;
	}

	private ItemVariantBasicInfoModel basicInfoEntity2basicInfoModel(ItemVariantCommonInfo basicInfo) {
		ItemVariantBasicInfoModel basicInfokModel = new ItemVariantBasicInfoModel();

		String translatedItemName = translate(basicInfo.getItemName(), locale);

		basicInfokModel.setItemName(translatedItemName);
		basicInfokModel.setItemDescription(basicInfo.getItemDescription());
		basicInfokModel.setItemVariantPrice(basicInfo.getItemVariantPrice());
		return basicInfokModel;
	}

	private String translate(String name, Locale locale) {
		pr = (PropertyResourceBundle) PropertyResourceBundle
				.getBundle("ru.mail.ales2003.deals2017.webapp.controllers.i18n.entitieNames", locale);
		String key = pr.getString(name);
		return key;
	}

}

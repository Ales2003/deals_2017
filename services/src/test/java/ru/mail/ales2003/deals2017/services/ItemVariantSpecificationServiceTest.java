package ru.mail.ales2003.deals2017.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import ru.mail.ales2003.deals2017.dao.api.customentities.ItemVariantCommonInfo;
import ru.mail.ales2003.deals2017.dao.api.customentities.ItemVariantDetail;
import ru.mail.ales2003.deals2017.dao.api.customentities.ItemVariantSpecification;
import ru.mail.ales2003.deals2017.dao.api.filters.ItemColumnNamesForSortingParams;
import ru.mail.ales2003.deals2017.dao.api.filters.OrderDirectionForSortingParams;
import ru.mail.ales2003.deals2017.dao.api.filters.PaginationParams;
import ru.mail.ales2003.deals2017.dao.api.filters.SortingParams;
import ru.mail.ales2003.deals2017.dao.db.filters.impl.ItemVariantCommonInfoFilter;
import ru.mail.ales2003.deals2017.datamodel.Attribute;
import ru.mail.ales2003.deals2017.datamodel.CharacterType;
import ru.mail.ales2003.deals2017.datamodel.CharacterTypeInItemVariant;
import ru.mail.ales2003.deals2017.datamodel.Item;
import ru.mail.ales2003.deals2017.datamodel.ItemVariant;
import ru.mail.ales2003.deals2017.datamodel.Measure;

public class ItemVariantSpecificationServiceTest extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemVariantSpecificationServiceTest.class);

	@Inject
	private IItemVariantSpecificationService service;

	@Inject
	private IItemService itemService;

	@Inject
	private IItemVariantService itemVariantService;

	@Inject
	private ICharacterTypeService measureService;

	private ItemVariantCommonInfo commonInfo;
	private List<ItemVariantCommonInfo> commonInfos;

	private List<ItemVariantDetail> details;
	
	private ItemVariantSpecification specification;

	private Item item;
	private Item itemFromDb;

	private ItemVariant itemVariant_1;
	private ItemVariant itemVariant_2;
	private ItemVariant itemVariant_1FromDb;
	private ItemVariant itemVariant_2FromDb;

	private CharacterType measure;
	private CharacterType measureFromDb;

	private CharacterTypeInItemVariant attribute_1;
	private CharacterTypeInItemVariant attribute_2;
	private CharacterTypeInItemVariant attribute_3;
	private CharacterTypeInItemVariant attribute_4;

	@Before
	public void runBeforeTestMethod() {
		LOGGER.debug("Start preparation of the method");

		LOGGER.debug("Creating item in Db");
		item = new Item();
		item.setName("Heater1");
		item.setBasicPrice(new BigDecimal("250.00"));
		item.setDescription("Very good heater!!!");
		itemService.save(item);
		itemFromDb = itemService.get(item.getId());
		LOGGER.debug("Item was created with id={}", itemFromDb.getId());

		LOGGER.debug("Creating itemVariants in Db");
		itemVariant_1 = new ItemVariant();
		itemVariant_1.setItemId(itemFromDb.getId());
		itemVariant_1.setVariantPrice(new BigDecimal("300.00"));
		itemVariantService.saveItemVariant(itemVariant_1);
		itemVariant_1FromDb = itemVariantService.getItemVariant(itemVariant_1.getId());
		itemVariant_2 = new ItemVariant();
		itemVariant_2.setItemId(itemFromDb.getId());
		itemVariant_2.setVariantPrice(new BigDecimal("400.00"));
		itemVariantService.saveItemVariant(itemVariant_2);
		itemVariant_2FromDb = itemVariantService.getItemVariant(itemVariant_2.getId());
		LOGGER.debug("ItemVariants were created");

		LOGGER.debug("Creating characterType in Db");
		measure = new CharacterType();
		measure.setName(Measure.MM);
		measureService.save(measure);
		measureFromDb = measureService.get(measure.getId());
		LOGGER.debug("CharacterType was created with id={}", measureFromDb.getId());

		LOGGER.debug("Creating characterTypeInItemVariant in Db");
		attribute_1 = new CharacterTypeInItemVariant();
		attribute_1.setItemVariantId(itemVariant_1FromDb.getId());
		attribute_1.setAttribute(Attribute.LENGTH);
		attribute_1.setValue("2000");
		attribute_1.setCharacterTypeId(measureFromDb.getId());
		attribute_2 = new CharacterTypeInItemVariant();
		attribute_2.setItemVariantId(itemVariant_1FromDb.getId());
		attribute_2.setAttribute(Attribute.WIDTH);
		attribute_2.setValue("280");
		attribute_2.setCharacterTypeId(measureFromDb.getId());
		attribute_3 = new CharacterTypeInItemVariant();
		attribute_3.setItemVariantId(itemVariant_2FromDb.getId());
		attribute_3.setAttribute(Attribute.LENGTH);
		attribute_3.setValue("1500");
		attribute_3.setCharacterTypeId(measureFromDb.getId());
		attribute_4 = new CharacterTypeInItemVariant();
		attribute_4.setItemVariantId(itemVariant_2FromDb.getId());
		attribute_4.setAttribute(Attribute.WIDTH);
		attribute_4.setValue("220");
		attribute_4.setCharacterTypeId(measureFromDb.getId());

		itemVariantService.saveAttributeMultiple(attribute_1, attribute_2, attribute_3, attribute_4);

		for (CharacterTypeInItemVariant entity : itemVariantService.getAllAttributes()) {
			LOGGER.debug("CharacterTypeInItemVariant was created with id={}", entity.getId());
		}

		LOGGER.debug("Finish preparation of the method");
	}

	@After
	public void runAfterTestMethod() {
		LOGGER.debug("Start completion of the method");

		LOGGER.debug("Start deleting entities of CharacterTypeInItemVariant from Db");
		for (CharacterTypeInItemVariant cTIIV : itemVariantService.getAllAttributes()) {
			itemVariantService.deleteAttribute(cTIIV.getId());
		}
		LOGGER.debug("Entities of CharacterTypeInItemVariant were deleted from Db ");

		LOGGER.debug("Start deleting characterTypes from Db");
		for (CharacterType cT : measureService.getAll()) {
			measureService.delete(cT.getId());
		}
		LOGGER.debug("characterTypes were deleted from Db ");

		LOGGER.debug("Start deleting itemVariants from Db");
		for (ItemVariant iV : itemVariantService.getAllItemVariants()) {
			itemVariantService.deleteItemVariant(iV.getId());
		}
		LOGGER.debug("ItemVariants were deleted from Db ");

		LOGGER.debug("Start deleting items from Db");
		for (Item i : itemService.getAll()) {
			itemService.delete(i.getId());
		}
		LOGGER.debug("Items were deleted from Db ");

		LOGGER.debug("Finish completion of the method");
	}

	/*
	 * Test for the getting an object. Given object from Db is checked for the
	 * existence and filling of fields.
	 */

	@Test
	public void getCommonInfoTest() {
		LOGGER.debug("Start getCommonInfoTest method");
		commonInfo = service.getCommonInfo(itemVariant_1FromDb.getId());
		Assert.notNull(commonInfo, "instance must not be empty");

		Assert.isTrue(
				(commonInfo.getItemVariantId() != null) && (commonInfo.getItemName() != null)
						&& (commonInfo.getItemDescription() != null) && (commonInfo.getItemVariantPrice() != null),
				"columns values must not by empty");

		LOGGER.debug("Finish  getCommonInfoTest method");
	}

	/*
	 * Test for the getting of several objects, for each are checked for the
	 * existence and filling of fields
	 */

	@Test
	public void getCommonInfoForAllTest() {
		LOGGER.debug("Start getCommonInfoForAllTest method");

		commonInfos = service.getCommonInfoForAll();
		for (ItemVariantCommonInfo instance : commonInfos) {
			Assert.notNull(instance, "instance must not be empty");
			Assert.isTrue(
					(instance.getItemVariantId() != null) && (instance.getItemName() != null)
							&& (instance.getItemDescription() != null) && (instance.getItemVariantPrice() != null),
					"columns values must not by empty");
		}
		LOGGER.debug("Finish  getCommonInfoForAllTest method");
	}

	@Test
	public void getCommonInfoFilteredTest() {
		LOGGER.debug("Start getCommonInfoFilteredTest method");
		ItemVariantCommonInfoFilter filter = new ItemVariantCommonInfoFilter();

	    String itemVariantDescription = itemFromDb.getDescription();
		String itemVariantName = itemFromDb.getName();
		BigDecimal itemVariantPrice = itemVariant_1FromDb.getVariantPrice();

		PaginationParams paginationParams = new PaginationParams();
		paginationParams.setLimit(5);
		paginationParams.setOffset(0);

		SortingParams sortingParams = new SortingParams();
		sortingParams.setSortColumn(ItemColumnNamesForSortingParams.price);

		sortingParams.setSortOrder(OrderDirectionForSortingParams.desc);

		// try to use LIKE by description filtering
		filter.setItemVariantDescription(itemVariantDescription);
		//filter.setItemVariantDescription(itemVariantDescription.substring(2, itemVariantDescription.length() - 2));

		// try to use LIKE by name filtering
		// filter.setItemVariantName(itemVariantName);

		//filter.setItemVariantName(itemVariantName.substring(2, itemVariantName.length() - 2));
		filter.setItemVariantName(itemVariantName);
		filter.setItemVariantPriceMIN(itemVariantPrice.multiply(new BigDecimal("0.5")));
		filter.setItemVariantPriceMAX(itemVariantPrice.multiply(new BigDecimal("1.5")));
		filter.setPaginationParams(paginationParams);
		filter.setSortingParams(sortingParams);

		commonInfos = service.getCommonInfoFiltered(filter);

		for (ItemVariantCommonInfo instance : commonInfos) {
			Assert.notNull(instance, "instance must not be empty");
			Assert.isTrue(
					(instance.getItemVariantId() != null) && (instance.getItemName() != null)
							&& (instance.getItemDescription() != null) && (instance.getItemVariantPrice() != null),
					"columns values must not by empty");
		}
		LOGGER.debug("Finish  getCommonInfoFilteredTest method");
	}

	@Test
	public void getDetailsTest() {
		LOGGER.debug("Start getDetailsTest method");
		details = service.getDetails(itemVariant_1FromDb.getId());
		for (ItemVariantDetail detail : details) {
			LOGGER.debug("Instance from query = {}", detail.toString());
			Assert.notNull(detail, "itemVariantDetail must be saved");
			Assert.isTrue(
					(detail.getItemVariantId() != null) && (detail.getAttributeName() != null)
							&& (detail.getAttributeValue() != null) && (detail.getAttributeMeasure() != null),
					"columns values must not by empty");
		}
		LOGGER.debug("Finish  getDetailsTest method");
	}

	@Test
	public void getSpecificationTest() {
		LOGGER.debug("Start getSpecificationTest method");
		commonInfo = service.getCommonInfo(itemVariant_1FromDb.getId());
		details = new ArrayList<>();
		details = service.getDetails(itemVariant_1FromDb.getId());
		specification = new ItemVariantSpecification();
		specification.setInfo(commonInfo);
		specification.setDetails(details);
		LOGGER.debug("Specification is created: {}", specification.toString());
		Assert.notNull(specification, "instance must be saved");

		Assert.isTrue((specification.getInfo() != null) && (specification.getDetails() != null),
				"columns values must not by empty");

		LOGGER.debug("Finish  getSpecificationTest method");
	}

}

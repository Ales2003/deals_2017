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

import ru.mail.ales2003.deals2017.datamodel.Item;
import ru.mail.ales2003.deals2017.datamodel.ItemVariant;

public class ItemVariantServiceTest extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemVariantServiceTest.class);

	@Inject
	private IItemVariantService service;

	@Inject
	private IItemService itemService;

	private ItemVariant instance_1;
	private ItemVariant instance_2;
	private ItemVariant instance_1FromDb;
	private ItemVariant instance_2FromDb;
	private ItemVariant modifiedInstance;

	private Item item;
	private Item itemFromDb;

	@Before
	public void runBeforeTestMethod() {
		LOGGER.debug("Start preparation of the method");

		LOGGER.debug("Creating item in Db");
		item = new Item();
		itemService.save(item);
		itemFromDb = itemService.get(item.getId());
		LOGGER.debug("Item was created with id={}", itemFromDb.getId());

		LOGGER.debug("Creating itemVariants in JVM");
		instance_1 = getInstance(itemFromDb.getId(), new BigDecimal("250.00"));
		instance_2 = getInstance(itemFromDb.getId(), new BigDecimal("350.00"));
		LOGGER.debug("ItemVariants in JVM were created");

		LOGGER.debug("Finish preparation of the method");
	}

	@After
	public void runAfterTestMethod() {
		LOGGER.debug("Start completion of the method");

		LOGGER.debug("Start deleting itemVariants from Db");
		for (ItemVariant iV : service.getAllItemVariants()) {
			deleteFromDb(iV.getId());
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
	 * Two objects with the same Id are compared: created in Java and saved in &
	 * extracted from the database
	 */
	@Test
	public void insertItemVariantTest() {
		LOGGER.debug("Start insertTest method");
		service.saveItemVariant(instance_1);
		instance_1FromDb = service.getItemVariant(instance_1.getId());

		Assert.notNull(instance_1FromDb, "instance must be saved");

		Assert.isTrue((instance_1FromDb.getItemId() != null) && (instance_1FromDb.getVariantPrice() != null),
				"columns values must not by empty");

		Assert.isTrue(
				instance_1FromDb.getItemId().equals(instance_1.getItemId())
						&& instance_1FromDb.getVariantPrice().equals(instance_1.getVariantPrice()),
				"values of the corresponding columns must by eq.");
		LOGGER.debug("Finish insertTest method");
	}

	/*
	 * Test for the insertion of several objects, for each are compared two
	 * objects with the same Id: created in Java and saved in & extracted from
	 * the database
	 */
	@Test
	public void insertItemVariantMultipleTest() {

		LOGGER.debug("Start insertMultipleTest method");

		service.saveItemVariantMultiple(instance_1, instance_2);
		instance_1FromDb = service.getItemVariant(instance_1.getId());
		instance_2FromDb = service.getItemVariant(instance_2.getId());

		Assert.notNull(instance_1FromDb, "instance_1 must be saved");
		Assert.notNull(instance_2FromDb, "instance_2 must be saved");

		Assert.isTrue(instance_1FromDb.getItemId() != null && instance_1FromDb.getVariantPrice() != null,
				"columns must not by empty");

		Assert.isTrue(instance_2FromDb.getItemId() != null && instance_2FromDb.getVariantPrice() != null,
				"columns must not by empty");

		Assert.isTrue(
				instance_1FromDb.getItemId().equals(instance_1.getItemId())
						&& instance_1FromDb.getVariantPrice().equals(instance_1.getVariantPrice()),
				"values of the corresponding columns must by eq.");

		Assert.isTrue(
				instance_2FromDb.getItemId().equals(instance_2.getItemId())
						&& instance_2FromDb.getVariantPrice().equals(instance_2.getVariantPrice()),
				"values of the corresponding columns must by eq.");

		LOGGER.debug("Finish  insertMultipleTest method");
	}

	/*
	 * Three objects with the same Id are compared: created in Java, modified in
	 * Java and saved in & extracted from the database
	 */
	@Test
	public void updateItemVariantTest() {
		LOGGER.debug("Start updateTest method");
		service.saveItemVariant(instance_1);

		modifiedInstance = service.getItemVariant(instance_1.getId());
		modifiedInstance.setVariantPrice(instance_1.getVariantPrice().add(new BigDecimal("2")));

		service.saveItemVariant(modifiedInstance);

		instance_1FromDb = service.getItemVariant(modifiedInstance.getId());

		Assert.isTrue((instance_1.getId().equals(modifiedInstance.getId())),
				"id of initial instance must by eq. to modified instance id");

		Assert.isTrue(!(instance_1.getVariantPrice().equals(modifiedInstance.getVariantPrice())),
				"values of the corresponding columns of initial and modified instances must not by eq.");

		Assert.isTrue((instance_1FromDb.getId().equals(modifiedInstance.getId())),
				"id of instance from Db must by eq. to id of  modified instances");

		Assert.isTrue(instance_1FromDb.getVariantPrice().equals(modifiedInstance.getVariantPrice()),
				"values of the corresponding columns of instance from Db and modified instances must by eq.");

		LOGGER.debug("Finish  updateTest method");
	}

	/*
	 * Test for the getting an object. Two objects with the same Id are
	 * compared: created in Java and saved in & extracted from the database
	 */
	@Test
	public void getItemVariantTest() {
		LOGGER.debug("Start getTest method");
		service.saveItemVariant(instance_1);
		instance_1FromDb = service.getItemVariant(instance_1.getId());

		Assert.notNull(instance_1FromDb, "instance must be saved");

		Assert.isTrue((instance_1FromDb.getItemId() != null) && (instance_1FromDb.getVariantPrice() != null),
				"columns values must not by empty");

		Assert.isTrue(
				instance_1FromDb.getItemId().equals(instance_1.getItemId())
						&& instance_1FromDb.getVariantPrice().equals(instance_1.getVariantPrice()),
				"values of the corresponding columns must by eq.");

		LOGGER.debug("Finish  getTest method");
	}

	/*
	 * Test for the getting of several objects, for each are compared two
	 * objects with the same Id: created in Java and saved in & extracted from
	 * the database
	 */
	@Test
	public void getAllItemVariantTest() {
		LOGGER.debug("Start getAllTest method");
		List<ItemVariant> instances = new ArrayList<>();
		instances.add(instance_1);
		instances.add(instance_2);
		service.saveItemVariantMultiple(instance_1, instance_2);
		List<ItemVariant> instancesFromDb = service.getAllItemVariants();
		Assert.isTrue(instances.size() == instancesFromDb.size(),
				"count of from Db instances must by eq. to count of inserted instances");

		for (int i = 0; i < instances.size(); i++) {
			// False if compared to ==, since the references to objects
			Assert.isTrue(instances.get(i).getId().equals(instancesFromDb.get(i).getId()),
					"id of every instance from Db must by eq. to appropriate prepared instance id");

			Assert.isTrue(
					instances.get(i).getItemId().equals(instancesFromDb.get(i).getItemId())
							&& instances.get(i).getVariantPrice().equals(instancesFromDb.get(i).getVariantPrice()),
					"column's values of every instance from Db must by eq. to appropriate prepared instance's column's values");
		}
		LOGGER.debug("Finish getAllTest method");
	}

	/*
	 * Test for the deleting. One object is created, saved in DB and deleted.
	 * Then the object is checked for absence in the database
	 */
	@Test(expected = IllegalArgumentException.class)
	public void deleteItemVariantTest() {
		LOGGER.debug("Start deleteTest method");
		service.saveItemVariant(instance_1);
		instance_1FromDb = service.getItemVariant(instance_1.getId());
		deleteFromDb(instance_1.getId());
		Assert.notNull(instance_1FromDb, "instance must be saved");
		Assert.isNull(service.getItemVariant(instance_1.getId()), "instance must be deleted");
		LOGGER.debug("Finish deleteTest method");
	}

	// =============To do tests for attributDao===============

	/*
	 * method creates a new instance & gives it args
	 */
	private ItemVariant getInstance(Integer itemId, BigDecimal variantPrice) {
		ItemVariant instance = new ItemVariant();
		instance.setItemId(itemId);
		instance.setVariantPrice(variantPrice);
		return instance;
	}

	/*
	 * method deletes an instance by id
	 */
	private void deleteFromDb(Integer id) {
		service.deleteItemVariant(id);
	}

}

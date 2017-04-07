package ru.mail.ales2003.deals2017.services;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import ru.mail.ales2003.deals2017.datamodel.Item;

public class ItemServiceTest extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemServiceTest.class);

	@Inject
	private IItemService service;

	private Item instance_1;
	private Item instance_2;
	private Item instance_1FromDb;
	private Item instance_2FromDb;
	private Item modifiedInstance;

	@Before
	public void runBeforeTestMethod() {
		LOGGER.debug("Start preparation of the method");

		LOGGER.debug("Creating items in JVM");
		instance_1 = getInstance("Convector", "A device that warms a room by creating a current of hot air",
				new BigDecimal("200.00"));
		// Also I can so:
		// BigDecimal.valueOf(200.00).setScale(2, BigDecimal.ROUND_HALF_EVEN));
		// new BigDecimal(200.00).setScale(2, BigDecimal.ROUND_HALF_EVEN));
		// new BigDecimal("300").setScale(2, BigDecimal.ROUND_HALF_EVEN));
		instance_2 = getInstance("Air conditioner", "A machine that keeps the air in a building cool",
				new BigDecimal("300.00"));

		LOGGER.debug("Item in JVM was created");

		LOGGER.debug("Finish preparation of the method");
	}

	@After
	public void runAfterTestMethod() {
		LOGGER.debug("Start completion of the method");

		LOGGER.debug("Start deleting items from Db");
		for (Item c : service.getAll()) {
			deleteFromDb(c.getId());
		}
		LOGGER.debug("Items were deleted from Db ");

		LOGGER.debug("Finish completion of the method");
	}

	/*
	 * Two objects with the same Id are compared: created in Java and saved in &
	 * extracted from the database
	 */
	@Test
	public void insertTest() {
		LOGGER.debug("Start insertTest method");
		service.save(instance_1);
		instance_1FromDb = service.get(instance_1.getId());

		Assert.notNull(instance_1FromDb, "instance must be saved");

		Assert.isTrue((instance_1FromDb.getName() != null) && (instance_1FromDb.getDescription() != null)
				&& (instance_1FromDb.getBasicPrice() != null), "columns values must not by empty");

		Assert.isTrue(
				instance_1FromDb.getName().equals(instance_1.getName())
						&& instance_1FromDb.getDescription().equals(instance_1.getDescription())
						&& instance_1FromDb.getBasicPrice().equals(instance_1.getBasicPrice()),
				"values of the corresponding columns must by eq.");
		LOGGER.debug("Finish insertTest method");
	}

	/*
	 * Test for the insertion of several objects, for each are compared two
	 * objects with the same Id: created in Java and saved in & extracted from
	 * the database
	 */
	@Test
	public void insertMultipleTest() {

		LOGGER.debug("Start insertMultipleTest method");

		service.saveMultiple(instance_1, instance_2);
		instance_1FromDb = service.get(instance_1.getId());
		instance_2FromDb = service.get(instance_2.getId());

		Assert.notNull(instance_1FromDb, "instance_1 must be saved");
		Assert.notNull(instance_2FromDb, "instance_2 must be saved");

		Assert.isTrue(instance_1FromDb.getName() != null && instance_1FromDb.getDescription() != null
				&& instance_1FromDb.getBasicPrice() != null, "columns must not by empty");

		Assert.isTrue(instance_2FromDb.getName() != null && instance_2FromDb.getDescription() != null
				&& instance_2FromDb.getBasicPrice() != null, "columns must not by empty");

		Assert.isTrue(
				instance_1FromDb.getName().equals(instance_1.getName())
						&& instance_1FromDb.getDescription().equals(instance_1.getDescription())
						&& instance_1FromDb.getBasicPrice().equals(instance_1.getBasicPrice()),
				"values of the corresponding columns must by eq.");

		Assert.isTrue(
				instance_2FromDb.getName().equals(instance_2.getName())
						&& instance_2FromDb.getDescription().equals(instance_2.getDescription())
						&& instance_2FromDb.getBasicPrice().equals(instance_2.getBasicPrice()),
				"values of the corresponding columns must by eq.");

		LOGGER.debug("Finish  insertMultipleTest method");
	}

	// TO DO : updateTest(), getTest(), getAllTest(), deleteTest()

	/*
	 * method creates a new instance & gives it args
	 */
	private Item getInstance(String name, String description, BigDecimal basicPrice) {
		Item instance = new Item();
		instance.setName(name);
		instance.setDescription(description);
		instance.setBasicPrice(basicPrice);
		return instance;
	}

	/*
	 * method deletes an instance by id
	 */
	private void deleteFromDb(Integer id) {
		service.delete(id);
	}
}

package ru.mail.ales2003.deals2017.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import ru.mail.ales2003.deals2017.datamodel.Manager;

public class ManagerServiceTest extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ManagerServiceTest.class);

	@Inject
	private IManagerService service;

	private Manager instance_1;
	private Manager instance_2;
	private Manager instance_1FromDb;
	private Manager instance_2FromDb;
	private Manager modifiedInstance;

	@Before
	public void runBeforeTestMethod() {
		LOGGER.debug("Start preparation of the method");
		instance_1 = getInstance("Nikita", "Sergeevich", "Samohval", "general manager");
		instance_2 = getInstance("Andrei", "Ivanovich", "Ratich", "junior manager");
		LOGGER.debug("Finish preparation of the method");
	}

	@After
	public void runAfterTestMethod() {
		LOGGER.debug("Start completion of the method");
		for (Manager cg : service.getAll()) {
			deleteFromDb(cg.getId());
		}
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

		Assert.notNull(instance_1FromDb.getFirstName(), "'firstname' column must not by empty");
		Assert.notNull(instance_1FromDb.getPatronymic(), "'patronymic' column must not by empty");
		Assert.notNull(instance_1FromDb.getLastName(), "'lastName' column must not by empty");
		Assert.notNull(instance_1FromDb.getPosition(), "'position' column must not by empty");

		Assert.isTrue(
				instance_1FromDb.getFirstName().equals(instance_1.getFirstName())
						&& instance_1FromDb.getPatronymic().equals(instance_1.getPatronymic())
						&& instance_1FromDb.getLastName().equals(instance_1.getLastName())
						&& instance_1FromDb.getPosition().equals(instance_1.getPosition()),
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

		Assert.isTrue(
				(instance_1FromDb.getFirstName() != null) && (instance_1FromDb.getPatronymic() != null)
						&& (instance_1FromDb.getLastName() != null) && (instance_1FromDb.getPosition() != null),
				"columns values must not by empty");

		Assert.isTrue(
				(instance_2FromDb.getFirstName() != null) && (instance_2FromDb.getPatronymic() != null)
						&& (instance_2FromDb.getLastName() != null) && (instance_2FromDb.getPosition() != null),
				"columns values must not by empty");

		Assert.isTrue(
				instance_1FromDb.getFirstName().equals(instance_1.getFirstName())
						&& instance_1FromDb.getPatronymic().equals(instance_1.getPatronymic())
						&& instance_1FromDb.getLastName().equals(instance_1.getLastName())
						&& instance_1FromDb.getPosition().equals(instance_1.getPosition()),
				"values of the corresponding columns must by eq.");

		Assert.isTrue(
				instance_2FromDb.getFirstName().equals(instance_2.getFirstName())
						&& instance_2FromDb.getPatronymic().equals(instance_2.getPatronymic())
						&& instance_2FromDb.getLastName().equals(instance_2.getLastName())
						&& instance_2FromDb.getPosition().equals(instance_2.getPosition()),
				"values of the corresponding columns must by eq.");
	}

	/*
	 * Three objects with the same Id are compared: created in Java, modified in
	 * Java and saved in & extracted from the database
	 */
	@Test
	public void updateTest() {
		LOGGER.debug("Start updateTest method");
		service.save(instance_1);
		modifiedInstance = service.get(instance_1.getId());
		modifiedInstance.setFirstName("modifiedName");
		modifiedInstance.setFirstName("modifiedName");
		modifiedInstance.setFirstName("modifiedName");
		modifiedInstance.setFirstName("modifiedPosition");
		service.save(modifiedInstance);

		instance_1FromDb = service.get(modifiedInstance.getId());

		Assert.isTrue((instance_1.getId().equals(modifiedInstance.getId())),
				"id of initial instance must by eq. to modified instance id");
		Assert.isTrue(
				!(instance_1.getFirstName().equals(modifiedInstance.getFirstName())
						&& instance_1.getPatronymic().equals(modifiedInstance.getPatronymic())
						&& instance_1.getLastName().equals(modifiedInstance.getLastName())
						&& instance_1.getPosition().equals(modifiedInstance.getPosition())),
				"values of the corresponding columns of initial and modified instances must not by eq.");

		Assert.isTrue((instance_1FromDb.getId().equals(modifiedInstance.getId())),
				"id of instance from Db must by eq. to modified instance id");
		Assert.isTrue(
				instance_1FromDb.getFirstName().equals(modifiedInstance.getFirstName())
						&& instance_1FromDb.getPatronymic().equals(modifiedInstance.getPatronymic())
						&& instance_1FromDb.getLastName().equals(modifiedInstance.getLastName())
						&& instance_1FromDb.getPosition().equals(modifiedInstance.getPosition()),
				"values of the corresponding columns of initial and modified instances must by eq.");

		LOGGER.debug("Finish  updateTest method");
	}

	/*
	 * Test for the getting an object. Two objects with the same Id are
	 * compared: created in Java and saved in & extracted from the database
	 */
	@Test
	public void getTest() {
		LOGGER.debug("Start getTest method");
		service.save(instance_1);
		instance_1FromDb = service.get(instance_1.getId());
		Assert.notNull(instance_1FromDb, "instance must be saved");

		Assert.isTrue(
				(instance_1FromDb.getFirstName() != null) && (instance_1FromDb.getPatronymic() != null)
						&& (instance_1FromDb.getLastName() != null) && (instance_1FromDb.getPosition() != null),
				"columns values must not by empty");

		Assert.isTrue(
				instance_1FromDb.getFirstName().equals(instance_1.getFirstName())
						&& instance_1FromDb.getPatronymic().equals(instance_1.getPatronymic())
						&& instance_1FromDb.getLastName().equals(instance_1.getLastName())
						&& instance_1FromDb.getPosition().equals(instance_1.getPosition()),
				"values of the corresponding columns must by eq.");

		LOGGER.debug("Finish  getTest method");
	}

	/*
	 * Test for the getting of several objects, for each are compared two
	 * objects with the same Id: created in Java and saved in & extracted from
	 * the database
	 */
	@Test
	public void getAllTest() {
		LOGGER.debug("Start getAllTest method");
		List<Manager> instances = new ArrayList<>();
		instances.add(instance_1);
		instances.add(instance_2);
		service.saveMultiple(instance_1, instance_2);
		List<Manager> instancesFromDb = service.getAll();
		Assert.isTrue(instances.size() == instancesFromDb.size(),
				"count of from Db instances must by eq. to count of inserted instances");

		for (int i = 0; i < instances.size(); i++) {
			// False if compared to ==, since the references to objects
			Assert.isTrue(instances.get(i).getId().equals(instancesFromDb.get(i).getId()),
					"id of every instance from Db must by eq. to appropriate prepared instance id");

			Assert.isTrue(
					instances.get(i).getFirstName().equals(instancesFromDb.get(i).getFirstName())
							&& instances.get(i).getPatronymic().equals(instancesFromDb.get(i).getPatronymic())
							&& instances.get(i).getLastName().equals(instancesFromDb.get(i).getLastName())
							&& instances.get(i).getPosition().equals(instancesFromDb.get(i).getPosition()),
					"column's values of every instance from Db must by eq. to appropriate prepared instance's column's values");
		}
		LOGGER.debug("Finish getAllTest method");
	}

	/*
	 * Test for the deleting. One object is created, saved in DB and deleted.
	 * Then the object is checked for absence in the database
	 */
	@Test
	public void deleteTest() {
		LOGGER.debug("Start deleteTest method");
		service.save(instance_1);
		instance_1FromDb = service.get(instance_1.getId());
		deleteFromDb(instance_1.getId());
		Assert.notNull(instance_1FromDb, "instance must be saved");
		Assert.isNull(service.get(instance_1.getId()), "instance must be deleted");
		LOGGER.debug("Finish deleteTest method");
	}

	/*
	 * method creates a new instance & gives it args
	 */
	private Manager getInstance(String firstName, String patronymic, String lastName, String position) {
		Manager instance = new Manager();
		instance.setFirstName(firstName);
		instance.setPatronymic(patronymic);
		instance.setLastName(lastName);
		instance.setPosition(position);
		return instance;
	}

	/*
	 * method deletes an instance by id
	 */
	private void deleteFromDb(Integer id) {
		service.delete(id);
	}

}

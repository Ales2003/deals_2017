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

import ru.mail.ales2003.deals2017.datamodel.CharacterType;
import ru.mail.ales2003.deals2017.datamodel.Measure;
import ru.mail.ales2003.deals2017.services.servicesexceptions.DuplicationKeyInformationException;

public class CharacterTypeServiceTest extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(CharacterTypeServiceTest.class);

	@Inject
	private ICharacterTypeService service;

	private CharacterType type_1;
	private CharacterType type_2;
	private CharacterType type_1FromDb;
	private CharacterType type_2FromDb;
	private CharacterType modifiedType;
	List<CharacterType> types;
	List<CharacterType> typesFromDb;

	@Before
	public void runBeforeTestMethod() {
		LOGGER.debug("Start preparation of the method");
		type_1 = getInstance(Measure.MM);
		type_2 = getInstance(Measure.KG);
		LOGGER.debug("Finish preparation of the method");
	}

	@After
	public void runAfterTestMethod() {

		LOGGER.debug("Start completion of the method");
		for (CharacterType ct : service.getAll()) {
			service.delete(ct.getId());
		}
		LOGGER.debug("Finish completion of the method");
		service.clearCharacterTypeSet();

	}

	/*
	 * Two objects with the same Id are compared: created in Java and extracted
	 * from the database
	 */
	@Test
	public void insertTest() {
		LOGGER.debug("Start insertTest method");
		service.save(type_1);
		type_1FromDb = service.get(type_1.getId());
		Assert.notNull(type_1FromDb, "group must be saved");
		Assert.notNull(type_1FromDb.getName(), "'name' column must not by empty");
		Assert.isTrue(type_1FromDb.getName().equals(type_1.getName()), "name from Db must by eq. to prepared name");
		LOGGER.debug("Finish insertTest method");
	}

	/*
	 * Test for the insertion of several objects, for each are compared two
	 * objects with the same Id: created in Java and extracted from the database
	 */
	@Test
	public void insertMultipleTest() {
		LOGGER.debug("Start insertMultipleTest method");
		service.saveMultiple(type_1, type_2);
		type_1FromDb = service.get(type_1.getId());
		type_2FromDb = service.get(type_2.getId());
		Assert.notNull(type_1FromDb, "type_1 must be saved");
		Assert.notNull(type_2FromDb, "type_2 must be saved");
		Assert.notNull(type_1FromDb.getName(), "'name' column must not by empty");
		Assert.notNull(type_2FromDb.getName(), "'name' column must not by empty");
		Assert.isTrue(type_1FromDb.getName().equals(type_1.getName()), "name from Db must by eq. to prepared name");
		Assert.isTrue(type_2FromDb.getName().equals(type_2.getName()), "name from Db must by eq. to prepared name");
		LOGGER.debug("Finish  insertMultipleTest method");
	}

	/*
	 * Three objects with the same Id are compared: created in Java, modified in
	 * Java and extracted from the database
	 */
	@Test
	public void updateTest() {
		LOGGER.debug("Start updateTest method");
		service.save(type_1);
		modifiedType = service.get(type_1.getId());
		modifiedType.setName(Measure.L);
		service.save(modifiedType);
		type_1FromDb = service.get(modifiedType.getId());
		Assert.isTrue((type_1.getId().equals(modifiedType.getId())),
				"id of initial type must by eq. to modified type id");
		Assert.isTrue(!(type_1.getName().equals(modifiedType.getName())),
				"name of initial and modified types must not by eq.");
		Assert.isTrue((type_1FromDb.getId().equals(modifiedType.getId())), "id from Db must by eq. to modified id");
		Assert.isTrue(type_1FromDb.getName().equals(modifiedType.getName()),
				"name from Db must by eq. to modified name");
		LOGGER.debug("Finish  updateTest method");
	}

	/*
	 * Test for the getting an object. Two objects with the same Id are
	 * compared: created in Java and saved in & extracted from the database
	 */
	@Test
	public void getTest() {
		LOGGER.debug("Start getTest method");
		service.save(type_1);
		type_1FromDb = service.get(type_1.getId());
		Assert.notNull(type_1FromDb, "type must be saved");
		Assert.notNull(type_1FromDb.getName(), "'name' column must not by empty");
		Assert.isTrue(type_1FromDb.getName().equals(type_1.getName()),
				"name of type from Db must by eq. to prepared types name");
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
		types = new ArrayList<>();
		types.add(type_1);
		types.add(type_2);
		service.saveMultiple(type_1, type_2);
		typesFromDb = service.getAll();
		Assert.isTrue(types.size() == typesFromDb.size(),
				"count of from Db types must by eq. to count of inserted types");
		for (int i = 0; i < types.size(); i++) {
			// False if compared to ==, since the references to objects
			Assert.isTrue(types.get(i).getId().equals(typesFromDb.get(i).getId()),
					"id of every type from Db must by eq. to appropriate prepared type id");
			Assert.isTrue(types.get(i).getName().equals(typesFromDb.get(i).getName()),
					"name of every type from Db must by eq. to appropriate prepared types name");
		}
		LOGGER.debug("Finish getAllTest method");
	}

	/*
	 * Test for the deleting. One object is created, saved in DB and deleted.
	 * Then the object is checked for absence in the database
	 */
	@Test(expected = IllegalArgumentException.class)
	public void deleteTest() {
		LOGGER.debug("Start deleteTest method");
		service.save(type_1);
		type_1FromDb = service.get(type_1.getId());
		deleteFromDb(type_1.getId());
		Assert.notNull(type_1FromDb, "type must be saved");
		// here it is expected the IllegalArgumentException.
		Assert.isNull(service.get(type_1.getId()), "type must be deleted");
		LOGGER.debug("Finish deleteTest method");
	}

	/*
	 * Method tests the throwing of IAE by re removal an exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void expectIAEByReRemovTest() {
		LOGGER.debug("Start expectIAEByReRemovTest method");
		service.save(type_1);
		type_1FromDb = service.get(type_1.getId());
		deleteFromDb(type_1.getId());
		// re-removal
		deleteFromDb(type_1.getId());
		LOGGER.debug("Finish expectIAEByReRemovTest method");
	}

	/*
	 * Method tests the throwing of IAE by reading the entity that doesn't exist
	 */
	@Test(expected = IllegalArgumentException.class)
	public void expectIAEByEmptinessReadingTest() {
		LOGGER.debug("Start expectIAEByEmptinessReadingTest method");
		service.save(type_1);
		type_1FromDb = service.get(type_1.getId());
		deleteFromDb(type_1.getId());
		// reading
		service.get(type_1.getId());
		LOGGER.debug("Finish expectIAEByEmptinessReadingTest method");
	}

	/*
	 * Method tests the throwing of IAE by reading the entity that doesn't exist
	 */
	@Test(expected = DuplicationKeyInformationException.class)
	public void expectDKIEByEmptinessReadingTest() {
		LOGGER.debug("Start expectDKIEByEmptinessReadingTest method");

		type_1.setName(Measure.M);
		type_2.setName(Measure.M);
		service.saveMultiple(type_1, type_2);

		LOGGER.debug("Finish expectDKIEByEmptinessReadingTest method");
	}

	/*
	 * method creates a new instance & gives it name
	 */
	private CharacterType getInstance(Measure name) {
		CharacterType instance = new CharacterType();
		instance.setName(name);
		return instance;
	}

	/*
	 * method deletes an instance by id
	 */
	private void deleteFromDb(Integer id) {
		service.delete(id);
	}

}

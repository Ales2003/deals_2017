package ru.mail.ales2003.deals2017.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ru.mail.ales2003.deals2017.dao.api.ICharacterTypeDao;
import ru.mail.ales2003.deals2017.datamodel.CharacterType;
import ru.mail.ales2003.deals2017.services.ICharacterTypeService;
import ru.mail.ales2003.deals2017.services.servicesexceptions.DuplicationKeyInformationException;

@Service
public class CharacterTypeServiceImpl implements ICharacterTypeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CharacterTypeServiceImpl.class);

	@Inject
	private ICharacterTypeDao characterTypeDao;

	private String className = CharacterType.class.getSimpleName();

	private static Set<String> characterTypeSet = new HashSet<>();

	@Override
	public CharacterType get(Integer id) {
		if (characterTypeDao.get(id) == null) {
			String errMsg = String.format("[%s] entity with id = [%s] don't exist in storage", className, id);
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg);
		} else {
			CharacterType entity = characterTypeDao.get(id);
			LOGGER.info("Read one {} entity: {}", className, entity.toString());
			return entity;
		}
	}

	@Override
	public List<CharacterType> getAll() {
		LOGGER.info("{} entities storage returns {} entities.", className, characterTypeDao.getAll().size());
		LOGGER.info("Read all {} entities:", className);
		for (CharacterType cT : characterTypeDao.getAll()) {
			LOGGER.info("{} entity = {}", className, cT.toString());
		}
		return characterTypeDao.getAll();
	}

	@Override
	public void save(CharacterType entity) {
		if (entity == null) {
			LOGGER.error("Error: as the {} entity was sent a null reference", className);
			return;
		} else if (entity.getId() == null) {

			// LOGGING
			refreshCharacterTypeSet();

			if (isExist(entity)) {
				String errMsg = String.format("You want to insert [%s] entity with name [%s]. But such exist already.",
						className, entity.getName().name());
				LOGGER.error("Error: Warning: {}", errMsg);
				throw new DuplicationKeyInformationException(errMsg);
			}

			// LOGGING

			characterTypeDao.insert(entity);
			LOGGER.info("Inserted new {} entity: {}", className, entity.toString());
		} else {
			characterTypeDao.update(entity);
			LOGGER.info("Updated one {} entity: {}", className, entity.toString());
		}
	}

	@Override
	public void saveMultiple(CharacterType... entityArray) {
		for (CharacterType entity : entityArray) {
			LOGGER.info("Inserted new {} entity from array: {}", className, entity.toString());
			save(entity);
		}
		LOGGER.info("{} entities from array were inserted", className);
	}

	@Override
	public void delete(Integer id) {
		if (id == null) {
			LOGGER.error("Error: as the id was sent a null reference");
			return;
		} else {
			characterTypeDao.delete(id);
			LOGGER.info("Deleted {} entity by id: {}", className, id);
		}
	}

	// Methods to avoid duplication in the CharacterType storage

	// LOGGING
	@Override
	public void refreshCharacterTypeSet() {
		clearCharacterTypeSet();
		fillCharacterTypeSet();
	}

	// LOGGING
	@Override
	public void fillCharacterTypeSet() {
		List<CharacterType> members = getAll();
		for (CharacterType instance : members) {
			String word = instance.getName().name();
			characterTypeSet.add(word);
		}
	}

	// LOGGING
	@Override
	public void clearCharacterTypeSet() {
		characterTypeSet.clear();
	}

	// LOGGING
	@Override
	public boolean isExist(CharacterType entity) {
		Boolean isExist = false;
		String word = entity.getName().name();
		if (isExist = characterTypeSet.contains(word)) {
			return isExist;
		}
		return isExist;
	}

}

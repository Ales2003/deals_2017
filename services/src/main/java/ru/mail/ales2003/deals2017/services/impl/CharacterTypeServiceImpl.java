package ru.mail.ales2003.deals2017.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ru.mail.ales2003.deals2017.dao.api.ICharacterTypeDao;
import ru.mail.ales2003.deals2017.datamodel.CharacterType;
import ru.mail.ales2003.deals2017.services.ICharacterTypeService;

@Service
public class CharacterTypeServiceImpl implements ICharacterTypeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CharacterTypeServiceImpl.class);

	@Inject
	private ICharacterTypeDao characterTypeDao;

	@Override
	public CharacterType get(Integer id) {
		if (characterTypeDao.get(id) == null) {
			LOGGER.error("Error: characterType with id = " + id + " don't exist in storage)");
			return null;
		} else {
			CharacterType entity = characterTypeDao.get(id);
			LOGGER.info("Read one characterType: id={}, name={}", entity.getId(), entity.getName());
			return entity;
		}
	}

	@Override
	public List<CharacterType> getAll() {
		if (characterTypeDao.getAll() == null) {
			LOGGER.error("Error: all characterTypes don't exist in storage");
			return null;
		} else {
			LOGGER.info("Read all characterTypes");
			return characterTypeDao.getAll();
		}
	}

	@Override
	public void save(CharacterType entity) {
		if (entity == null) {
			LOGGER.error("Error: as the characterType was sent a null reference");
			return;
		} else if (entity.getId() == null) {
			characterTypeDao.insert(entity);
			LOGGER.info("Inserted new characterType: id={}, name={}", entity.getId(), entity.getName());
		} else {
			characterTypeDao.update(entity);
			LOGGER.info("Updated characterType: id={}, name={}", entity.getId(), entity.getName());
		}
	}

	@Override
	public void saveMultiple(CharacterType... entityArray) {
		for (CharacterType entity : entityArray) {
			LOGGER.info("Inserted new caracterType from array: " + entity);
			save(entity);
		}
		LOGGER.info("Inserted characterTypes from array");
	}

	@Override
	public void delete(Integer id) {
		if(id==null){
			LOGGER.error("Error: as the id was sent a null reference");
			return;
		} else {
		characterTypeDao.delete(id);
		LOGGER.info("Deleted characterType by id: " + id);
		}
	}
}

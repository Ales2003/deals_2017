package ru.mail.ales2003.deals2017.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import ru.mail.ales2003.deals2017.dao.impl.db.ICharacterTypeDao;
import ru.mail.ales2003.deals2017.datamodel.CharacterType;
import ru.mail.ales2003.deals2017.services.ICharacterTypeService;

@Service
public class CharacterTypeServiceImpl implements ICharacterTypeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CharacterTypeServiceImpl.class);

	@Inject
	private ICharacterTypeDao characterTypeDao;

	@Override
	public CharacterType get(Integer id) {
		try {
			CharacterType entity = characterTypeDao.get(id);
			LOGGER.info("Read one CharacterType: id={}, name={}", entity.getId(), entity.getName());
			return entity;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<CharacterType> getAll() {
		LOGGER.info("Read all CharacterTypes");
		return characterTypeDao.getAll();
	}

	@Override
	public void save(CharacterType entity) {
		if (entity.getId() == null) {
			characterTypeDao.insert(entity);
			LOGGER.info("Inserted new CharacterType: id={}, name={}", entity.getId(), entity.getName());
		} else {
			characterTypeDao.update(entity);
			LOGGER.info("Updated new CharacterType: id={}, name={}", entity.getId(), entity.getName());
		}
	}

	@Override
	public void saveMultiple(CharacterType... entityArray) {
		for (CharacterType characterType : entityArray) {
			LOGGER.debug("Inserted new CharacterType from array: " + characterType);
			save(characterType);
		}
		LOGGER.info("Inserted CharacterTypes from array");
	}

	@Override
	public void delete(Integer id) {
		characterTypeDao.delete(id);
		LOGGER.info("Deleted CharacterType by id: " + id);
	}
}

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

	private String className = CharacterType.class.getSimpleName();
	
	
	@Override
	public CharacterType get(Integer id) {
		if (characterTypeDao.get(id) == null) {
			String errMsg = String.format("[%s] with id = [%s] don't exist in storage", className, id);
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException (errMsg);
		} else {
			CharacterType entity = characterTypeDao.get(id);
			LOGGER.info("Read one {}: id={}, name={}", className, entity.getId(), entity.getName());
			return entity;
		}
	}

	@Override
	public List<CharacterType> getAll() {
		LOGGER.info("{} storage returns {} entitys.", className,
				characterTypeDao.getAll().size());
		return characterTypeDao.getAll();
	}

	@Override
	public void save(CharacterType entity) {
		if (entity == null) {
			LOGGER.error("Error: as the {} was sent a null reference", className);
			return;
		} else if (entity.getId() == null) {
			characterTypeDao.insert(entity);
			LOGGER.info("Inserted new {}: id={}, name={}", className, entity.getId(), entity.getName());
		} else {
			characterTypeDao.update(entity);
			LOGGER.info("Updated {}: id={}, name={}", className, entity.getId(), entity.getName());
		}
	}

	@Override
	public void saveMultiple(CharacterType... entityArray) {
		for (CharacterType entity : entityArray) {
			LOGGER.info("Inserted new {} from array: {}", className, entity);
			save(entity);
		}
		LOGGER.info("Inserted {}s from array", className);
	}

	@Override
	public void delete(Integer id) {
		if (id == null) {
			LOGGER.error("Error: as the id was sent a null reference");
			return;
		} else {
			characterTypeDao.delete(id);
			LOGGER.info("Deleted {} by id: {}", className, id);
		}
	}
}

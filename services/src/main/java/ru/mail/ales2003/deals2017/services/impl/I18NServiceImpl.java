package ru.mail.ales2003.deals2017.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ru.mail.ales2003.deals2017.dao.api.II18NDao;
import ru.mail.ales2003.deals2017.datamodel.I18N;
import ru.mail.ales2003.deals2017.services.II18NService;

@Service
public class I18NServiceImpl implements II18NService {

	private static final Logger LOGGER = LoggerFactory.getLogger(I18NServiceImpl.class);

	@Inject
	private II18NDao i18nDao;

	private String className = I18N.class.getSimpleName();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ru.mail.ales2003.deals2017.services.II18NService#get(java.lang.Integer)
	 */
	@Override
	public I18N get(Integer id) {
		if (i18nDao.get(id) == null) {
			String errMsg = String.format("[%s] with id = [%s] don't exist in storage", className, id);
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg);
		} else {
			I18N entity = i18nDao.get(id);
			LOGGER.info("Read one {}Item: id={}, table_name={}, member_id={}, language={}, value={}", className,
					entity.getId(), entity.getTableName(), entity.getMemberId(), entity.getLanguage(),
					entity.getValue());
			return entity;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ru.mail.ales2003.deals2017.services.II18NService#getAll()
	 */
	@Override
	public List<I18N> getAll() {
		LOGGER.info("{} storage returns {} entitys.", className, i18nDao.getAll().size());
		LOGGER.info("Read all I18N:");
		for (I18N entity : i18nDao.getAll()) {
			LOGGER.info("I18N = {}", entity.toString());
		}
		return i18nDao.getAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ru.mail.ales2003.deals2017.services.II18NService#save(ru.mail.ales2003.
	 * deals2017.datamodel.I18N)
	 */
	@Override
	public void save(I18N entity) {
		if (entity == null) {
			LOGGER.error("Error: as the {}item was sent a null reference", className);
			return;
		} else if (entity.getId() == null) {
			i18nDao.insert(entity);
			LOGGER.info("Inserted new {}Item: id={}, table_name={}, member_id={}, language={}, value={}", className,
					entity.getId(), entity.getTableName(), entity.getMemberId(), entity.getLanguage(),
					entity.getValue());
		} else {
			i18nDao.update(entity);
			LOGGER.info("Updated {}item: id={}, table_name={}, member_id={}, language={}, value={}", className,
					entity.getId(), entity.getTableName(), entity.getMemberId(), entity.getLanguage(),
					entity.getValue());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ru.mail.ales2003.deals2017.services.II18NService#saveMultiple(ru.mail.
	 * ales2003.deals2017.datamodel.I18N[])
	 */
	@Override
	public void saveMultiple(I18N... entityArray) {
		for (I18N entity : entityArray) {
			LOGGER.info("Inserted new {} from array: {}", className, className, entity.toString());
			save(entity);
		}
		LOGGER.info("Inserted {}s from array", className);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ru.mail.ales2003.deals2017.services.II18NService#delete(java.lang.
	 * Integer)
	 */
	@Override
	public void delete(Integer id) {
		if (id == null) {
			LOGGER.error("Error: as the id was sent a null reference");
			return;
		} else {
			i18nDao.delete(id);
			LOGGER.info("Deleted {}item by id: {}", className, id);
		}
	}
}

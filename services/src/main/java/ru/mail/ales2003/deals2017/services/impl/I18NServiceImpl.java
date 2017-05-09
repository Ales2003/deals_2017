package ru.mail.ales2003.deals2017.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ru.mail.ales2003.deals2017.dao.api.II18NDao;
import ru.mail.ales2003.deals2017.datamodel.I18N;
import ru.mail.ales2003.deals2017.datamodel.Language;
import ru.mail.ales2003.deals2017.datamodel.Table;
import ru.mail.ales2003.deals2017.services.II18NService;

@Service
public class I18NServiceImpl implements II18NService {

	private static final Logger LOGGER = LoggerFactory.getLogger(I18NServiceImpl.class);

	@Inject
	private II18NDao i18nDao;

	@Inject
	private ForeignTranslateServiceStub translator;

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
			String errMsg = String.format("[%s] entity with id = [%s] don't exist in storage", className, id);
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg);
		} else {
			I18N entity = i18nDao.get(id);
			LOGGER.info("Read one {} entity: id={}, table_name={}, member_id={}, language={}, value={}, keyword={}",
					className, entity.getId(), entity.getTableName(), entity.getMemberId(), entity.getLanguage(),
					entity.getValue(), entity.getKeyword());
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
		LOGGER.info("{} entity storage returns {} entitys.", className, i18nDao.getAll().size());
		LOGGER.info("Read all {} entitys:", className);
		for (I18N entity : i18nDao.getAll()) {
			LOGGER.info("{} entity = {}", className, entity.toString());
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
			LOGGER.error("Error: as the {} entity was sent a null reference", className);
			return;
		} else if (entity.getId() == null) {
			i18nDao.insert(entity);
			LOGGER.info("Inserted new {} entity: {}", className, entity.toString());
		} else {
			i18nDao.update(entity);
			LOGGER.info("Updated one {} entity: {}", className, entity.toString());
		}
	}

	@Override
	public List<Integer> saveMultilingual(String keyword, Table tableName, Integer inOwnTableId) {
		LOGGER.info("Saving to I18N keyword = {} from table = {} with id in own table = {}.", keyword, tableName,
				inOwnTableId);
		List<Language> langs = new ArrayList<>(Arrays.asList(Language.values()));
		List<Integer> ids = new ArrayList<>();
		for (Language language : langs) {
			I18N entity = new I18N();

			entity.setTableName(tableName);
			entity.setKeyword(keyword);
			entity.setMemberId(inOwnTableId);

			entity.setLanguage(language);
			String value;
			try {
				value = ForeignTranslateServiceStub.translateFromEnglish(language, keyword);
			} catch (Exception e) {
				String errMsg = String.format(
						"Because keyword = [%s] from table = [%s] and id in own table = [%s] wasn't translated and  wasn't saved",
						keyword, tableName, inOwnTableId);
				LOGGER.error("Error: {}", errMsg);
				return null;
			}
			entity.setValue(value);
			save(entity);
			Integer savedId = entity.getId();
			LOGGER.info(
					"Keyword = {} from table = {} with id in own table = {} was sacsessfull saved to I18N with id = {} and value = {}.",
					keyword, tableName, inOwnTableId, savedId, value);
			ids.add(savedId);
		}
		return ids;
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
			LOGGER.info("Inserted new {} entity from array: {}", className, className, entity.toString());
			save(entity);
		}
		LOGGER.info("{} entitys from array were inserted", className);
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
			LOGGER.info("Deleted {} entity by id: {}", className, id);
		}
	}
}

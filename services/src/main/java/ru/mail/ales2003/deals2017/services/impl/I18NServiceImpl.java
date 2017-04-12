package ru.mail.ales2003.deals2017.services.impl;

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

	@Override
	public I18N get(Integer id) {
		if (i18nDao.get(id) == null) {
			LOGGER.error("Error: entity with id = " + id + " don't exist in storage)");
			return null;
		} else {
			I18N item = i18nDao.get(id);
			LOGGER.info("Read one i18nItem: id={}, table_name={}, member_id={}, language={}, value={}", item.getId(),
					item.getTableName(), item.getMemberId(), item.getLanguage(), item.getValue());
			return item;
		}
	}

	@Override
	public List<I18N> getAll() {
		if (i18nDao.getAll() == null) {
			LOGGER.error("Error: all i18nItems don't exist in storage");
			return null;
		} else {
			LOGGER.info("Read all i18nItems");
			return i18nDao.getAll();
		}
	}


	@Override
	public void save(I18N item) {
		if (item == null) {
			LOGGER.error("Error: as the i18nitem was sent a null reference");
			return;
		} else if (item.getId() == null) {
			i18nDao.insert(item);
			LOGGER.info("Inserted new i18nItem: id={}, table_name={}, member_id={}, language={}, value={}",
					item.getId(), item.getTableName(), item.getMemberId(), item.getLanguage(), item.getValue());
		} else {
			i18nDao.update(item);
			LOGGER.info("Updated i18nitem: id={}, table_name={}, member_id={}, language={}, value={}", item.getId(),
					item.getTableName(), item.getMemberId(), item.getLanguage(), item.getValue());
		}

	}

	@Override
	public void saveMultiple(I18N... itemArray) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Integer id) {
		if (id == null) {
			LOGGER.error("Error: as the id was sent a null reference");
			return;
		} else {
			i18nDao.delete(id);
			LOGGER.info("Deleted i18nitem by id: " + id);
		}
	}

	/*
	 * @After public void runAfterTestMethod() {
	 * LOGGER.debug("Start completion of the method"); for (Manager cg :
	 * service.getAll()) { deleteFromDb(cg.getId()); }
	 * LOGGER.debug("Finish completion of the method"); }
	 */

}

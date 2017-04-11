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

	@Override
	public I18N get(Integer id) {
		if (i18nDao.get(id) == null) {
			LOGGER.error("Error: manager with id = " + id + " don't exist in storage)");
			return null;
		} else {
			I18N i18n = i18nDao.get(id);
			// LOGGER.info("Read one manager: id={}, first_name={},
			// patronymic={}, last_name={}, position={}",
			// i18n.getId(), i18nr.getFirstName(), i18n.getPatronymic(),
			// i18n.getLastName(), i18n.getPosition());
			return i18n;
		}
	}

	@Override
	public List<I18N> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(I18N item) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveMultiple(I18N... itemArray) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

	/*
	 * @After public void runAfterTestMethod() {
	 * LOGGER.debug("Start completion of the method"); for (Manager cg :
	 * service.getAll()) { deleteFromDb(cg.getId()); }
	 * LOGGER.debug("Finish completion of the method"); }
	 */

}

package ru.mail.ales2003.deals2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ru.mail.ales2003.deals2017.datamodel.I18N;
import ru.mail.ales2003.deals2017.datamodel.Table;

public interface II18NService {

	/**
	 * @param id
	 * @return I18N entity
	 */
	I18N get(Integer id);

	/**
	 * @return List&ltI18N&gt entities
	 */
	List<I18N> getAll();

	/**
	 * @param entity
	 */
	@Transactional
	void save(I18N entity);

	@Transactional
	void saveMultilingual(String wordToTranslate, Table tableName, Integer inOwnTableId);

	/**
	 * @param entityArray
	 */
	@Transactional
	void saveMultiple(I18N... entityArray);

	/**
	 * @param id
	 */
	@Transactional
	void delete(Integer id);

}

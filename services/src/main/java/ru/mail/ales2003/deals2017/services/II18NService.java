package ru.mail.ales2003.deals2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ru.mail.ales2003.deals2017.datamodel.I18N;
import ru.mail.ales2003.deals2017.datamodel.Item;

public interface II18NService {
	I18N get(Integer id);

	List<I18N> getAll();

	@Transactional
	void save(I18N item);

	@Transactional
	void saveMultiple(I18N... itemArray);

	@Transactional
	void delete(Integer id);

}

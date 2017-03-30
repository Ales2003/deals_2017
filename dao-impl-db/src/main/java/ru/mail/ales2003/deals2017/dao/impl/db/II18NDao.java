package ru.mail.ales2003.deals2017.dao.impl.db;

import java.util.List;

import ru.mail.ales2003.deals2017.datamodel.I18N;

public interface II18NDao {

	I18N insert(I18N entity);

	I18N get(Integer id);

	void update(I18N entity);

	void delete(Integer id);

	List<I18N> getAll();

}

package ru.mail.ales2003.deals2017.dao.impl.db.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.impl.db.II18NDao;
import ru.mail.ales2003.deals2017.datamodel.I18N;

@Repository
public class I18NDaoImpl implements II18NDao {

	@Inject
	private JdbcTemplate jdbcTemplate;

	// =============CREATION AREA===============

	@Override
	public I18N insert(I18N entity) {
		// TODO Auto-generated method stub
		return null;
	}

	// =============READING AREA===============

	@Override
	public I18N get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<I18N> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	// =============UPDATE AREA===============

	@Override
	public void update(I18N entity) {
		// TODO Auto-generated method stub

	}

	// =============DELETE AREA===============

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

}

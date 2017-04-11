package ru.mail.ales2003.deals2017.dao.db.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.api.II18NDao;
import ru.mail.ales2003.deals2017.datamodel.I18N;

@Repository
public class I18NDaoImpl extends AbstractDaoImplDb<I18N, Integer>implements II18NDao {

	
	
	/*@Inject
	private JdbcTemplate jdbcTemplate;*/

	// =============CREATION AREA===============

	

	@Override
	public String getSelectQuery() {
		final String READ_BY_ID_SQL = "select * from manager";
		return READ_BY_ID_SQL;
	}

}

package ru.mail.ales2003.deals2017.dao.db.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import ru.mail.ales2003.deals2017.dao.api.GenericDao;

public abstract class AbstractDaoImplDb<T, PK> implements GenericDao<T, PK> {

	// ?? What is the sense in final variable?
	// TO IMPLEMENT in child
	private static Logger LOGGER;

	private Class tClassName;

	@Inject
	private JdbcTemplate jdbcTemplate;

	private String entityName;

	// TO IMPLEMENT
	public abstract String getSelectQuery();

	// =============CREATION AREA===============

	@Override
	public T insert(T entity) {

		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(PK id) {
		// final String READ_BY_ID_SQL = "select * from manager where id = ? ";
		final String READ_BY_ID_SQL = getSelectQuery() + "where id = ?";
		try {
			return (T) jdbcTemplate.queryForObject(READ_BY_ID_SQL, new Object[] { id },
					new BeanPropertyRowMapper<T>(tClassName));
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("Error: " + entityName + " with id = " + id + " don't exist in storage)", e);
			// throw e;
			return null;
		}
	}

	@Override
	public List<T> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(T entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(PK id) {
		// TODO Auto-generated method stub

	}

}

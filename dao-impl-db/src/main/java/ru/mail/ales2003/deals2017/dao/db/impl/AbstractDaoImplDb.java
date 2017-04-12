package ru.mail.ales2003.deals2017.dao.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.api.GenericDao;

@Repository
public abstract class AbstractDaoImplDb<T, PK> implements GenericDao<T, PK> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDaoImplDb.class);

	@Inject
	protected JdbcTemplate jdbcTemplate;

	// Methods to implementing for CREATION AREA
	protected abstract String getInsertQuery();
	protected abstract T setEntityId(T entity, Integer id);
	protected abstract void prepareStatementForInsert(PreparedStatement statement, T entity);

	// Methods to implementing for CREATION AREA
	protected abstract String getSelectQuery();
	protected abstract Class<T> getMappedClass();

	// Methods to implementing for UPDATING AREA
	protected abstract String getUpdateQuery();
	protected abstract void prepareStatementForUpdate(PreparedStatement ps, T entity);

	// Methods to implementing for DELETING AREA
	protected abstract String getDeleteQuery();

	// =============CREATION AREA===============

	@Override
	public T insert(T entity) {

		final String INSERT_SQL = getInsertQuery();

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
				prepareStatementForInsert(ps, entity);
				return ps;
			}
		}, keyHolder);
		Integer id = keyHolder.getKey().intValue();
		setEntityId(entity, id);
		return entity;
	}

	// =============READING AREA===============

	@Override
	public T get(PK id) {
		final String READ_BY_ID_SQL = getSelectQuery() + " where id = ?";
		try {
			return (T) jdbcTemplate.queryForObject(READ_BY_ID_SQL, new Object[] { id },
					(RowMapper<T>) new BeanPropertyRowMapper<T>(getMappedClass()));
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("Error: entity[class:" + getMappedClass().getCanonicalName() + "] with id = " + id
					+ " don't exist in storage", e);
			// throw e;
			return null;
		}
	}

	@Override
	public List<T> getAll() {
		try {
			List<T> entitys = jdbcTemplate.query(getSelectQuery(), new BeanPropertyRowMapper<T>(getMappedClass()));
			return entitys;
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("Error: all entitys[class:" + getMappedClass().getCanonicalName() + "] don't exist in storage",
					e);
			return null;
		}
	}

	// =============UPDATE AREA===============

	@Override
	public void update(T entity) {
		final String UPDATE_SQL = getUpdateQuery();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(UPDATE_SQL, new String[] { "id" });
				prepareStatementForUpdate(ps, entity);
				return ps;
			}
		});
	}

	// =============DELETE AREA===============

	@Override
	public void delete(PK id) {
		final String DELETE_BY_ID_SQL = getDeleteQuery();
		jdbcTemplate.update(DELETE_BY_ID_SQL + " where id=" + id);
	}
}

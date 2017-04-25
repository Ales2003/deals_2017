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

	// =====================Methods to implementing for CREATION AREA
	/**
	 * @return String InsertQuery
	 */
	protected abstract String getInsertQuery();

	/**
	 * @param entity
	 * @param id
	 * @return T entity
	 */
	protected abstract T setEntityId(T entity, Integer id);

	/**
	 * @param preparedStatement
	 * @param entity
	 */
	protected abstract void prepareStatementForInsert(PreparedStatement preparedStatement, T entity);

	// =====================Methods to implementing for READING AREA
	/**
	 * @return String SelectQuery
	 */
	protected abstract String getSelectQuery();

	/**
	 * @return Class<T> MappedClass
	 */
	protected abstract Class<T> getMappedClass();

	// =====================Methods to implementing for UPDATING AREA
	/**
	 * @return String UpdateQuery
	 */
	protected abstract String getUpdateQuery();

	/**
	 * @param preparedStatement
	 * @param entity
	 */
	protected abstract void prepareStatementForUpdate(PreparedStatement preparedStatement, T entity);

	// =====================Methods to implementing for DELETING AREA

	/**
	 * @return String DeleteQuery
	 */
	protected abstract String getDeleteQuery();

	// =============CREATION AREA===============

	/**
	 * @param entity
	 * @return T entity
	 */
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
	/**
	 * @param id
	 * @return T entity
	 */
	@Override
	public T get(PK id) {
		if (id == null) {
			throw new IllegalArgumentException("Error: as the id was sent a null reference.");
		}
		final String READ_BY_ID_SQL = getSelectQuery() + " where id = ?";
		try {
			return (T) jdbcTemplate.queryForObject(READ_BY_ID_SQL, new Object[] { id },
					(RowMapper<T>) new BeanPropertyRowMapper<T>(getMappedClass()));
		} catch (EmptyResultDataAccessException e) {
			String errMsg = String.format(
					"You want to READ the [%s] with id = [%s], but it doesn't exist in the storage.",
					getMappedClass().getSimpleName(), id);
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg, e);
		}
	}

	/**
	 * @return List&ltT&gt entitys
	 */
	@Override
	public List<T> getAll() {
		try {
			List<T> entitys = jdbcTemplate.query(getSelectQuery(), new BeanPropertyRowMapper<T>(getMappedClass()));
			LOGGER.debug("[{}]. Store returns [{}] entitys.", getMappedClass().getCanonicalName(), entitys.size());
			return entitys;
		} catch (EmptyResultDataAccessException e) {
			String errMsg = String.format("Class [%s]. Storage returns incorrect entity count.",
					getMappedClass().getCanonicalName());
			LOGGER.error("Error: {}", errMsg);
			throw e;
		}
	}

	// =============UPDATE AREA===============
	/**
	 * @param entity
	 */
	@Override
	public void update(T entity) {
		final String UPDATE_SQL = getUpdateQuery();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				// Value of "id" field is not required here, so I deleted it:
				// PreparedStatement ps =
				// connection.prepareStatement(UPDATE_SQL, new String[] { "id"
				// });
				PreparedStatement ps = connection.prepareStatement(UPDATE_SQL);
				prepareStatementForUpdate(ps, entity);
				return ps;
			}
		});
	}

	// =============DELETE AREA===============
	/**
	 * @param id
	 */
	@Override
	public void delete(PK id) {
		// can also add deletion check

		final String DELETE_BY_ID_SQL = getDeleteQuery();
		int i = jdbcTemplate.update(DELETE_BY_ID_SQL + " where id=" + id);
		if (i == 0) {
			String errMsg = String.format(
					"You want to DELETE the [%s] with id = [%s], but it doesn't exist in the storage.",
					getMappedClass().getSimpleName(), id);
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg);
		}
	}
}

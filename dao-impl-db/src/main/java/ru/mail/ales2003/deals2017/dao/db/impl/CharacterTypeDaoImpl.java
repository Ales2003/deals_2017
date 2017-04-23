package ru.mail.ales2003.deals2017.dao.db.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.api.ICharacterTypeDao;
import ru.mail.ales2003.deals2017.datamodel.CharacterType;

/**
 * @author admin
 *
 */
@Repository
public class CharacterTypeDaoImpl extends AbstractDaoImplDb<CharacterType, Integer> implements ICharacterTypeDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(CharacterTypeDaoImpl.class);

	// =============CREATION AREA===============

	@Override
	protected String getInsertQuery() {
		final String INSERT_SQL = "insert into character_type (name) values(?)";
		return INSERT_SQL;
	}

	@Override
	protected CharacterType setEntityId(CharacterType entity, Integer id) {
		entity.setId(id);
		return entity;
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement ps, CharacterType entity) {
		try {
			ps.setString(1, entity.getName().name());
		} catch (SQLException e) {
			LOGGER.error("Error: prepareStatementForInsert is broken: {}", e);
			// !!!TO REFACTOR!!!
			throw new RuntimeException(e);
		}
	}

	// =============READING AREA===============

	@Override
	protected String getSelectQuery() {
		final String READ_BY_ID_SQL = "select * from character_type";
		return READ_BY_ID_SQL;
	}

	@Override
	protected Class<CharacterType> getMappedClass() {
		return CharacterType.class;
	}

	// =============UPDATE AREA===============

	@Override
	protected String getUpdateQuery() {
		final String UPDATE_SQL = "update character_type set name = ? where id = ?";
		return UPDATE_SQL;
	}

	@Override
	protected void prepareStatementForUpdate(PreparedStatement ps, CharacterType entity) {
		try {
			ps.setString(1, entity.getName().name());
			ps.setInt(2, entity.getId());
		} catch (SQLException e) {
			LOGGER.error("Error: prepareStatementForInsert is broken", e);
			// !!!TO REFACTOR!!!
			throw new RuntimeException(e);
		}
	}

	// =============DELETE AREA===============

	@Override
	protected String getDeleteQuery() {
		final String DELETE_BY_ID_SQL = "delete from character_type";
		return DELETE_BY_ID_SQL;
	}

	// =============CREATION AREA===============
	/*
	 * @Override public CharacterType insert(CharacterType entity) { final
	 * String INSERT_SQL = "insert into character_type (name) values(?)";
	 * KeyHolder keyHolder = new GeneratedKeyHolder(); jdbcTemplate.update(new
	 * PreparedStatementCreator() {
	 * 
	 * @Override public PreparedStatement createPreparedStatement(Connection
	 * connection) throws SQLException { PreparedStatement ps =
	 * connection.prepareStatement(INSERT_SQL, new String[] { "id" });
	 * ps.setString(1, entity.getName().name()); return ps; } }, keyHolder);
	 * entity.setId(keyHolder.getKey().intValue()); return entity; }
	 */

	// =============READING AREA===============

	/*
	 * @Override public CharacterType get(Integer id) { if (id == null) { throw
	 * new
	 * IllegalArgumentException("Error: as the id was sent a null reference"); }
	 * final String READ_BY_ID_SQL =
	 * "select * from character_type where id = ? "; try { return
	 * jdbcTemplate.queryForObject(READ_BY_ID_SQL, new Object[] { id }, new
	 * BeanPropertyRowMapper<CharacterType>(CharacterType.class)); } catch
	 * (EmptyResultDataAccessException e) { String errMsg =
	 * String.format("characterType with id = [%s] don't exist in storage)",
	 * id); LOGGER.error("Error: {}", errMsg); throw new
	 * IllegalArgumentException(errMsg, e); } }
	 */

	/*@Override public List<CharacterType> getAll() { 
		try { final String 	}
	 READ_ALL_SQL = "select * from character_type";
	 List<CharacterType> characterTypes = jdbcTemplate.query(READ_ALL_SQL, new BeanPropertyRowMapper<CharacterType>(CharacterType.class));
	 LOGGER.debug("[{}]. Store returns [{}] entitys.",  CharacterType.class.getSimpleName(), characterTypes.size()); return
	 characterTypes; }
	catch (EmptyResultDataAccessException e) { LOGGER.error("[%s]. Store returns incorrect entity count.",
	 CharacterType.class.getSimpleName()); return null;
	 throw e; }
	}*/
	 

	// =============UPDATE AREA===============
	/*
	 * @Override public void update(CharacterType entity) { final String
	 * UPDATE_SQL = "update character_type set name = ? where id = ?";
	 * jdbcTemplate.update(new PreparedStatementCreator() {
	 * 
	 * @Override public PreparedStatement createPreparedStatement(Connection
	 * connection) throws SQLException { PreparedStatement ps =
	 * connection.prepareStatement(UPDATE_SQL, new String[] { "id" });
	 * ps.setString(1, entity.getName().name()); ps.setInt(2, entity.getId());
	 * return ps; } }); }
	 */
	// =============DELETE AREA===============

	/*
	 * @Override public void delete(Integer id) { // can add deletion check
	 * 
	 * jdbcTemplate.update("delete from character_type where id=" + id);
	 * 
	 * }
	 */

}

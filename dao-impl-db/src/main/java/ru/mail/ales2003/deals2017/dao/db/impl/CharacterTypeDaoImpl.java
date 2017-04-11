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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.api.ICharacterTypeDao;
import ru.mail.ales2003.deals2017.datamodel.CharacterType;

@Repository
public class CharacterTypeDaoImpl implements ICharacterTypeDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(CharacterTypeDaoImpl.class);

	@Inject
	private JdbcTemplate jdbcTemplate;

	// =============CREATION AREA===============

	@Override
	public CharacterType insert(CharacterType entity) {
		final String INSERT_SQL = "insert into character_type (name) values(?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
				ps.setString(1, entity.getName());
				return ps;
			}
		}, keyHolder);
		entity.setId(keyHolder.getKey().intValue());
		return entity;
	}

	// =============READING AREA===============

	@Override
	public CharacterType get(Integer id) {
		final String READ_BY_ID_SQL = "select * from character_type where id = ? ";
		try {
			return jdbcTemplate.queryForObject(READ_BY_ID_SQL, new Object[] { id },
					new BeanPropertyRowMapper<CharacterType>(CharacterType.class));
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("Error: characterType with id = " + id + " don't exist in storage)", e);
			//throw e;
			return null;
		}
	}

	@Override
	public List<CharacterType> getAll() {
		try {
			final String READ_ALL_SQL = "select * from character_type";
			List<CharacterType> characterTypes = jdbcTemplate.query(READ_ALL_SQL,
					new BeanPropertyRowMapper<CharacterType>(CharacterType.class));
			return characterTypes;
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("Error: all сharacterTypes don't exist in storage", e);
			return null;
		}
	}

	// =============UPDATE AREA===============

	@Override
	public void update(CharacterType entity) {
		final String UPDATE_SQL = "update character_type set name = ? where id = ?";
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(UPDATE_SQL, new String[] { "id" });
				ps.setString(1, entity.getName());
				ps.setInt(2, entity.getId());
				return ps;
			}
		});
	}

	// =============DELETE AREA===============

	@Override
	public void delete(Integer id) {
		jdbcTemplate.update("delete from character_type where id=" + id);
	}

}
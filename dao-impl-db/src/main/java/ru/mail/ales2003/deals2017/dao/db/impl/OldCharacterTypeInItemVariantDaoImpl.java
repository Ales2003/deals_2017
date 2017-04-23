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

import ru.mail.ales2003.deals2017.dao.api.ICharacterTypeInItemVariantDao;
import ru.mail.ales2003.deals2017.datamodel.CharacterTypeInItemVariant;

//@Repository
public class OldCharacterTypeInItemVariantDaoImpl implements ICharacterTypeInItemVariantDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(OldCharacterTypeInItemVariantDaoImpl.class);

	@Inject
	private JdbcTemplate jdbcTemplate;

	// =============CREATION AREA===============

	@Override
	public CharacterTypeInItemVariant insert(CharacterTypeInItemVariant entity) {
		final String INSERT_SQL = "insert into character_type_in_item_variant (item_variant_id, attribute, character_type_id, value) values(?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
				ps.setInt(1, entity.getItemVariantId());
				ps.setString(2, entity.getAttribute().name());
				ps.setInt(3, entity.getCharacterTypeId());
				ps.setString(4, entity.getValue());
				return ps;
			}
		}, keyHolder);
		entity.setId(keyHolder.getKey().intValue());
		return entity;
	}

	// =============READING AREA===============

	@Override
	public CharacterTypeInItemVariant get(Integer id) {
		final String READ_BY_ID_SQL = "select * from character_type_in_item_variant where id = ? ";
		try {
			return jdbcTemplate.queryForObject(READ_BY_ID_SQL, new Object[] { id },
					new BeanPropertyRowMapper<CharacterTypeInItemVariant>(CharacterTypeInItemVariant.class));
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("Error: characterTypeInItemVariant with id = " + id + " don't exist in storage)", e);
			// throw e;
			return null;
		}
	}

	@Override
	public List<CharacterTypeInItemVariant> getAll() {
		try {
			final String READ_ALL_SQL = "select * from character_type_in_item_variant";
			List<CharacterTypeInItemVariant> characterTypes = jdbcTemplate.query(READ_ALL_SQL,
					new BeanPropertyRowMapper<CharacterTypeInItemVariant>(CharacterTypeInItemVariant.class));
			return characterTypes;
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("Error: all characterTypeInItemVariants don't exist in storage", e);
			return null;
		}
	}

	// =============UPDATE AREA===============

	@Override
	public void update(CharacterTypeInItemVariant entity) {
		final String UPDATE_SQL = "update character_type_in_item_variant set item_variant_id = ?, attribute = ?, character_type_id = ?, value =?  where id = ?";
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(UPDATE_SQL, new String[] { "id" });
				ps.setInt(1, entity.getItemVariantId());
				ps.setString(2, entity.getAttribute().name());
				ps.setInt(3, entity.getCharacterTypeId());
				ps.setString(4, entity.getValue());
				ps.setInt(5, entity.getId());
				return ps;
			}
		});
	}

	// =============DELETE AREA===============

	@Override
	public void delete(Integer id) {
		jdbcTemplate.update("delete from character_type_in_item_variant where id=" + id);

	}

}

package ru.mail.ales2003.deals2017.dao.db.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.api.ICharacterTypeInItemVariantDao;
import ru.mail.ales2003.deals2017.datamodel.CharacterTypeInItemVariant;

@Repository
public class CharacterTypeInItemVariantDaoImpl extends AbstractDaoImplDb<CharacterTypeInItemVariant, Integer>
		implements ICharacterTypeInItemVariantDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(CharacterTypeInItemVariantDaoImpl.class);

	// =============CREATION AREA===============

	@Override
	protected String getInsertQuery() {
		final String INSERT_SQL = "insert into character_type_in_item_variant (item_variant_id, attribute, character_type_id, value) values(?,?,?,?)";
		return INSERT_SQL;
	}

	@Override
	protected CharacterTypeInItemVariant setEntityId(CharacterTypeInItemVariant entity, Integer id) {
		entity.setId(id);
		return entity;
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement ps, CharacterTypeInItemVariant entity) {
		try {
			ps.setInt(1, entity.getItemVariantId());
			ps.setString(2, entity.getAttribute().name());
			ps.setInt(3, entity.getCharacterTypeId());
			ps.setString(4, entity.getValue());
		} catch (SQLException e) {
			LOGGER.error("Error: prepareStatementForInsert is broken: {}", e);
			// !!!TO REFACTOR!!!
			throw new RuntimeException(e);
		}
	}

	// =============READING AREA===============

	@Override
	protected String getSelectQuery() {
		final String READ_BY_ID_SQL = "select * from character_type_in_item_variant";
		return READ_BY_ID_SQL;
	}

	@Override
	protected Class<CharacterTypeInItemVariant> getMappedClass() {
		return CharacterTypeInItemVariant.class;
	}

	// =============UPDATE AREA===============

	@Override
	protected String getUpdateQuery() {
		final String UPDATE_SQL = "update character_type_in_item_variant set item_variant_id = ?, attribute = ?, character_type_id = ?, value =?  where id = ?";
		return UPDATE_SQL;
	}

	@Override
	protected void prepareStatementForUpdate(PreparedStatement ps, CharacterTypeInItemVariant entity) {
		try {
			ps.setInt(1, entity.getItemVariantId());
			ps.setString(2, entity.getAttribute().name());
			ps.setInt(3, entity.getCharacterTypeId());
			ps.setString(4, entity.getValue());
			ps.setInt(5, entity.getId());
		} catch (SQLException e) {
			LOGGER.error("Error: prepareStatementForInsert is broken", e);
			// !!!TO REFACTOR!!!
			throw new RuntimeException(e);
		}
	}

	// =============DELETE AREA===============

	@Override
	protected String getDeleteQuery() {
		final String DELETE_BY_ID_SQL = "delete from character_type_in_item_variant";
		return DELETE_BY_ID_SQL;
	}
}

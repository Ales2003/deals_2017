package ru.mail.ales2003.deals2017.dao.db.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.api.IItemVariantDao;
import ru.mail.ales2003.deals2017.datamodel.ItemVariant;

@Repository
public class ItemVariantDaoImpl extends AbstractDaoImplDb<ItemVariant, Integer> implements IItemVariantDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemVariantDaoImpl.class);

	// =============CREATION AREA===============

	@Override
	protected String getInsertQuery() {
		final String INSERT_SQL = "insert into item_variant (item_id, variant_price) values(?,?)";
		return INSERT_SQL;
	}

	@Override
	protected ItemVariant setEntityId(ItemVariant entity, Integer id) {
		entity.setId(id);
		return entity;
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement ps, ItemVariant entity) {
		try {
			ps.setInt(1, entity.getItemId());
			ps.setBigDecimal(2, entity.getVariantPrice());
		} catch (SQLException e) {
			LOGGER.error("Error: prepareStatementForInsert is broken: {}", e);
			// !!!TO REFACTOR!!!
			throw new RuntimeException(e);
		}
	}

	// =============READING AREA===============

	@Override
	protected String getSelectQuery() {
		final String READ_BY_ID_SQL = "select * from item_variant";
		return READ_BY_ID_SQL;
	}

	@Override
	protected Class<ItemVariant> getMappedClass() {
		return ItemVariant.class;
	}

	// =============UPDATE AREA===============

	@Override
	protected String getUpdateQuery() {
		final String UPDATE_SQL = "update item_variant set item_id = ?, variant_price = ? where id = ?";
		return UPDATE_SQL;
	}

	@Override
	protected void prepareStatementForUpdate(PreparedStatement ps, ItemVariant entity) {
		try {
			ps.setInt(1, entity.getItemId());
			ps.setBigDecimal(2, entity.getVariantPrice());
			ps.setInt(3, entity.getId());
		} catch (SQLException e) {
			LOGGER.error("Error: prepareStatementForInsert is broken", e);
			// !!!TO REFACTOR!!!
			throw new RuntimeException(e);
		}
	}

	// =============DELETE AREA===============

	@Override
	protected String getDeleteQuery() {
		final String DELETE_BY_ID_SQL = "delete from item_variant";
		return DELETE_BY_ID_SQL;
	}
}

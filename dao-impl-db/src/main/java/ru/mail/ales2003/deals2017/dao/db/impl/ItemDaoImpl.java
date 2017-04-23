package ru.mail.ales2003.deals2017.dao.db.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.api.IItemDao;
import ru.mail.ales2003.deals2017.datamodel.Item;

@Repository
public class ItemDaoImpl extends AbstractDaoImplDb<Item, Integer> implements IItemDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemDaoImpl.class);

	// =============CREATION AREA===============

	@Override
	protected String getInsertQuery() {
		final String INSERT_SQL = "insert into item (name, description, basic_price) values(?,?,?)";
		return INSERT_SQL;
	}

	@Override
	protected Item setEntityId(Item entity, Integer id) {
		entity.setId(id);
		return entity;
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement ps, Item entity) {
		try {
			ps.setString(1, entity.getName());
			ps.setString(2, entity.getDescription());
			ps.setBigDecimal(3, entity.getBasicPrice());
		} catch (SQLException e) {
			LOGGER.error("Error: prepareStatementForInsert is broken: {}", e);
			// !!!TO REFACTOR!!!
			throw new RuntimeException(e);
		}
	}

	// =============READING AREA===============

	@Override
	protected String getSelectQuery() {
		final String READ_BY_ID_SQL = "select * from item";
		return READ_BY_ID_SQL;
	}

	@Override
	protected Class<Item> getMappedClass() {
		return Item.class;
	}

	// =============UPDATE AREA===============

	@Override
	protected String getUpdateQuery() {
		final String UPDATE_SQL = "update item set name = ?, description = ?, basic_price = ? where id = ?";
		return UPDATE_SQL;
	}

	@Override
	protected void prepareStatementForUpdate(PreparedStatement ps, Item entity) {
		try {
			ps.setString(1, entity.getName());
			ps.setString(2, entity.getDescription());
			ps.setBigDecimal(3, entity.getBasicPrice());
			ps.setInt(4, entity.getId());
		} catch (SQLException e) {
			LOGGER.error("Error: prepareStatementForInsert is broken", e);
			// !!!TO REFACTOR!!!
			throw new RuntimeException(e);
		}
	}

	// =============DELETE AREA===============

	@Override
	protected String getDeleteQuery() {
		final String DELETE_BY_ID_SQL = "delete from item";
		return DELETE_BY_ID_SQL;
	}
}

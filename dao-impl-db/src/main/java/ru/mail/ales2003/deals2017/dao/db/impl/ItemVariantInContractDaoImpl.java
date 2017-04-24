package ru.mail.ales2003.deals2017.dao.db.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.api.IItemVariantInContractDao;
import ru.mail.ales2003.deals2017.datamodel.ItemVariantInContract;

@Repository
public class ItemVariantInContractDaoImpl extends AbstractDaoImplDb<ItemVariantInContract, Integer>
		implements IItemVariantInContractDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemVariantInContractDaoImpl.class);

	// =============CREATION AREA===============

	@Override
	protected String getInsertQuery() {
		final String INSERT_SQL = "insert into item_variant_in_contract (quantity, contract_id, item_variant_id) values(?,?,?)";
		return INSERT_SQL;
	}

	@Override
	protected ItemVariantInContract setEntityId(ItemVariantInContract entity, Integer id) {
		entity.setId(id);
		return entity;
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement ps, ItemVariantInContract entity) {
		try {
			ps.setInt(1, entity.getQuantity());
			ps.setInt(2, entity.getContractId());
			ps.setInt(3, entity.getItemVariantId());
		} catch (SQLException e) {
			LOGGER.error("Error: prepareStatementForInsert is broken: {}", e);
			// !!!TO REFACTOR!!!
			throw new RuntimeException(e);
		}

	}

	// =============READING AREA===============

	@Override
	protected String getSelectQuery() {
		final String READ_BY_ID_SQL = "select * from item_variant_in_contract";
		return READ_BY_ID_SQL;
	}

	@Override
	protected Class<ItemVariantInContract> getMappedClass() {
		return ItemVariantInContract.class;
	}

	// =============UPDATE AREA===============

	@Override
	protected String getUpdateQuery() {
		final String UPDATE_SQL = "update item_variant_in_contract set quantity = ?, contract_id =?, item_variant_id=?  where id = ?";
		return UPDATE_SQL;
	}

	@Override
	protected void prepareStatementForUpdate(PreparedStatement ps, ItemVariantInContract entity) {
		try {
			ps.setInt(1, entity.getQuantity());
			ps.setInt(2, entity.getContractId());
			ps.setInt(3, entity.getItemVariantId());
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
		final String DELETE_BY_ID_SQL = "delete from item_variant_in_contract";
		return DELETE_BY_ID_SQL;
	}
}

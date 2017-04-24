package ru.mail.ales2003.deals2017.dao.db.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.api.ICustomerGroupDao;
import ru.mail.ales2003.deals2017.datamodel.CustomerGroup;

@Repository
public class CustomerGroupDaoImpl extends AbstractDaoImplDb<CustomerGroup, Integer> implements ICustomerGroupDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerGroupDaoImpl.class);

	// =============CREATION AREA===============

	@Override
	protected String getInsertQuery() {
		final String INSERT_SQL = "insert into customer_group (name) values(?)";
		return INSERT_SQL;
	}

	@Override
	protected CustomerGroup setEntityId(CustomerGroup entity, Integer id) {
		entity.setId(id);
		return entity;
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement ps, CustomerGroup entity) {
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
		final String READ_BY_ID_SQL = "select * from customer_group";
		return READ_BY_ID_SQL;
	}

	@Override
	protected Class<CustomerGroup> getMappedClass() {
		return CustomerGroup.class;
	}

	// =============UPDATE AREA===============

	@Override
	protected String getUpdateQuery() {
		final String UPDATE_SQL = "update customer_group set name = ? where id = ?";
		return UPDATE_SQL;
	}

	@Override
	protected void prepareStatementForUpdate(PreparedStatement ps, CustomerGroup entity) {
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
		final String DELETE_BY_ID_SQL = "delete from customer_group";
		return DELETE_BY_ID_SQL;
	}
}

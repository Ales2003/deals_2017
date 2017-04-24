package ru.mail.ales2003.deals2017.dao.db.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.api.IManagerDao;
import ru.mail.ales2003.deals2017.datamodel.Manager;

@Repository
public class ManagerDaoImpl extends AbstractDaoImplDb<Manager, Integer> implements IManagerDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(ManagerDaoImpl.class);

	@Override
	protected String getInsertQuery() {
		final String INSERT_SQL = "insert into  manager (first_name, patronymic, last_name, position) values(?,?,?,?)";
		return INSERT_SQL;
	}

	@Override
	protected Manager setEntityId(Manager entity, Integer id) {
		entity.setId(id);
		return entity;
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement ps, Manager entity) {
		try {
			ps.setString(1, entity.getFirstName());
			ps.setString(2, entity.getPatronymic());
			ps.setString(3, entity.getLastName());
			ps.setString(4, entity.getPosition());
		} catch (SQLException e) {
			LOGGER.error("Error: prepareStatementForInsert is broken: {}", e);
			// !!!TO REFACTOR!!!
			throw new RuntimeException(e);
		}
	}

	// =============READING AREA===============

	@Override
	protected String getSelectQuery() {
		final String READ_BY_ID_SQL = "select * from manager";
		return READ_BY_ID_SQL;
	}

	@Override
	protected Class<Manager> getMappedClass() {
		return Manager.class;
	}

	// =============UPDATE AREA===============

	@Override
	protected String getUpdateQuery() {
		final String UPDATE_SQL = "update manager set first_name = ?, patronymic = ?, last_name = ?, position = ? where id = ?";
		return UPDATE_SQL;
	}

	@Override
	protected void prepareStatementForUpdate(PreparedStatement ps, Manager entity) {
		try {
			ps.setString(1, entity.getFirstName());
			ps.setString(2, entity.getPatronymic());
			ps.setString(3, entity.getLastName());
			ps.setString(4, entity.getPosition());
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
		final String DELETE_BY_ID_SQL = "delete from manager";
		return DELETE_BY_ID_SQL;
	}

}

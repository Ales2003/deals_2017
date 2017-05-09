package ru.mail.ales2003.deals2017.dao.db.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.api.II18NDao;
import ru.mail.ales2003.deals2017.datamodel.I18N;

@Repository
public class I18NDaoImpl extends AbstractDaoImplDb<I18N, Integer> implements II18NDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(I18NDaoImpl.class);

	// =============CREATION AREA===============

	@Override
	protected String getInsertQuery() {
		final String INSERT_SQL = "insert into i18n (table_name, member_id, language, value, keyword) values(?,?,?,?,?)";
		return INSERT_SQL;
	}

	@Override
	protected I18N setEntityId(I18N entity, Integer id) {
		entity.setId(id);
		return entity;
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement ps, I18N entity) {
		try {
			ps.setString(1, entity.getTableName().name());
			ps.setInt(2, entity.getMemberId());
			ps.setString(3, entity.getLanguage().name());
			ps.setString(4, entity.getValue());
			ps.setString(5, entity.getKeyword());
		} catch (SQLException e) {
			LOGGER.error("Error: prepareStatementForInsert is broken: {}", e);
			// !!!TO REFACTOR!!!
			throw new RuntimeException(e);
		}
	}

	// =============READING AREA===============

		@Override
	protected String getSelectQuery() {
		final String READ_BY_ID_SQL = "select * from i18n";
		return READ_BY_ID_SQL;
	}

		@Override
	protected Class<I18N> getMappedClass() {
		return I18N.class;
	}

	// =============UPDATE AREA===============

	@Override
	protected String getUpdateQuery() {
		final String UPDATE_SQL = "update i18n set table_name = ?, member_id = ?, language = ?, value =?, keyword =? where id = ?";
		return UPDATE_SQL;
	}

		@Override
	protected void prepareStatementForUpdate(PreparedStatement ps, I18N entity) {
		try {
			ps.setString(1, entity.getTableName().name());
			ps.setInt(2, entity.getMemberId());
			ps.setString(3, entity.getLanguage().name());
			ps.setString(4, entity.getValue());
			ps.setString(5, entity.getKeyword());
			ps.setInt(6, entity.getId());
		} catch (SQLException e) {
			LOGGER.error("Error: prepareStatementForInsert is broken", e);
			// !!!TO REFACTOR!!!
			throw new RuntimeException(e);
		}
	}

	// =============DELETE AREA===============

	@Override
	protected String getDeleteQuery() {
		final String DELETE_BY_ID_SQL = "delete from i18n";
		return DELETE_BY_ID_SQL;
	}
}

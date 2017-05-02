package ru.mail.ales2003.deals2017.dao.db.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.api.IUserAuthDao;
import ru.mail.ales2003.deals2017.datamodel.UserAuth;

@Repository
public class UserAuthDaoImpl extends AbstractDaoImplDb<UserAuth, Integer> implements IUserAuthDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthDaoImpl.class);

	@Override
	protected String getInsertQuery() {
		final String INSERT_SQL = "insert into user_auth (in_own_table_id, role, login, password) values(?,?,?,?)";
		return INSERT_SQL;
	}

	@Override
	protected UserAuth setEntityId(UserAuth entity, Integer id) {
		entity.setId(id);
		return entity;
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement ps, UserAuth entity) {
		try {
			ps.setInt(1, entity.getInOwnTableId());
			ps.setString(2, entity.getRole().name());
			ps.setString(3, entity.getLogin());
			ps.setString(4, entity.getPassword());
		} catch (SQLException e) {
			LOGGER.error("Error: prepareStatementForInsert is broken: {}", e);
			// !!!TO REFACTOR!!!
			throw new RuntimeException(e);
		}

	}

	@Override
	protected String getSelectQuery() {
		final String READ_BY_ID_SQL = "select * from user_auth";
		return READ_BY_ID_SQL;
	}

	@Override
	protected Class<UserAuth> getMappedClass() {
		return UserAuth.class;
	}

	@Override
	protected String getUpdateQuery() {
		final String UPDATE_SQL = "update user_auth set in_own_table_id = ?, role = ?, login = ?, password =? where id = ?";
		return UPDATE_SQL;
	}

	@Override
	protected void prepareStatementForUpdate(PreparedStatement ps, UserAuth entity) {
		try {
			ps.setInt(1, entity.getInOwnTableId());
			ps.setString(2, entity.getRole().name());
			ps.setString(3, entity.getLogin());
			ps.setString(4, entity.getPassword());
			ps.setInt(5, entity.getId());
		} catch (SQLException e) {
			LOGGER.error("Error: prepareStatementForInsert is broken", e);
			// !!!TO REFACTOR!!!
			throw new RuntimeException(e);
		}

	}

	@Override
	protected String getDeleteQuery() {
		final String DELETE_BY_ID_SQL = "delete from user_auth";
		return DELETE_BY_ID_SQL;
	}

	// ADVANCED METHODS

	@Override
	public UserAuth get(String login) {
		if (login == null) {
			String errMsg = String.format("Error: as the login was sent a null reference.");
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg);
		}
		final String READ_BY_LOGIN_SQL = getSelectQuery() + " where login = ?";
		try {
			return jdbcTemplate.queryForObject(READ_BY_LOGIN_SQL, new Object[] { login },
					new BeanPropertyRowMapper<UserAuth>(UserAuth.class));
		} catch (EmptyResultDataAccessException e) {
			String errMsg = String.format(
					"You want to READ the [%s] with login = [%s], but it doesn't exist in the storage.",
					getMappedClass().getSimpleName(), login);
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg, e);
		}
	}

	@Override
	public Set<String> getAllLogins() {
		List<String> rs = jdbcTemplate.query("select login from user_auth ",
				new BeanPropertyRowMapper<String>(String.class));
		Set<String> logins = new HashSet<>();
		logins.addAll(rs);
		return logins;
	}

}

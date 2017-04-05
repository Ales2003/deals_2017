package ru.mail.ales2003.deals2017.dao.impl.db.impl;

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

import ru.mail.ales2003.deals2017.dao.impl.db.IManagerDao;
import ru.mail.ales2003.deals2017.datamodel.Manager;

@Repository
public class ManagerDaoImpl implements IManagerDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(ManagerDaoImpl.class);

	@Inject
	private JdbcTemplate jdbcTemplate;

	// =============CREATION AREA===============

	@Override
	public Manager insert(Manager manager) {
		final String INSERT_SQL = "insert into  manager (first_name, patronymic, last_name, position) values(?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
				ps.setString(1, manager.getFirstName());
				ps.setString(2, manager.getPatronymic());
				ps.setString(3, manager.getLastName());
				ps.setString(4, manager.getPosition());
				return ps;
			}
		}, keyHolder);
		manager.setId(keyHolder.getKey().intValue());
		return manager;
	}

	// =============READING AREA===============

	@Override
	public Manager get(Integer id) {
		final String READ_BY_ID_SQL = "select * from manager where id = ? ";
		try {
			return jdbcTemplate.queryForObject(READ_BY_ID_SQL, new Object[] { id },
					new BeanPropertyRowMapper<Manager>(Manager.class));
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("Error: manager with id = " + id + " don't exist in storage)", e);
			// throw e;
			return null;
		}
	}

	@Override
	public List<Manager> getAll() {
		try {
			List<Manager> managers = jdbcTemplate.query("select * from manager",
					new BeanPropertyRowMapper<Manager>(Manager.class));
			return managers;
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("Error: all managers don't exist in storage", e);
			return null;
		}
	}

	// =============UPDATE AREA===============

	@Override
	public void update(Manager manager) {
		final String UPDATE_SQL = "update manager set first_name = ?, patronymic = ?, last_name = ?, position = ? where id = ?";
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(UPDATE_SQL, new String[] { "id" });
				ps.setString(1, manager.getFirstName());
				ps.setString(2, manager.getPatronymic());
				ps.setString(3, manager.getLastName());
				ps.setString(4, manager.getPosition());
				ps.setInt(5, manager.getId());
				return ps;
			}
		});
	}

	// =============DELETE AREA===============

	@Override
	public void delete(Integer id) {
		jdbcTemplate.update("delete from manager where id=" + id);
	}
}

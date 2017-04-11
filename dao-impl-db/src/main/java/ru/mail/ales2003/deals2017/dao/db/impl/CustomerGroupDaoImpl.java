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
import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.api.ICustomerGroupDao;
import ru.mail.ales2003.deals2017.datamodel.CustomerGroup;

@Repository
public class CustomerGroupDaoImpl implements ICustomerGroupDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerGroupDaoImpl.class);

	@Inject
	private JdbcTemplate jdbcTemplate;

	// =============CREATION AREA===============

	@Override
	public CustomerGroup insert(CustomerGroup entity) {
		final String INSERT_SQL = "insert into customer_group (name) values(?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
				ps.setString(1, entity.getName().name());
				return ps;
			}
		}, keyHolder);
		entity.setId(keyHolder.getKey().intValue());
		return entity;
	}

	// =============READING AREA===============

	@Override
	public CustomerGroup get(Integer id) {
		final String READ_BY_ID_SQL = "select * from customer_group where id = ? ";
		try {
			return jdbcTemplate.queryForObject(READ_BY_ID_SQL, new Object[] { id },
					new BeanPropertyRowMapper<CustomerGroup>(CustomerGroup.class));
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("Error: customerGroup with id = " + id + " don't exist in storage)", e);
			// throw e;
			return null;
		}
	}

	@Override
	public List<CustomerGroup> getAll() {
		try {
			List<CustomerGroup> customerGroups = jdbcTemplate.query("select * from customer_group",
					new BeanPropertyRowMapper<CustomerGroup>(CustomerGroup.class));
			return customerGroups;
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("Error: all customerGroups don't exist in storage", e);
			return null;
		}
	}

	// =============UPDATE AREA===============

	@Override
	public void update(CustomerGroup entity) {
		final String UPDATE_SQL = "update customer_group set name = ? where id = ?";
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(UPDATE_SQL, new String[] { "id" });
				ps.setString(1, entity.getName().name());
				ps.setInt(2, entity.getId());
				return ps;
			}
		});
	}

	// =============DELETE AREA===============

	@Override
	public void delete(Integer id) {
		jdbcTemplate.update("delete from customer_group where id=" + id);
	}

}

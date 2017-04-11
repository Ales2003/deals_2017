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

import ru.mail.ales2003.deals2017.dao.api.ICustomerDao;
import ru.mail.ales2003.deals2017.datamodel.Customer;

@Repository
public class CustomerDaoImpl implements ICustomerDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerDaoImpl.class);

	@Inject
	private JdbcTemplate jdbcTemplate;

	// =============CREATION AREA===============

	@Override
	public Customer insert(Customer entity) {
		final String INSERT_SQL = "insert into customer (first_name, patronymic, last_name, company_name,"
				+ " address, phone_number,customer_group_id, manager_id) values(?,?,?,?,?,?,?,?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
				ps.setString(1, entity.getFirstName());
				ps.setString(2, entity.getPatronymic());
				ps.setString(3, entity.getLastName());
				ps.setString(4, entity.getCompanyName());
				ps.setString(5, entity.getAddress());
				ps.setString(6, entity.getPhoneNumber());
				ps.setInt(7, entity.getCustomerGroupId());
				ps.setInt(8, entity.getManagerId());
				return ps;
			}
		}, keyHolder);

		entity.setId(keyHolder.getKey().intValue());

		return entity;
	}

	// =============READING AREA===============

	@Override
	public Customer get(Integer id) {
		final String READ_BY_ID_SQL = "select * from customer where id = ? ";

		try {
			return jdbcTemplate.queryForObject(READ_BY_ID_SQL, new Object[] { id },
					new BeanPropertyRowMapper<Customer>(Customer.class));
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("Error: customer with id = " + id + " don't exist in storage)", e);
			return null;
		}
	}

	@Override
	public List<Customer> getAll() {
		try {
			List<Customer> rs = jdbcTemplate.query("select * from customer ",
					new BeanPropertyRowMapper<Customer>(Customer.class));
			return rs;
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("Error: all customers don't exist in storage", e);
			return null;
		}
	}

	// To think whether it is necessary

	/*
	 * public List<Customer> getWithManagerAndGroup() { try { List<Customer> rs
	 * = jdbcTemplate.query("select * from customer,  ", new
	 * BeanPropertyRowMapper<Customer>(Customer.class)); return rs; } catch
	 * (EmptyResultDataAccessException e) {
	 * LOGGER.error("Error: all customers don't exist in storage", e); return
	 * null; } }
	 */
	// =============UPDATE AREA===============

	@Override
	public void update(Customer entity) {
		final String UPDATE_SQL = "update customer set first_name = ?, patronymic = ?, last_name = ?, company_name = ?,"
				+ " address = ?, phone_number = ?,customer_group_id = ?, manager_id = ? where id = ?";

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(UPDATE_SQL, new String[] { "id" });
				ps.setString(1, entity.getFirstName());
				ps.setString(2, entity.getPatronymic());
				ps.setString(3, entity.getLastName());
				ps.setString(4, entity.getCompanyName());
				ps.setString(5, entity.getAddress());
				ps.setString(6, entity.getPhoneNumber());
				ps.setInt(7, entity.getCustomerGroupId());
				ps.setInt(8, entity.getManagerId());
				ps.setInt(9, entity.getId());
				return ps;
			}
		});

	}

	// =============DELETE AREA===============

	@Override
	public void delete(Integer id) {
		jdbcTemplate.update("delete from customer where id=" + id);
	}
}

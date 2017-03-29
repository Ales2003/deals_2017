package ru.mail.ales2003.deals2017.dao.impl.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.impl.db.ICustomerDao;
import ru.mail.ales2003.deals2017.datamodel.Customer;

@Repository
public class CustomerDaoImpl implements ICustomerDao {

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
		try {
			return jdbcTemplate.queryForObject("select * from customer where id = ? ", new Object[] { id },
					new BeanPropertyRowMapper<Customer>(Customer.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Customer> getAll() {
		List<Customer> rs = jdbcTemplate.query("select * from customer ",
				new BeanPropertyRowMapper<Customer>(Customer.class));
		return rs;
	}

	// =============UPDATE AREA===============

	@Override
	public void update(Customer entity) {
		final String INSERT_SQL = "insert into customer " + "(first_name, patronymic, last_name, company_name,"
				+ " address, phone_number,customer_group_id, manager_id)" + " values(?,?,?,?,?,?,?,?)";

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
		});

	}

	// =============DELETE AREA===============

	@Override
	public void delete(Integer id) {
		jdbcTemplate.update("delete from customer where id=" + id);

	}

}

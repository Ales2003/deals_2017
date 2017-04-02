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

import ru.mail.ales2003.deals2017.dao.impl.db.ICustomerGroupDao;
import ru.mail.ales2003.deals2017.datamodel.CustomerGroup;
import ru.mail.ales2003.deals2017.datamodel.CustomerType;

@Repository
public class CustomerGroupDaoImpl implements ICustomerGroupDao {

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
	public List<CustomerGroup> getAll() {
		try {

			List<CustomerGroup> customerGroup = jdbcTemplate.query("select * from customer_group",
					new BeanPropertyRowMapper<CustomerGroup>(CustomerGroup.class));
			return customerGroup;
		} catch (EmptyResultDataAccessException e) {
			
			return null;
		}
	}

	@Override
	public CustomerGroup get(Integer id) {
		try {
			return jdbcTemplate.queryForObject("select * from customer_group where id = ? ", new Object[] { id },
					new BeanPropertyRowMapper<CustomerGroup>(CustomerGroup.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	// =============UPDATE AREA===============

	@Override
	public void update(CustomerGroup entity) {
		final String INSERT_SQL = "update customer_group set name = ? where id = ?";

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
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

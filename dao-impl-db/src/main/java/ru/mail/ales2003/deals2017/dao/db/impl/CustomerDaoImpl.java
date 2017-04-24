package ru.mail.ales2003.deals2017.dao.db.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.api.ICustomerDao;
import ru.mail.ales2003.deals2017.datamodel.Customer;

@Repository
public class CustomerDaoImpl extends AbstractDaoImplDb<Customer, Integer> implements ICustomerDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerDaoImpl.class);

	// =============CREATION AREA===============

	@Override
	protected String getInsertQuery() {
		final String INSERT_SQL = "insert into customer (first_name, patronymic, last_name, company_name,"
				+ " address, phone_number,customer_group_id, manager_id) values(?,?,?,?,?,?,?,?)";
		return INSERT_SQL;
	}

	@Override
	protected Customer setEntityId(Customer entity, Integer id) {
		entity.setId(id);
		return entity;
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement ps, Customer entity) {
		try {
			ps.setString(1, entity.getFirstName());
			ps.setString(2, entity.getPatronymic());
			ps.setString(3, entity.getLastName());
			ps.setString(4, entity.getCompanyName());
			ps.setString(5, entity.getAddress());
			ps.setString(6, entity.getPhoneNumber());
			ps.setInt(7, entity.getCustomerGroupId());
			ps.setInt(8, entity.getManagerId());
		} catch (SQLException e) {
			LOGGER.error("Error: prepareStatementForInsert is broken: {}", e);
			// !!!TO REFACTOR!!!
			throw new RuntimeException(e);
		}
	}

	// =============READING AREA===============

	@Override
	protected String getSelectQuery() {
		final String READ_BY_ID_SQL = "select * from customer";
		return READ_BY_ID_SQL;
	}

	@Override
	protected Class<Customer> getMappedClass() {
		return Customer.class;
	}

	// =============UPDATE AREA===============

	@Override
	protected String getUpdateQuery() {
		final String UPDATE_SQL = "update customer set first_name = ?, patronymic = ?, last_name = ?, company_name = ?,"
				+ " address = ?, phone_number = ?,customer_group_id = ?, manager_id = ? where id = ?";
		return UPDATE_SQL;
	}

	@Override
	protected void prepareStatementForUpdate(PreparedStatement ps, Customer entity) {
		try {
			ps.setString(1, entity.getFirstName());
			ps.setString(2, entity.getPatronymic());
			ps.setString(3, entity.getLastName());
			ps.setString(4, entity.getCompanyName());
			ps.setString(5, entity.getAddress());
			ps.setString(6, entity.getPhoneNumber());
			ps.setInt(7, entity.getCustomerGroupId());
			ps.setInt(8, entity.getManagerId());
			ps.setInt(9, entity.getId());
		} catch (SQLException e) {
			LOGGER.error("Error: prepareStatementForInsert is broken", e);
			// !!!TO REFACTOR!!!
			throw new RuntimeException(e);
		}
	}

	// =============DELETE AREA===============

	@Override
	protected String getDeleteQuery() {
		final String DELETE_BY_ID_SQL = "delete from customer";
		return DELETE_BY_ID_SQL;
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

}

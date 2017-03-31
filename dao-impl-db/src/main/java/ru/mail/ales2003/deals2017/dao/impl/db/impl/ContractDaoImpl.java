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

import ru.mail.ales2003.deals2017.dao.impl.db.IContractDao;
import ru.mail.ales2003.deals2017.datamodel.Contract;

@Repository
public class ContractDaoImpl implements IContractDao {

	@Inject
	private JdbcTemplate jdbcTemplate;

	// =============CREATION AREA===============

	@Override
	public Contract insert(Contract entity) {
		final String INSERT_SQL = "insert into contract (сreated, contract_status, pay_form, pay_status, customer_id) values(?, ?, ?, ?, ?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
				ps.setTimestamp(1, entity.getСreated());
				ps.setString(2, entity.getContractStatus().name());
				ps.setString(3, entity.getPayForm().name());
				ps.setString(4, entity.getPayStatus().name());
				ps.setInt(5, entity.getCustomerId());
				
				return ps;
			}
		}, keyHolder);

		entity.setId(keyHolder.getKey().intValue());

		return entity;
	}

	// =============READING AREA===============

	@Override
	public Contract get(Integer id) {
		try {
			return jdbcTemplate.queryForObject("select * from contract where id = ? ", new Object[] { id },
					new BeanPropertyRowMapper<Contract>(Contract.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Contract> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	// =============UPDATE AREA===============

	@Override
	public void update(Contract entity) {
		// TODO Auto-generated method stub

	}

	// =============DELETE AREA===============

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

}

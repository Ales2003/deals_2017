package ru.mail.ales2003.deals2017.dao.impl.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
import ru.mail.ales2003.deals2017.datamodel.Customer;

@Repository
public class ContractDaoImpl implements IContractDao {

	@Inject
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Contract insert(Contract entity) {
		final String INSERT_SQL = "insert into contract (сreated) values(?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
				ps.setTimestamp(1, entity.getСreated());
				return ps;
			}
		}, keyHolder);

		entity.setId(keyHolder.getKey().intValue());

		return entity;
	}

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
	public void update(Contract contract) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

}

package ru.mail.ales2003.deals2017.dao.impl.db.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.impl.db.ICustomerGroupDao;
import ru.mail.ales2003.deals2017.datamodel.CustomerGroup;

@Repository
public class CustomerGroupDaoImpl implements ICustomerGroupDao {

	@Inject
	private JdbcTemplate jdbcTemplate;

	// =============CREATION AREA===============

	@Override
	public CustomerGroup insert(CustomerGroup entity) {
		// TODO Auto-generated method stub
		return null;
	}

	// =============READING AREA===============
	
	@Override
	public List<CustomerGroup> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerGroup get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	// =============UPDATE AREA===============

	@Override
	public void update(CustomerGroup entity) {
		// To do something

	}

	// =============DELETE AREA===============

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

}

package ru.mail.ales2003.deals2017.dao.impl.db.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.impl.db.IManagerDao;
import ru.mail.ales2003.deals2017.datamodel.Manager;

@Repository
public class ManagerDaoImpl implements IManagerDao {

	@Inject
	private JdbcTemplate jdbcTemplate;

	// =============CREATION AREA===============

	@Override
	public Manager insert(Manager manager) {
		// TODO Auto-generated method stub
		return null;
	}

	// =============READING AREA===============

	@Override
	public Manager get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Manager> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	// =============UPDATE AREA===============

	@Override
	public void update(Manager manager) {
		// TODO Auto-generated method stub

	}

	// =============DELETE AREA===============

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

}

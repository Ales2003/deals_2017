package ru.mail.ales2003.deals2017.dao.db.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.api.IContract2ItemVariantDao;
import ru.mail.ales2003.deals2017.datamodel.Contract2ItemVariant;

@Repository
public class Contract2ItemVariantDaoImpl implements IContract2ItemVariantDao {

	@Inject
	private JdbcTemplate jdbcTemplate;

	// =============CREATION AREA===============

	@Override
	public Contract2ItemVariant insert(Contract2ItemVariant entity) {
		// TODO Auto-generated method stub
		return null;
	}

	// =============READING AREA===============

	@Override
	public Contract2ItemVariant get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contract2ItemVariant> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	// =============UPDATE AREA===============

	@Override
	public void update(Contract2ItemVariant entity) {
		// TODO Auto-generated method stub

	}

	// =============DELETE AREA===============

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

}

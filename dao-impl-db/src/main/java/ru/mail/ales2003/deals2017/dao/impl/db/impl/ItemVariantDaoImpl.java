package ru.mail.ales2003.deals2017.dao.impl.db.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.impl.db.IItemVariantDao;
import ru.mail.ales2003.deals2017.datamodel.ItemVariant;

@Repository
public class ItemVariantDaoImpl implements IItemVariantDao {

	@Inject
	private JdbcTemplate jdbcTemplate;

	// =============CREATION AREA===============

	@Override
	public ItemVariant insert(ItemVariant entity) {
		// TODO Auto-generated method stub
		return null;
	}

	// =============READING AREA===============

	@Override
	public ItemVariant get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ItemVariant> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	// =============UPDATE AREA===============

	@Override
	public void update(ItemVariant entity) {
		// TODO Auto-generated method stub

	}

	// =============DELETE AREA===============

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

}

package ru.mail.ales2003.deals2017.dao.impl.db.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.impl.db.IItemDao;
import ru.mail.ales2003.deals2017.datamodel.Item;

@Repository
public class ItemDaoImpl implements IItemDao {

	@Inject
	private JdbcTemplate jdbcTemplate;

	// =============CREATION AREA===============

	@Override
	public Item insert(Item item) {
		return null;
	}

	// =============READING AREA===============

	@Override
	public Item get(Integer id) {
		try {
			return jdbcTemplate.queryForObject("select * from item where id = ? ", new Object[] { id },
					new BeanPropertyRowMapper<Item>(Item.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Item> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	// =============UPDATE AREA===============

	@Override
	public void update(Item item) {
		// TODO Auto-generated method stub

	}

	// =============DELETE AREA===============

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

}

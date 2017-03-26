package ru.mail.ales2003.deals2017.dao.impl.db.impl;

import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.impl.db.IItemDao;
import ru.mail.ales2003.deals2017.datamodel.Item;

@Repository
public class ItemDaoImpl implements IItemDao {

	@Override
	public Integer insert(Item item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item get(Integer id) {
		 // TODO go to DB
        Item item = new Item();
        item.setId(id);
        return item;
	}

	@Override
	public void update(Item item) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

}

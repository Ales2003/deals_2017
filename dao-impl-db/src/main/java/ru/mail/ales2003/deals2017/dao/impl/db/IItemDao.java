package ru.mail.ales2003.deals2017.dao.impl.db;

import java.util.List;

import ru.mail.ales2003.deals2017.datamodel.Item;

public interface IItemDao {

	Item insert(Item item);

	Item get(Integer id);

	List<Item> getAll();

	void update(Item item);

	void delete(Integer id);

}

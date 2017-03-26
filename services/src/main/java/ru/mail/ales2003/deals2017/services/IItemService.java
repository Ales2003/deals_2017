package ru.mail.ales2003.deals2017.services;

import ru.mail.ales2003.deals2017.datamodel.Item;

public interface IItemService {

	Integer insert(Item item);

	Item get(Integer id);

	void update(Item item);

	void delete(Integer id);

}

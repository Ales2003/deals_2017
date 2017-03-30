package ru.mail.ales2003.deals2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ru.mail.ales2003.deals2017.datamodel.Item;

public interface IItemService {

	Item get(Integer id);

	List<Item> getAll();

	@Transactional
	void save(Item item);

	@Transactional
	void saveMultiple(Item... item);

	@Transactional
	void delete(Integer id);

}

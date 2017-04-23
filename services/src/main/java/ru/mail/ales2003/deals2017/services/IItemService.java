package ru.mail.ales2003.deals2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ru.mail.ales2003.deals2017.datamodel.Item;

public interface IItemService {

	/**
	 * @param id
	 * @return item
	 */
	Item get(Integer id);

	/**
	 * @return List&ltharacterType&gt items
	 */
	List<Item> getAll();

	/**
	 * @param item
	 */
	@Transactional
	void save(Item item);

	/**
	 * @param itemArray
	 */
	@Transactional
	void saveMultiple(Item... itemArray);

	/**
	 * @param id
	 */
	@Transactional
	void delete(Integer id);

}

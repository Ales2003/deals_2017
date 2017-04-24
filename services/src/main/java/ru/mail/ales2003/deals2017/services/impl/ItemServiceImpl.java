package ru.mail.ales2003.deals2017.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ru.mail.ales2003.deals2017.dao.api.IItemDao;
import ru.mail.ales2003.deals2017.datamodel.Item;
import ru.mail.ales2003.deals2017.services.IItemService;

@Service
public class ItemServiceImpl implements IItemService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemServiceImpl.class);

	private String className = Item.class.getSimpleName();

	// dependency injection (внедряем зависимость в этот класс - ссылку на бин
	// имплементатора IItemDao)
	@Inject
	public IItemDao itemDao;

	// вводим properties c помощью @Value
	@Value("${key1}")
	public String key1;

	@Value("${key2}")
	public Integer key2;

	@Override
	public Item get(Integer id) {
		if (itemDao.get(id) == null) {
			String errMsg = String.format("[%s] entity with id = [%s] don't exist in storage", className, id);
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg);
		} else {
			Item item = itemDao.get(id);
			LOGGER.info("Read one {} entity: {}", className, item.toString());
			return item;
		}
	}

	@Override
	public List<Item> getAll() {
		LOGGER.info("{} entities storage returns {} entities.", className, itemDao.getAll().size());
		LOGGER.info("Read all {} entities:", className);
		for (Item i : itemDao.getAll()) {
			LOGGER.info("{} entity = {}", className, i.toString());
		}
		return itemDao.getAll();
	}

	@Override
	public void save(Item item) {
		if (item == null) {
			LOGGER.error("Error: as the entity was sent a null reference");
			return;
		} else if (item.getId() == null) {
			itemDao.insert(item);
			LOGGER.info("Inserted new {} entity: {}", className, item.toString());
		} else {
			itemDao.update(item);
			LOGGER.info("Updated one {} entity: {}", className, item.toString());
		}
	}

	@Override
	public void saveMultiple(Item... itemArray) {
		for (Item item : itemArray) {
			LOGGER.debug("Inserted new {} entity from array: {}", className, item.toString());
			save(item);
		}
		LOGGER.info("{} entities from array were inserted", className);
	}

	@Override
	public void delete(Integer id) {
		if (id == null) {
			LOGGER.error("Error: as the id was sent a null reference");
			return;
		} else {
			itemDao.delete(id);
			LOGGER.info("Deleted {} by id: {}", className, id);
		}
	}
}

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
			LOGGER.error("Error: item with id = " + id + " don't exist in storage)");
			return null;
		} else {
			Item item = itemDao.get(id);
			LOGGER.info("Read one item: id={}, name={}, description={}, basicPrice={}", item.getId(), item.getName(),
					item.getDescription(), item.getBasicPrice());
			return item;
		}
	}

	@Override
	public List<Item> getAll() {
		if (itemDao.getAll() == null) {
			LOGGER.error("Error: all items don't exist in storage");
			return null;
		} else {
			LOGGER.info("Read all items");
			return itemDao.getAll();
		}
	}

	@Override
	public void save(Item item) {
		if (item == null) {
			LOGGER.error("Error: as the item was sent a null reference");
			return;
		} else if (item.getId() == null) {
			itemDao.insert(item);
			LOGGER.info("Inserted new item: id={}, name={}, description={}, basicPrice={}", item.getId(),
					item.getName(), item.getDescription(), item.getBasicPrice());
		} else {
			itemDao.update(item);
			LOGGER.info("Updated item: id={}, name={}, description={}, basicPrice={}", item.getId(), item.getName(),
					item.getDescription(), item.getBasicPrice());
		}
	}

	@Override
	public void saveMultiple(Item... itemArray) {
		for (Item item : itemArray) {
			LOGGER.debug("Inserted new item from array: " + item);
			save(item);
		}
		LOGGER.info("Inserted items from array");
	}

	@Override
	public void delete(Integer id) {
		if (id == null) {
			LOGGER.error("Error: as the id was sent a null reference");
			return;
		} else {
			itemDao.delete(id);
			LOGGER.info("Deleted item by id: " + id);
		}
	}
}

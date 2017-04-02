package ru.mail.ales2003.deals2017.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import ru.mail.ales2003.deals2017.dao.impl.db.IItemDao;
import ru.mail.ales2003.deals2017.datamodel.CustomerType;
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
		try {
			Item item = itemDao.get(id);
			LOGGER.info("Read one Item: id={}, name={}, description={}, basicPrice={}", item.getId(), item.getName(), item.getDescription(), item.getBasicPrice());
			return item;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Item> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Item item) {
		if (item.getId() == null) {
			itemDao.insert(item);
			LOGGER.info("Inserted new Item: id={}, name={}, description={}, basicPrice={}", item.getId(), item.getName(), item.getDescription(), item.getBasicPrice());
		} else {
			itemDao.update(item);
			LOGGER.info("Updated Item: id={}, name={}, description={}, basicPrice={}", item.getId(), item.getName(), item.getDescription(), item.getBasicPrice());
		}

	}

	@Override
	public void saveMultiple(Item... item) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

}

package ru.mail.ales2003.deals2017.services.impl;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ru.mail.ales2003.deals2017.dao.impl.db.IItemDao;
import ru.mail.ales2003.deals2017.datamodel.Item;
import ru.mail.ales2003.deals2017.services.IItemService;

@Service
public class ItemServiceImpl implements IItemService {

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
	public Integer insert(Item item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item get(Integer id) {

		return itemDao.get(id);
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

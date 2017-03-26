package ru.mail.ales2003.deals2017.dao.impl.db;

import ru.mail.ales2003.deals2017.datamodel.Contract2Item;

public interface IContract2ItemDao {
	
	Integer insert(Contract2Item contract2Item);
	
	Contract2Item get(Integer id);

	void update(Contract2Item contract2Item);
	
	void delete (Integer id);
	
	
}

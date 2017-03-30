package ru.mail.ales2003.deals2017.dao.impl.db;

import ru.mail.ales2003.deals2017.datamodel.Contract2ItemVariant;

public interface IContract2ItemVariantDao {
	
	Contract2ItemVariant insert(Contract2ItemVariant entity);
	
	Contract2ItemVariant get(Integer id);

	void update(Contract2ItemVariant entity);
	
	void delete (Integer id);
	
	
}

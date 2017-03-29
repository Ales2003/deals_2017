package ru.mail.ales2003.deals2017.dao.impl.db;

import ru.mail.ales2003.deals2017.datamodel.Contract2ItemVariant;

public interface IContract2ItemVariantDao {
	
	Integer insert(Contract2ItemVariant contract2ItemVariant);
	
	Contract2ItemVariant get(Integer id);

	void update(Contract2ItemVariant contract2ItemVariant);
	
	void delete (Integer id);
	
	
}

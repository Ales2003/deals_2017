package ru.mail.ales2003.deals2017.dao.impl.db;

import ru.mail.ales2003.deals2017.datamodel.Item;
import ru.mail.ales2003.deals2017.datamodel.ItemVariant;

public interface IItemVariantDao {

	Integer insert(ItemVariant itemVariant);

	Item get(Integer id);

	void update(ItemVariant itemVariant);

	void delete(Integer id);
}

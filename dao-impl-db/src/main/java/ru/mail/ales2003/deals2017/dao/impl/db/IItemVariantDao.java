package ru.mail.ales2003.deals2017.dao.impl.db;

import java.util.List;

import ru.mail.ales2003.deals2017.datamodel.ItemVariant;

public interface IItemVariantDao {

	ItemVariant insert(ItemVariant entity);

	ItemVariant get(Integer id);

	List<ItemVariant> getAll();

	void update(ItemVariant entity);

	void delete(Integer id);
}

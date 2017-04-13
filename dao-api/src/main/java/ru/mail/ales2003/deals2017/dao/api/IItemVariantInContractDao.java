package ru.mail.ales2003.deals2017.dao.api;

import java.util.List;

import ru.mail.ales2003.deals2017.datamodel.ItemVariantInContract;

public interface IItemVariantInContractDao {

	ItemVariantInContract insert(ItemVariantInContract entity);

	ItemVariantInContract get(Integer id);

	List<ItemVariantInContract> getAll();

	void update(ItemVariantInContract entity);

	void delete(Integer id);

}

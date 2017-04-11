package ru.mail.ales2003.deals2017.dao.api;

import java.util.List;

import ru.mail.ales2003.deals2017.datamodel.Contract2ItemVariant;

public interface IContract2ItemVariantDao {

	Contract2ItemVariant insert(Contract2ItemVariant entity);

	Contract2ItemVariant get(Integer id);

	List<Contract2ItemVariant> getAll();

	void update(Contract2ItemVariant entity);

	void delete(Integer id);

}

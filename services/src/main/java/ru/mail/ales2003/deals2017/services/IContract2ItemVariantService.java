package ru.mail.ales2003.deals2017.services;

import ru.mail.ales2003.deals2017.datamodel.Contract2ItemVariant;

public interface IContract2ItemVariantService {

	Integer insert(Contract2ItemVariant contract2Item);

	Contract2ItemVariant get(Integer id);

	void update(Contract2ItemVariant contract2Item);

	void delete(Integer id);

}

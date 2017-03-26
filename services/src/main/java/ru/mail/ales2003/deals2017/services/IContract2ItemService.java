package ru.mail.ales2003.deals2017.services;

import ru.mail.ales2003.deals2017.datamodel.Contract2Item;

public interface IContract2ItemService {

	Integer insert(Contract2Item contract2Item);

	Contract2Item get(Integer id);

	void update(Contract2Item contract2Item);

	void delete(Integer id);

}

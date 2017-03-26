package ru.mail.ales2003.deals2017.dao.impl.db;

import ru.mail.ales2003.deals2017.datamodel.Contract;

public interface IContractDao {

	Integer insert(Contract contract);

	Contract get(Integer id);

	void update(Contract contract);

	void delete(Integer id);

}

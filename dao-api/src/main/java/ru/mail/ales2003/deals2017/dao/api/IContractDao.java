package ru.mail.ales2003.deals2017.dao.api;

import java.util.List;

import ru.mail.ales2003.deals2017.datamodel.Contract;

public interface IContractDao {

	Contract insert(Contract entity);

	Contract get(Integer id);

	void update(Contract entity);

	void delete(Integer id);

	List<Contract> getAll();

	// ???????++++++++++++++++++++=====================
	// public List<ItemInContract> findAll();

}

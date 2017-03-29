package ru.mail.ales2003.deals2017.services;

import ru.mail.ales2003.deals2017.datamodel.Contract;

public interface IContractService {

	void save(Contract contract);

	Contract get(Integer id);

	void update(Contract contract);

	void delete(Integer id);
}

package ru.mail.ales2003.deals2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ru.mail.ales2003.deals2017.datamodel.Contract;
import ru.mail.ales2003.deals2017.datamodel.Contract2ItemVariant;

public interface IContractService {

	Contract get(Integer id);

	List<Contract> getAll();

	void save(Contract contract);

	/*// saves contract + list of entities in given contract
	//TO DO 
	//Contract2ItemVariant get (Integer id)
	//List<Contract2ItemVariant> getAll(Contract contract), 
	void save(Contract contract, Contract2ItemVariant... entity);*/

	@Transactional
	void saveMultiple(Contract... contract);

	void delete(Integer id);
}

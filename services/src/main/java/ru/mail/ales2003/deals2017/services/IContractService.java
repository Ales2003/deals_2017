package ru.mail.ales2003.deals2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ru.mail.ales2003.deals2017.dao.api.custom.classes.ItemInContract;
import ru.mail.ales2003.deals2017.datamodel.Contract;

public interface IContractService {

	Contract get(Integer id);

	List<Contract> getAll();

	@Transactional
	void save(Contract contract);

	@Transactional
	void saveMultiple(Contract... contract);

	@Transactional
	void delete(Integer id);
	
	/*//!!!!!!!!!!!!!!!!++++++++++++++++++
	public List<ItemInContract> ic();*/

	/*
	 * // saves contract + list of entities in given contract //TO DO
	 * //Contract2ItemVariant get (Integer id) //List<Contract2ItemVariant>
	 * getAll(Contract contract), void save(Contract contract,
	 * Contract2ItemVariant... entity);
	 */

}

package ru.mail.ales2003.deals2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ru.mail.ales2003.deals2017.dao.api.custom.entities.Detail;
import ru.mail.ales2003.deals2017.datamodel.Contract;
import ru.mail.ales2003.deals2017.datamodel.ItemVariantInContract;
import ru.mail.ales2003.deals2017.datamodel.ItemVariant;

public interface IContractService {

	Contract getContract(Integer contractId);

	List<Contract> getAllContract();

	@Transactional
	void saveContract(Contract contract);

	@Transactional
	void saveContractMultiple(Contract... contract);

	@Transactional
	void deleteContract(Integer contractId);

	// Goods management and handling in a contract

	ItemVariantInContract getItemInContract(Integer itemInContractId);

	List<ItemVariantInContract> getAllItemInContract();

	@Transactional
	void saveItemInContract(ItemVariantInContract itemInContract);

	@Transactional
	void saveItemInContractMultiple(ItemVariantInContract... itemInContractArray);

	@Transactional
	void deleteItemInContract(Integer itemInContractid);



}

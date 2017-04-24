package ru.mail.ales2003.deals2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ru.mail.ales2003.deals2017.datamodel.Contract;
import ru.mail.ales2003.deals2017.datamodel.ItemVariantInContract;

public interface IContractService {

	// ==================Contracts management and handling
	/**
	 * @param contractId
	 * @return Contract contract
	 */
	Contract getContract(Integer contractId);

	/**
	 * @return List&ltContract&gt contract
	 */
	List<Contract> getAllContract();

	/**
	 * @param contract
	 */
	@Transactional
	void saveContract(Contract contract);

	/**
	 * @param contractArray
	 */
	@Transactional
	void saveContractMultiple(Contract... contractArray);

	/**
	 * @param contractId
	 */
	@Transactional
	void deleteContract(Integer contractId);

	// ==================Item variants management and handling in a contract

	/**
	 * @param itemId
	 * @return ItemVariantInContract item
	 */
	ItemVariantInContract getItemVariantInContract(Integer itemId);

	/**
	 * @return List&ltContract&gt items
	 */
	List<ItemVariantInContract> getAllItemVariantsInContract();

	/**
	 * @param item
	 */
	@Transactional
	void saveItemVariantInContract(ItemVariantInContract item);

	/**
	 * @param itemsArray
	 */
	@Transactional
	void saveItemVariantInContractMultiple(ItemVariantInContract... itemsArray);

	/**
	 * @param itemId
	 */
	@Transactional
	void deleteItemVariantInContract(Integer itemId);

}

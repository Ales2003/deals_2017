package ru.mail.ales2003.deals2017.dao.api.customdao;

import java.util.List;

import ru.mail.ales2003.deals2017.dao.api.customentities.ContractDetail;

/**
 * Methods provide a detail information about all item variants in given
 * contract: id of given contract, item variants id, items name, item variants
 * price, qty of every item variant in contract. These methods are designed to
 * display understandable information to managers and customers.
 * 
 * @author Aliaksandr Vishneuski ales_2003@mail.ru
 *
 */
public interface IContractDetailDao {

	/**
	 * @param contractId
	 * @return a list List&ltContractDetail&gt of the contract details
	 */
	List<ContractDetail> getDetails(Integer contractId);
}

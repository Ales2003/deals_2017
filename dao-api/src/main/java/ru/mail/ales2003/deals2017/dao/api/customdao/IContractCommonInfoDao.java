package ru.mail.ales2003.deals2017.dao.api.customdao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ru.mail.ales2003.deals2017.dao.api.customentities.ContractCommonInfo;
import ru.mail.ales2003.deals2017.dao.api.filters.IContractFilter;

/**
 * Methods provide a common information about contract: id of contract, time of
 * creating, contract status, pay form, pay status, total amount, customer
 * information (id, group, name), manager information (name). These methods are
 * designed to display understandable information to customers and managers.
 * 
 * @author Aliaksandr Vishneuski ales_2003@mail.ru
 *
 */
public interface IContractCommonInfoDao {

	/**
	 * @param contractId
	 * @return a common info about one contract.
	 */
	ContractCommonInfo getCommonInfo(Integer contractId);

	/**
	 * @param filter
	 *            - the IContractFilter.
	 * @return
	 */
	List<ContractCommonInfo> getCommonInfoFiltered(IContractFilter filter);

	/**
	 * @return list List&ltContractCommonInfo&gt of the common info about all
	 *         contracts.
	 */
	List<ContractCommonInfo> getCommonInfoForAll();

	/**
	 * @return Map&ltString, ArrayList&ltContractCommonInfo&gt&gt
	 */
	Map<String, ArrayList<ContractCommonInfo>> getCACHEToPersistendSave();

	/**
	 * @param cache
	 * @return
	 */
	void SetPersistentSavedCACHE(Map<String, ArrayList<ContractCommonInfo>> cache);

	/**
	 * 
	 */
	void clearCACHE();
}

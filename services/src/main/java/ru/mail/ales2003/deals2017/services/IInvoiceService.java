package ru.mail.ales2003.deals2017.services;

import java.util.List;

import ru.mail.ales2003.deals2017.dao.api.customentities.ContractCommonInfo;
import ru.mail.ales2003.deals2017.dao.api.customentities.ContractDetail;
import ru.mail.ales2003.deals2017.dao.api.customentities.Invoice;
import ru.mail.ales2003.deals2017.dao.api.filters.IContractFilter;

/**
 * Methods provide both common and detailed information about contract. The
 * first part of the methods is based on the use IContractCommonInfoDao. The
 * first part of the methods is based on the use IContractDetailDao. The third
 * part of the methods is based on both marked Daos. These methods are designed
 * to display understandable information to customers and managers.
 * 
 * @author admin
 */

public interface IInvoiceService {

	/**
	 * @param contractId
	 * @return common info for one contract
	 */
	ContractCommonInfo getCommonInfo(Integer contractId);

	/**
	 * @return common info for all contracts
	 */
	List<ContractCommonInfo> getCommonInfoForAll();

	/**
	 * @param filter
	 * @return common infos list List&ltContractCommonInfo&gt for some contracts
	 *         after filtering by basic parameters: 
	 *         //TO WRITE
	 *         items name, items
	 *         description, item variants price also with sorting and
	 *         limit/offset.
	 */
	List<ContractCommonInfo> getCommonInfoFiltered(IContractFilter filter);

	/**
	 * @param contractId
	 * @return details list List&ltContractDetail&gt for one item variant
	 */
	List<ContractDetail> getDetails(Integer contractId);

	/**
	 * @param contractId
	 * @return invoice as a consolidation of common information and
	 *         details list for one contract
	 */
	Invoice getInvoice(Integer contractId);

}

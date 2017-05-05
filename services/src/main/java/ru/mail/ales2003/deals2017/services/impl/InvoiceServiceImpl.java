package ru.mail.ales2003.deals2017.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ru.mail.ales2003.deals2017.dao.api.customdao.IContractCommonInfoDao;
import ru.mail.ales2003.deals2017.dao.api.customdao.IContractDetailDao;
import ru.mail.ales2003.deals2017.dao.api.customentities.ContractCommonInfo;
import ru.mail.ales2003.deals2017.dao.api.customentities.ContractDetail;
import ru.mail.ales2003.deals2017.dao.api.customentities.Invoice;
import ru.mail.ales2003.deals2017.dao.api.filters.IContractFilter;
import ru.mail.ales2003.deals2017.datamodel.Contract;
import ru.mail.ales2003.deals2017.services.IInvoiceService;

@Service
public class InvoiceServiceImpl implements IInvoiceService {

	private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceServiceImpl.class);

	@Inject
	public IContractCommonInfoDao commonInfoDao;

	@Inject
	public IContractDetailDao detailsDao;

	private String contractClassName = Contract.class.getSimpleName();

	private String commonInfoClassName = ContractCommonInfo.class.getSimpleName();

	@Override
	public ContractCommonInfo getCommonInfo(Integer contractId) {
		if (commonInfoDao.getCommonInfo(contractId) == null) {
			String errMsg = String.format("[%s] entity with id = [%s] don't exist in storage", contractClassName,
					contractId);
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg);
		} else {
			ContractCommonInfo commonInfo = commonInfoDao.getCommonInfo(contractId);
			LOGGER.info("Read common information about {} with id = {} with basic info: {}", contractClassName,
					contractId, commonInfo.toString());
			return commonInfo;
		}
	}

	@Override
	public List<ContractCommonInfo> getCommonInfoForAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ContractCommonInfo> getCommonInfoFiltered(IContractFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ContractDetail> getDetails(Integer contractId) {
		if (detailsDao.getDetails(contractId) == null) {
			String errMsg = String.format("[%s] entity with id = [%s] don't exist in storage", contractClassName,
					contractId);
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg);
		} else {
			List<ContractDetail> details = detailsDao.getDetails(contractId);
			LOGGER.info("Read details of {} with id = {} : ", contractClassName, contractId);
			for (ContractDetail cD : details) {
				LOGGER.info("itemVariantDetail = {}", cD.toString());
			}
			return details;
		}
	}

	@Override
	public Invoice getInvoice(Integer contractId) {
		// TODO Auto-generated method stub
		return null;
	}

}

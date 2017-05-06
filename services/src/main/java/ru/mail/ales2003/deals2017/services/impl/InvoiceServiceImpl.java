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
		List<ContractCommonInfo> entities = commonInfoDao.getCommonInfoForAll();
		if (entities == null) {
			String errMsg = String.format("[%s] entities storage is epty", contractClassName);
			LOGGER.error("Error: {}", errMsg);
			// return null;
		}
		LOGGER.info("{} entities storage returns {} entities: ", contractClassName, entities.size());
		LOGGER.info("Read all {} entities with common info: ", contractClassName);
		for (ContractCommonInfo cI : entities) {
			LOGGER.info("{} entity = {}", commonInfoClassName, cI.toString());
		}
		return entities;
	}

	@Override
	public List<ContractCommonInfo> getCommonInfoFiltered(IContractFilter filter) {
		List<ContractCommonInfo> entities = commonInfoDao.getCommonInfoFiltered(filter);
		if (entities == null) {
			String errMsg = String.format("[%s] entities storage is epty", contractClassName);
			LOGGER.error("Error: {}", errMsg);
			// return null;
		}
		LOGGER.info("{} entities storage returns {} entities: ", contractClassName, entities.size());
		LOGGER.info("Read all {} entities with common info with filter: {}", contractClassName, filter.toString());
		for (ContractCommonInfo cI : entities) {
			LOGGER.info("{} entity = {}", commonInfoClassName, cI.toString());
		}
		return entities;
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
		if (commonInfoDao.getCommonInfo(contractId) == null || detailsDao.getDetails(contractId) == null) {
			String errMsg = String.format("[%s] entity with id = [%s] don't exist in storage", contractClassName,
					contractId);
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg);
		} else {
			LOGGER.info("Read specification of {} with id = {} with basic info and details: ", contractClassName,
					contractId);
			Invoice invoice = new Invoice();
			invoice.setCommonInfo(commonInfoDao.getCommonInfo(contractId));
			invoice.setDetails(detailsDao.getDetails(contractId));
			LOGGER.info("Specification is created: ");

			LOGGER.info("Common info of {} = {} ", contractClassName, invoice.getCommonInfo().toString());
			for (ContractDetail i : invoice.getDetails()) {
				LOGGER.info("Detail of {} = {}", i.toString());
			}
			return invoice;
		}
	}

}

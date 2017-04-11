package ru.mail.ales2003.deals2017.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ru.mail.ales2003.deals2017.dao.api.IContract2ItemVariantDao;
import ru.mail.ales2003.deals2017.dao.api.IContractDao;
import ru.mail.ales2003.deals2017.datamodel.Contract;
import ru.mail.ales2003.deals2017.datamodel.Contract2ItemVariant;
import ru.mail.ales2003.deals2017.services.IContractService;

@Service
public class ContractServiceImpl implements IContractService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContractServiceImpl.class);

	@Inject
	private IContractDao contractDao;

	@Inject
	private IContract2ItemVariantDao contract2ItemVariantDao;

	public void addItemVariantToContract(Contract2ItemVariant entity) {
		if (entity.getId() == null) {
			contract2ItemVariantDao.insert(entity);
			LOGGER.info(" ......");
		} else {
			contract2ItemVariantDao.update(entity);
			LOGGER.info(" ......");
		}
	}

	@Override
	public Contract get(Integer id) {
		if (contractDao.get(id) == null) {
			LOGGER.error("Error: contract with id = " + id + " don't exist in storage)");
			return null;
		} else {
			Contract contract = contractDao.get(id);
			LOGGER.info(
					"Read one contract: id={}, сreated={}, contract_status={}, pay_form={}, pay_status={}, customer_id={}, total_price={}",
					contract.getId(), contract.getCreated(), contract.getContractStatus(), contract.getPayForm(),
					contract.getPayStatus(), contract.getCustomerId(), contract.getTotalPrice());
			return contract;
		}
	}

	@Override
	public List<Contract> getAll() {
		if (contractDao.getAll() == null) {
			LOGGER.error("Error: all contracts don't exist in storage");
			return null;
		} else {
			LOGGER.info("Read all contracts");
			return contractDao.getAll();
		}
	}

	@Override
	public void save(Contract contract) {
		if (contract == null) {
			LOGGER.error("Error: as the customer was sent a null reference");
			return;
		} else if (contract.getId() == null) {
			contractDao.insert(contract);
			LOGGER.info(
					"Inserted new contract: id={}, сreated={}, contract_status={}, pay_form={}, pay_status={}, customer_id={}, total_price={}",
					contract.getId(), contract.getCreated(), contract.getContractStatus(), contract.getPayForm(),
					contract.getPayStatus(), contract.getCustomerId(), contract.getTotalPrice());
		} else {
			contractDao.update(contract);
			LOGGER.info(
					"Updated contract: id={}, сreated={}, contract_status={}, pay_form={}, pay_status={}, customer_id={}, total_price={}",
					contract.getId(), contract.getCreated(), contract.getContractStatus(), contract.getPayForm(),
					contract.getPayStatus(), contract.getCustomerId(), contract.getTotalPrice());
		}
	}

	@Override
	public void saveMultiple(Contract... contractArray) {
		for (Contract contract : contractArray) {
			LOGGER.debug("Inserted new contract from array: " + contract);
			save(contract);
		}
		LOGGER.info("Inserted contracts from array");
	}

	@Override
	public void delete(Integer id) {
		if (id == null) {
			LOGGER.error("Error: as the id was sent a null reference");
			return;
		} else {
			contractDao.delete(id);
			LOGGER.info("Deleted contract by id: " + id);
		}
	}

	// !!!!!!!!!!!!!!+=============
	/*
	 * @Override public List<ItemInContract> ic() { return
	 * contractDao.findAll(); }
	 */
}

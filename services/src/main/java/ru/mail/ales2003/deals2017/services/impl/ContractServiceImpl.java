package ru.mail.ales2003.deals2017.services.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ru.mail.ales2003.deals2017.dao.api.IContractDao;
import ru.mail.ales2003.deals2017.dao.api.IItemVariantInContractDao;
import ru.mail.ales2003.deals2017.datamodel.Contract;
import ru.mail.ales2003.deals2017.datamodel.ContractStatus;
import ru.mail.ales2003.deals2017.datamodel.ItemVariantInContract;
import ru.mail.ales2003.deals2017.datamodel.PayStatus;
import ru.mail.ales2003.deals2017.services.IContractService;

@Service
public class ContractServiceImpl implements IContractService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContractServiceImpl.class);

	@Inject
	private IContractDao contractDao;

	@Inject
	private IItemVariantInContractDao itemVariantInContractDao;

	private String contractClassName = Contract.class.getSimpleName();

	private String itemVariantInContractClassName = ItemVariantInContract.class.getSimpleName();

	// ============================Contract management and handling

	// =============READING AREA===============

	@Override
	public Contract getContract(Integer id) {
		if (contractDao.getByManagerOrCustomerId(id) == null) {
			String errMsg = String.format("[%s] entity with id = [%s] don't exist in storage", contractClassName, id);
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg);
		} else {
			Contract contract = contractDao.getByManagerOrCustomerId(id);
			LOGGER.info("Read one {} entity: {}", contractClassName, contract.toString());
			return contract;
		}
	}

	@Override
	public List<Contract> getAllContract() {
		LOGGER.info("{} entities storage returns {} entities.", contractClassName, contractDao.getAll().size());
		LOGGER.info("Read all {} entities:", contractClassName);
		for (Contract cT : contractDao.getAll()) {
			LOGGER.info("{} entity = {}", contractClassName, cT.toString());
		}
		return contractDao.getAll();
	}

	// =============CREATION/UPDATE AREA===============

	@Override
	public void saveContract(Contract contract) {
		if (contract == null) {
			LOGGER.error("Error: as the {} entity was sent a null reference", contractClassName);
			return;
		} else if (contract.getId() == null) {
			contractDao.insert(contract);
			LOGGER.info("Inserted new {} entity: {}", contractClassName, contract.toString());
		} else {
			contractDao.update(contract);
			LOGGER.info("Updated one {} entity: {}", contractClassName, contract.toString());
		}
	}

	@Override
	public void saveContractMultiple(Contract... contractArray) {
		for (Contract contract : contractArray) {
			LOGGER.info("Inserted new {} entity from array: {}", contractClassName, contract.toString());
			saveContract(contract);
		}
		LOGGER.info("{} entities from array were inserted", contractClassName);
	}

	// =============DELETE AREA===============

	@Override
	public void deleteContract(Integer id) {
		if (id == null) {
			LOGGER.error("Error: as the id was sent a null reference");
			return;
		} else {
			contractDao.delete(id);
			LOGGER.info("Deleted {} entity by id: {}", contractClassName, id);
		}
	}
	// =============TOTAL PRICE CALCULATE/UPDATE===============

	@Override
	public BigDecimal calculateContractTotalPrice(Integer id) {
		BigDecimal totalPrice;
		totalPrice = contractDao.calculateContractTotalPrice(id);
		return totalPrice;
	}

	@Override
	public void updateContractTotalPrice(Integer id) {
		BigDecimal totalPrice;
		totalPrice = contractDao.calculateContractTotalPrice(id);
		contractDao.updateContractTotalPrice(id, totalPrice);

	}

	// =============CHECK FOR CHANGEABILITY===============
	@Override
	public Boolean isChangeable(Integer id) {
		// ADD LOGGING
		Boolean isChangeable = true;
		Contract contract = getContract(id);
		if (contract.getContractStatus().equals(ContractStatus.EXECUTED)
				&& contract.getPayStatus().equals(PayStatus.PAID)) {
			isChangeable = false;
		}
		return isChangeable;
		// ADD LOGGING
	}

	// ======================ItemVariant management and handling in a contract

	// =============READING AREA===============

	@Override
	public ItemVariantInContract getItemVariantInContract(Integer id) {
		if (itemVariantInContractDao.getByManagerOrCustomerId(id) == null) {
			String errMsg = String.format("[%s] entity with id = [%s] don't exist in storage",
					itemVariantInContractClassName, id);
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg);
		} else {
			ItemVariantInContract item = itemVariantInContractDao.getByManagerOrCustomerId(id);
			LOGGER.info("Read one {} entity: {}", itemVariantInContractClassName, item.toString());
			return item;
		}
	}

	@Override
	public List<ItemVariantInContract> getAllItemVariantsInContract() {
		LOGGER.info("{} entities storage returns {} entities.", itemVariantInContractClassName,
				itemVariantInContractDao.getAll().size());
		LOGGER.info("Read all {} entities:", itemVariantInContractClassName);
		for (ItemVariantInContract cT : itemVariantInContractDao.getAll()) {
			LOGGER.info("{} entity = {}", itemVariantInContractClassName, cT.toString());
		}
		return itemVariantInContractDao.getAll();
	}

	// =============CREATION/UPDATE AREA===============

	@Override
	public void saveItemVariantInContract(ItemVariantInContract item) {

		if (item == null) {
			LOGGER.error("Error: as the {} entity was sent a null reference", itemVariantInContractClassName);
			return;
		} else if (item.getId() == null) {
			itemVariantInContractDao.insert(item);
			LOGGER.info("Inserted new {} entity: {}", itemVariantInContractClassName, item.toString());

			LOGGER.info("Start  totalPrice updating in contract {} by saving of entity: {}. Total price before = {}. ",
					getContract(item.getContractId()).toString(), item.toString(),
					getContract(item.getContractId()).getTotalPrice());

			updateContractTotalPrice(item.getContractId());

			LOGGER.info("Finish  totalPrice updating in contract {} by saving of entity: {}. Total price after = {}. ",
					getContract(item.getContractId()).toString(), item.toString(),
					getContract(item.getContractId()).getTotalPrice());

		}

		// ADD LOGGING
		else if (isChangeable(item.getId()))
		// ADD LOGGING
		{
			itemVariantInContractDao.update(item);
			LOGGER.info("Updated one {} entity: {}", itemVariantInContractClassName, item.toString());
		}
	}

	@Override
	public void saveItemVariantInContractMultiple(ItemVariantInContract... itemsArray) {
		for (ItemVariantInContract item : itemsArray) {
			LOGGER.info("Inserted new {} entity from array: {}", itemVariantInContractClassName, item.toString());
			saveItemVariantInContract(item);
		}
		LOGGER.info("{} entities from array were inserted", itemVariantInContractClassName);

	}

	// =============DELETE AREA===============

	@Override
	public void deleteItemVariantInContract(Integer id) {
		// ADD LOGGING

		if (isChangeable(id)) {

			

			// ADD LOGGING

			if (id == null) {
				LOGGER.error("Error: as the id was sent a null reference");
				return;
			} else {
				itemVariantInContractDao.delete(id);
				LOGGER.info("Deleted {} entity by id: {}", itemVariantInContractClassName, id);
			}
		}
	}

}

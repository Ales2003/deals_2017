package ru.mail.ales2003.deals2017.services.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ru.mail.ales2003.deals2017.dao.api.customdao.IContractCommonInfoDao;
import ru.mail.ales2003.deals2017.dao.api.customentities.ContractCommonInfo;

@Component
public class CachedDaoDataPersistKeeper {

	private static final Logger LOGGER = LoggerFactory.getLogger(CachedDaoDataPersistKeeper.class);

	private static final String THE_PATH_TO_THE_PERSISTEND_SAVED_COMMON_INFO = "d:/REPO/GitHub/deals_2017/persistend-cache-fromdb-root-folder/contract_info.ser";

	@Inject
	private IContractCommonInfoDao commonInfoDao;

	public void read() {

		LOGGER.info("GETTING the {} from file {}.", "CACHE_ITEM_COMMON_INFO",
				THE_PATH_TO_THE_PERSISTEND_SAVED_COMMON_INFO);
		// read object from file
		Map<String, ArrayList<ContractCommonInfo>> commonInfofromPersistendFile = null;

		try (FileInputStream fileIn = new FileInputStream(THE_PATH_TO_THE_PERSISTEND_SAVED_COMMON_INFO);
				ObjectInputStream in = new ObjectInputStream(fileIn);) {

			commonInfofromPersistendFile = (Map<String, ArrayList<ContractCommonInfo>>) in.readObject();
			LOGGER.info("{} is succefull recovered from file {}.", "CACHE_ITEM_COMMON_INFO",
					THE_PATH_TO_THE_PERSISTEND_SAVED_COMMON_INFO);
		} catch (IOException i) {
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("Employee class not found");
			c.printStackTrace();
			return;
		}
		LOGGER.info("SETTING the recovered {} to IContractCommonInfoDao.", "CACHE_ITEM_COMMON_INFO");
		commonInfoDao.SetPersistentSavedCACHE(commonInfofromPersistendFile);
		LOGGER.info("Recovered {} is SENT.", "CACHE_ITEM_COMMON_INFO");
	}

	public void write() {

		LOGGER.info("GETTING the {} from IContractCommonInfoDao.", "CACHE_ITEM_COMMON_INFO");

		Map<String, ArrayList<ContractCommonInfo>> commonInfofromCache = commonInfoDao.getCACHEToPersistendSave();

		LOGGER.info("{} is RECEIVED with size = {}.", "CACHE_ITEM_COMMON_INFO", commonInfofromCache.size());

		// write object to file to D:\REPO\GitHub\deals_2017\sql-db-root-folder
		//
		try (FileOutputStream fileOut = new FileOutputStream(THE_PATH_TO_THE_PERSISTEND_SAVED_COMMON_INFO);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);) {
			out.writeObject(commonInfofromCache);
			System.out.println("Serialized");
			LOGGER.info("{} is succefull persistend saved.", "CACHE_ITEM_COMMON_INFO");
		} catch (IOException i) {
			LOGGER.info("Saving of {} is failed.", "CACHE_ITEM_COMMON_INFO");
			i.printStackTrace();
		}
	}

	public void clear() {
		commonInfoDao.clearCACHE();
	}
}

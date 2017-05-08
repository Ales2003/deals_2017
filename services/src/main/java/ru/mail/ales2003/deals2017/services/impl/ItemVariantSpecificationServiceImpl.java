package ru.mail.ales2003.deals2017.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ru.mail.ales2003.deals2017.dao.api.customdao.IItemVariantCommonInfoDao;
import ru.mail.ales2003.deals2017.dao.api.customdao.IItemVariantDetailDao;
import ru.mail.ales2003.deals2017.dao.api.customentities.ItemVariantCommonInfo;
import ru.mail.ales2003.deals2017.dao.api.customentities.ItemVariantDetail;
import ru.mail.ales2003.deals2017.dao.api.customentities.ItemVariantSpecification;
import ru.mail.ales2003.deals2017.dao.api.filters.IItemVariantFilter;
import ru.mail.ales2003.deals2017.datamodel.ItemVariant;
import ru.mail.ales2003.deals2017.services.IItemVariantSpecificationService;

@Service
public class ItemVariantSpecificationServiceImpl implements IItemVariantSpecificationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemVariantSpecificationServiceImpl.class);

	@Inject
	public IItemVariantCommonInfoDao commonInfoDao;

	@Inject
	public IItemVariantDetailDao detailsDao;

	private String itemVariantClassName = ItemVariant.class.getSimpleName();

	private String commonInfoClassName = ItemVariantCommonInfo.class.getSimpleName();

	@Override
	public ItemVariantCommonInfo getCommonInfo(Integer itemVariantId) {
		if (commonInfoDao.getCommonInfo(itemVariantId) == null) {
			String errMsg = String.format("[%s] entity with id = [%s] don't exist in storage", itemVariantClassName,
					itemVariantId);
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg);
		} else {
			ItemVariantCommonInfo commonInfo = commonInfoDao.getCommonInfo(itemVariantId);
			LOGGER.info("Read common information about {} with id = {} with basic info: {}", itemVariantClassName,
					itemVariantId, commonInfo.toString());
			return commonInfo;
		}
	}

	@Override
	public List<ItemVariantCommonInfo> getCommonInfoForAll() {
		List<ItemVariantCommonInfo> entities = commonInfoDao.getCommonInfoForAll();
		if (entities == null) {
			String errMsg = String.format("[%s] entities storage is epty", itemVariantClassName);
			LOGGER.error("Error: {}", errMsg);
			// return null;
		}
		LOGGER.info("{} entities storage returns {} entities: ", itemVariantClassName, entities.size());
		LOGGER.info("Read all {} entities with common info: ", itemVariantClassName);
		for (ItemVariantCommonInfo cI : entities) {
			LOGGER.info("{} entity = {}", commonInfoClassName, cI.toString());
		}
		return entities;
	}

	@Override
	public List<ItemVariantCommonInfo> getCommonInfoFiltered(IItemVariantFilter filter) {
		List<ItemVariantCommonInfo> entities = commonInfoDao.getCommonInfoFiltered(filter);
		if (entities == null) {
			String errMsg = String.format("[%s] entities storage is epty", itemVariantClassName);
			LOGGER.error("Error: {}", errMsg);
			// return null;
		}
		LOGGER.info("{} entities storage returns {} entities: ", itemVariantClassName, entities.size());
		LOGGER.info("Read all {} entities with common info with filter: {}", itemVariantClassName, filter.toString());
		for (ItemVariantCommonInfo cI : entities) {
			LOGGER.info("{} entity = {}", commonInfoClassName, cI.toString());
		}
		return entities;

	}

	@Override
	public List<ItemVariantDetail> getDetails(Integer itemVariantId) {
		if (detailsDao.getDetails(itemVariantId) == null) {
			String errMsg = String.format("[%s] entity with id = [%s] don't exist in storage", itemVariantClassName,
					itemVariantId);
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg);
		} else {
			List<ItemVariantDetail> details = detailsDao.getDetails(itemVariantId);
			LOGGER.info("Read details of {} with id = {} : ", itemVariantClassName, itemVariantId);
			for (ItemVariantDetail vD : details) {
				LOGGER.info("itemVariantDetail = {}", vD.toString());
			}
			return details;
		}
	}

	@Override
	public ItemVariantSpecification getSpecification(Integer itemVariantId) {
		if (commonInfoDao.getCommonInfo(itemVariantId) == null || detailsDao.getDetails(itemVariantId) == null) {
			String errMsg = String.format("[%s] entity with id = [%s] don't exist in storage", itemVariantClassName,
					itemVariantId);
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg);
		} else {
			LOGGER.info("Read specification of {} with id = {} with basic info and details: ", itemVariantClassName,
					itemVariantId);
			ItemVariantSpecification specification = new ItemVariantSpecification();
			specification.setInfo(commonInfoDao.getCommonInfo(itemVariantId));
			specification.setDetails(detailsDao.getDetails(itemVariantId));
			specification.setId(itemVariantId);
			LOGGER.info("Specification is created: ");

			LOGGER.info("Common info of {} = {} ", itemVariantClassName, specification.getInfo().toString());
			for (ItemVariantDetail vD : specification.getDetails()) {
				LOGGER.info("Detail of {} = {}", vD.toString());
			}
			return specification;
		}
	}

}

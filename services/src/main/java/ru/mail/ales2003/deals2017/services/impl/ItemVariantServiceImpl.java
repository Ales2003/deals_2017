package ru.mail.ales2003.deals2017.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ru.mail.ales2003.deals2017.dao.impl.db.IItemVariantDao;
import ru.mail.ales2003.deals2017.datamodel.ItemVariant;
import ru.mail.ales2003.deals2017.services.IItemVariantService;

@Service
public class ItemVariantServiceImpl implements IItemVariantService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemVariantServiceImpl.class);

	@Inject
	public IItemVariantDao itemVariantDao;

	@Override
	public ItemVariant get(Integer id) {
		if (itemVariantDao.get(id) == null) {
			LOGGER.error("Error: itemVariant with id = " + id + " don't exist in storage)");
			return null;
		} else {
			ItemVariant itemVariant = itemVariantDao.get(id);
			LOGGER.info("Read one itemVariant: id={}, itemId={}, variantPrice={}", itemVariant.getId(),
					itemVariant.getItemId(), itemVariant.getVariantPrice());
			return itemVariant;
		}
	}

	@Override
	public List<ItemVariant> getAll() {
		if (itemVariantDao.getAll() == null) {
			LOGGER.error("Error: all itemVariants don't exist in storage");
			return null;
		} else {
			LOGGER.info("Read all itemVariants");
			return itemVariantDao.getAll();
		}
	}

	@Override
	public void save(ItemVariant itemVariant) {
		if (itemVariant == null) {
			LOGGER.error("Error: as the item was sent a null reference");
			return;
		} else if (itemVariant.getId() == null) {
			itemVariantDao.insert(itemVariant);
			LOGGER.info("Inserted new itemVariant: id={}, itemId={}, variantPrice={}", itemVariant.getId(),
					itemVariant.getItemId(), itemVariant.getVariantPrice());
		} else {
			itemVariantDao.update(itemVariant);
			LOGGER.info("Updated itemVariant: id={}, itemId={}, variantPrice={}", itemVariant.getId(),
					itemVariant.getItemId(), itemVariant.getVariantPrice());
		}

	}

	@Override
	public void saveMultiple(ItemVariant... itemVariantArray) {
		for (ItemVariant itemVariant : itemVariantArray) {
			LOGGER.debug("Inserted new itemVariant from array: " + itemVariant);
			save(itemVariant);
		}
		LOGGER.info("Inserted itemVariants from array");

	}

	@Override
	public void delete(Integer id) {
		if (id == null) {
			LOGGER.error("Error: as the id was sent a null reference");
			return;
		} else {
			itemVariantDao.delete(id);
			LOGGER.info("Deleted itemVariant by id: " + id);
		}

	}

}

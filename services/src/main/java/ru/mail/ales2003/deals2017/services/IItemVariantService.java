package ru.mail.ales2003.deals2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ru.mail.ales2003.deals2017.datamodel.ItemVariant;

public interface IItemVariantService {

	ItemVariant get(Integer id);

	List<ItemVariant> getAll();

	@Transactional
	void save(ItemVariant itemVariant);

	@Transactional
	void saveMultiple(ItemVariant... itemVariantArray);

	@Transactional
	void delete(Integer id);

}

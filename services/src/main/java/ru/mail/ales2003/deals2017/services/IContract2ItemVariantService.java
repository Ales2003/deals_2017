package ru.mail.ales2003.deals2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ru.mail.ales2003.deals2017.datamodel.Contract2ItemVariant;

public interface IContract2ItemVariantService {

	Contract2ItemVariant get(Integer id);

	List<Contract2ItemVariant> getAll();

	@Transactional
	void save(Contract2ItemVariant entity);

	@Transactional
	void saveMultiple(Contract2ItemVariant... entity);

	void delete(Integer id);

}

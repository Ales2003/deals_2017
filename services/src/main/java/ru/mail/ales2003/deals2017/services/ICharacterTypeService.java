package ru.mail.ales2003.deals2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ru.mail.ales2003.deals2017.datamodel.CharacterType;

public interface ICharacterTypeService {
	
	CharacterType get(Integer id);

	List<CharacterType> getAll();

	@Transactional
	void save(CharacterType entity);

	@Transactional
	void saveMultiple(CharacterType... entityArray);

	@Transactional
	void delete(Integer id);

}

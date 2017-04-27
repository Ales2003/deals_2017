package ru.mail.ales2003.deals2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ru.mail.ales2003.deals2017.datamodel.CharacterType;

public interface ICharacterTypeService {

	/**
	 * @param id
	 * @return CharacterType entity
	 */
	CharacterType get(Integer id);

	/**
	 * @return List&ltCharacterType&gt entities
	 */
	List<CharacterType> getAll();

	/**
	 * @param entity
	 */
	@Transactional
	void save(CharacterType entity);

	/**
	 * @param entityArray
	 */
	@Transactional
	void saveMultiple(CharacterType... entityArray);

	/**
	 * @param id
	 */
	@Transactional
	void delete(Integer id);

	// Methods to avoid duplication in the CharacterType storage

	void fillChecker();

	void clearChecker();

	boolean isExist(CharacterType entity);

}

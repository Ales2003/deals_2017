package ru.mail.ales2003.deals2017.dao.impl.db;

import java.util.List;

import ru.mail.ales2003.deals2017.datamodel.CharacterType;

public interface ICharacterTypeDao {

	CharacterType insert(CharacterType entity);

	CharacterType get(Integer id);

	List<CharacterType> getAll();

	void update(CharacterType entity);

	void delete(Integer id);

}

package ru.mail.ales2003.deals2017.dao.api;

import java.util.List;

import ru.mail.ales2003.deals2017.datamodel.CharacterType2ItemVariant;

public interface ICharacterType2ItemVariantDao {

	CharacterType2ItemVariant insert(CharacterType2ItemVariant entity);

	CharacterType2ItemVariant get(Integer id);

	List<CharacterType2ItemVariant> getAll();

	void update(CharacterType2ItemVariant entity);

	void delete(Integer id);

}

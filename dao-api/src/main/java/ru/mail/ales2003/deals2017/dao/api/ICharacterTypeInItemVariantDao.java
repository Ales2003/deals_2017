package ru.mail.ales2003.deals2017.dao.api;

import java.util.List;

import ru.mail.ales2003.deals2017.datamodel.CharacterTypeInItemVariant;

public interface ICharacterTypeInItemVariantDao {

	CharacterTypeInItemVariant insert(CharacterTypeInItemVariant entity);

	CharacterTypeInItemVariant get(Integer id);

	List<CharacterTypeInItemVariant> getAll();

	void update(CharacterTypeInItemVariant entity);

	void delete(Integer id);

}

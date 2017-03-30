package ru.mail.ales2003.deals2017.dao.impl.db.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.impl.db.ICharacterType2ItemVariantDao;
import ru.mail.ales2003.deals2017.datamodel.CharacterType2ItemVariant;

@Repository
public class CharacterType2ItemVariantDaoImpl implements ICharacterType2ItemVariantDao {

	@Inject
	private JdbcTemplate jdbcTemplate;

	// =============CREATION AREA===============

	@Override
	public CharacterType2ItemVariant insert(CharacterType2ItemVariant entity) {
		// TODO Auto-generated method stub
		return null;
	}

	// =============READING AREA===============

	@Override
	public CharacterType2ItemVariant get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CharacterType2ItemVariant> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	// =============UPDATE AREA===============

	@Override
	public void update(CharacterType2ItemVariant entity) {
		// TODO Auto-generated method stub

	}

	// =============DELETE AREA===============

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

}

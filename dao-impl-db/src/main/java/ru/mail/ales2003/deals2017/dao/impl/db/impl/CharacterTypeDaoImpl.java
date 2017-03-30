package ru.mail.ales2003.deals2017.dao.impl.db.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.impl.db.ICharacterTypeDao;
import ru.mail.ales2003.deals2017.datamodel.CharacterType;

@Repository
public class CharacterTypeDaoImpl implements ICharacterTypeDao {

	@Inject
	private JdbcTemplate jdbcTemplate;

	// =============CREATION AREA===============

	@Override
	public CharacterType insert(CharacterType entity) {
		// TODO Auto-generated method stub
		return null;
	}

	// =============READING AREA===============

	@Override
	public CharacterType get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CharacterType> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	// =============UPDATE AREA===============

	@Override
	public void update(CharacterType entity) {
		// TODO Auto-generated method stub

	}

	// =============DELETE AREA===============

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

}

package ru.mail.ales2003.deals2017.dao.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.api.IItemVariantInContractDao;
import ru.mail.ales2003.deals2017.datamodel.ItemVariantInContract;

@Repository
public class ItemVariantInContractDaoImpl implements IItemVariantInContractDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemVariantInContractDaoImpl.class);

	@Inject
	private JdbcTemplate jdbcTemplate;

	// =============CREATION AREA===============

	@Override
	public ItemVariantInContract insert(ItemVariantInContract entity) {
		final String INSERT_SQL = "insert into item_variant_in_contract (quantity, contract_id, item_variant_id) values(?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
				ps.setInt(1, entity.getQuantity());
				ps.setInt(2, entity.getContractId());
				ps.setInt(3, entity.getItemVariantId());
				return ps;
			}
		}, keyHolder);
		entity.setId(keyHolder.getKey().intValue());
		return entity;
	}

	// =============READING AREA===============

	@Override
	public ItemVariantInContract get(Integer id) {
		final String READ_BY_ID_SQL = "select * from item_variant_in_contract where id = ? ";
		try {
			return jdbcTemplate.queryForObject(READ_BY_ID_SQL, new Object[] { id },
					new BeanPropertyRowMapper<ItemVariantInContract>(ItemVariantInContract.class));
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("Error: itemVariantInContract with id = " + id + " don't exist in storage)", e);
			// throw e;
			return null;
		}
	}

	@Override
	public List<ItemVariantInContract> getAll() {
		try {
			final String READ_ALL_SQL = "select * from item_variant_in_contract";
			List<ItemVariantInContract> characterTypes = jdbcTemplate.query(READ_ALL_SQL,
					new BeanPropertyRowMapper<ItemVariantInContract>(ItemVariantInContract.class));
			return characterTypes;
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("Error: all itemVariantInContracts don't exist in storage", e);
			return null;
		}
	}

	// =============UPDATE AREA===============

	@Override
	public void update(ItemVariantInContract entity) {
		final String UPDATE_SQL = "update item_variant_in_contract set quantity = ?, contract_id =?, item_variant_id=?  where id = ?";
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(UPDATE_SQL, new String[] { "id" });
				ps.setInt(1, entity.getQuantity());
				ps.setInt(2, entity.getContractId());
				ps.setInt(3, entity.getItemVariantId());
				ps.setInt(4, entity.getId());
				return ps;
			}
		});
	}

	// =============DELETE AREA===============

	@Override
	public void delete(Integer id) {
		jdbcTemplate.update("delete from item_variant_in_contract where id=" + id);

	}

}

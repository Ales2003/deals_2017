package ru.mail.ales2003.deals2017.dao.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.api.IContractDao;
import ru.mail.ales2003.deals2017.datamodel.Contract;

@Repository
public class ContractDaoImpl extends AbstractDaoImplDb<Contract, Integer> implements IContractDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContractDaoImpl.class);

	// =============CREATION AREA===============

	@Override
	public Contract insert(Contract entity) {
		final String INSERT_SQL = "insert into contract (created, contract_status, pay_form, pay_status, customer_id, total_price) values(?, ?, ?, ?, ?, ?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
				ps.setTimestamp(1, entity.getCreated());
				ps.setString(2, entity.getContractStatus().name());
				ps.setString(3, entity.getPayForm().name());
				ps.setString(4, entity.getPayStatus().name());
				ps.setInt(5, entity.getCustomerId());
				ps.setBigDecimal(6, entity.getTotalPrice());

				return ps;
			}
		}, keyHolder);

		entity.setId(keyHolder.getKey().intValue());

		return entity;
	}

	// =============READING AREA===============

	@Override
	public Contract get(Integer id) {
		try {
			return jdbcTemplate.queryForObject("select * from contract where id = ? ", new Object[] { id },
					new BeanPropertyRowMapper<Contract>(Contract.class));
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("Error: contract with id = " + id + " don't exist in storage)", e);
			return null;
		}
	}

	@Override
	public List<Contract> getAll() {
		try {
			List<Contract> rs = jdbcTemplate.query("select * from contract ",
					new BeanPropertyRowMapper<Contract>(Contract.class));
			return rs;
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("Error: all contracts don't exist in storage", e);
			return null;
		}
	}

	// =============UPDATE AREA===============

	@Override
	public void update(Contract entity) {
		final String UPDATE_SQL = "update contract set created = ?, contract_status = ?, pay_form = ?, pay_status = ?,"
				+ " customer_id = ?, total_price = ? where id = ?";

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(UPDATE_SQL, new String[] { "id" });
				ps.setTimestamp(1, entity.getCreated());
				ps.setString(2, entity.getContractStatus().name());
				ps.setString(3, entity.getPayForm().name());
				ps.setString(4, entity.getPayStatus().name());
				ps.setInt(5, entity.getCustomerId());
				ps.setBigDecimal(6, entity.getTotalPrice());
				ps.setInt(7, entity.getId());
				return ps;
			}
		});
	}

	// =============DELETE AREA===============

	@Override
	public void delete(Integer id) {
		jdbcTemplate.update("delete from contract where id=" + id);
	}

	// !!!=============CUSTOM MAPPING AREA===============
	/*
	 * public List<ItemVariantDetail> findAll() {
	 * 
	 * String sql =
	 * "SELECT * FROM item_variant as iv left join character_type_in_item_variant as ctiiv where iv.id=ctiiv.item_variant_id left join character_type as ct where ct.id=ctiiv.character_type_id where iv.id=86"
	 * ;
	 * 
	 * List<ItemVariantDetail> items = new ArrayList<>();
	 * 
	 * List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql); for (Map
	 * row : rows) { ItemVariantDetail item = new ItemVariantDetail();
	 * item.setContractId((Integer) (row.get("id")));
	 * item.setCustomerId((Integer) (row.get("customer_id"))); items.add(item);
	 * }
	 * 
	 * return items; }
	 */

}

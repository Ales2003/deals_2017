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

import ru.mail.ales2003.deals2017.dao.api.IItemVariantDao;
import ru.mail.ales2003.deals2017.datamodel.ItemVariant;

//@Repository
public class OldItemVariantDaoImpl implements IItemVariantDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(OldItemVariantDaoImpl.class);

	@Inject
	private JdbcTemplate jdbcTemplate;

	// =============CREATION AREA===============

	@Override
	public ItemVariant insert(ItemVariant entity) {
		final String INSERT_SQL = "insert into item_variant (item_id, variant_price) values(?,?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
				ps.setInt(1, entity.getItemId());
				ps.setBigDecimal(2, entity.getVariantPrice());
				return ps;
			}
		}, keyHolder);
		entity.setId(keyHolder.getKey().intValue());
		return entity;
	}

	// =============READING AREA===============

	@Override
	public ItemVariant get(Integer id) {
		final String READ_BY_ID_SQL = "select * from item_variant where id = ? ";

		try {
			return jdbcTemplate.queryForObject(READ_BY_ID_SQL, new Object[] { id },
					new BeanPropertyRowMapper<ItemVariant>(ItemVariant.class));
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("Error: itemVariant with id = " + id + " don't exist in storage)", e);
			return null;
		}
	}

	@Override
	public List<ItemVariant> getAll() {
		try {
			List<ItemVariant> rs = jdbcTemplate.query("select * from item_variant ",
					new BeanPropertyRowMapper<ItemVariant>(ItemVariant.class));
			return rs;
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("Error: all itemVariants don't exist in storage", e);
			return null;
		}
	}

	// =============UPDATE AREA===============

	@Override
	public void update(ItemVariant entity) {
		final String UPDATE_SQL = "update item_variant set item_id = ?, variant_price = ? where id = ?";

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				// Value of "id" field is not required here, so I deleted it:
				// PreparedStatement ps =
				// connection.prepareStatement(UPDATE_SQL, new String[] { "id"
				// });
				PreparedStatement ps = connection.prepareStatement(UPDATE_SQL);
				ps.setInt(1, entity.getItemId());
				ps.setBigDecimal(2, entity.getVariantPrice());
				ps.setInt(3, entity.getId());
				return ps;
			}
		});
	}

	// =============DELETE AREA===============

	@Override
	public void delete(Integer id) {
		jdbcTemplate.update("delete from item_variant where id=" + id);

	}

}

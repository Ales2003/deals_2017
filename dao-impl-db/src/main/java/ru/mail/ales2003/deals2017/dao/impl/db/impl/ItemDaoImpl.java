package ru.mail.ales2003.deals2017.dao.impl.db.impl;

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

import ru.mail.ales2003.deals2017.dao.impl.db.IItemDao;
import ru.mail.ales2003.deals2017.datamodel.Item;

@Repository
public class ItemDaoImpl implements IItemDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemDaoImpl.class);

	@Inject
	private JdbcTemplate jdbcTemplate;

	// =============CREATION AREA===============

	@Override
	public Item insert(Item item) {
		final String INSERT_SQL = "insert into item (name, description, basic_price) values(?,?,?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
				ps.setString(1, item.getName());
				ps.setString(2, item.getDescription());
				ps.setBigDecimal(3, item.getBasicPrice());
				return ps;
			}
		}, keyHolder);
		item.setId(keyHolder.getKey().intValue());
		return item;
	}

	// =============READING AREA===============

	@Override
	public Item get(Integer id) {
		final String READ_BY_ID_SQL = "select * from item where id = ? ";

		try {
			return jdbcTemplate.queryForObject(READ_BY_ID_SQL, new Object[] { id },
					new BeanPropertyRowMapper<Item>(Item.class));
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("Error: item with id = " + id + " don't exist in storage)", e);
			return null;
		}
	}

	@Override
	public List<Item> getAll() {
		try {
			List<Item> rs = jdbcTemplate.query("select * from item ", new BeanPropertyRowMapper<Item>(Item.class));
			return rs;
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("Error: all items don't exist in storage", e);
			return null;
		}
	}

	// =============UPDATE AREA===============

	@Override
	public void update(Item item) {

		final String UPDATE_SQL = "update item set name = ?, description = ?, basic_price = ? where id = ?";

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				// Value of "id" field is not required here, so I deleted it:
				// PreparedStatement ps =
				// connection.prepareStatement(UPDATE_SQL, new String[] { "id"
				// });
				PreparedStatement ps = connection.prepareStatement(UPDATE_SQL);
				ps.setString(1, item.getName());
				ps.setString(2, item.getDescription());
				ps.setBigDecimal(3, item.getBasicPrice());
				ps.setInt(4, item.getId());
				return ps;
			}
		});
	}

	// =============DELETE AREA===============

	@Override
	public void delete(Integer id) {
		jdbcTemplate.update("delete from item where id=" + id);
	}
}

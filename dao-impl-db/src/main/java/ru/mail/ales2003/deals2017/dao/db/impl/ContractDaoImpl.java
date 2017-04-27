package ru.mail.ales2003.deals2017.dao.db.impl;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.api.IContractDao;
import ru.mail.ales2003.deals2017.datamodel.Contract;

@Repository
public class ContractDaoImpl extends AbstractDaoImplDb<Contract, Integer> implements IContractDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContractDaoImpl.class);

	// =============CREATION AREA===============

	@Override
	protected String getInsertQuery() {
		final String INSERT_SQL = "insert into contract (created, contract_status, pay_form, pay_status, customer_id, total_price) values(?, ?, ?, ?, ?, ?)";
		return INSERT_SQL;
	}

	@Override
	protected Contract setEntityId(Contract entity, Integer id) {
		entity.setId(id);
		return entity;
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement ps, Contract entity) {
		try {
			ps.setTimestamp(1, entity.getCreated());
			ps.setString(2, entity.getContractStatus().name());
			ps.setString(3, entity.getPayForm().name());
			ps.setString(4, entity.getPayStatus().name());
			ps.setInt(5, entity.getCustomerId());
			ps.setBigDecimal(6, entity.getTotalPrice());
		} catch (SQLException e) {
			LOGGER.error("Error: prepareStatementForInsert is broken: {}", e);
			// !!!TO REFACTOR!!!
			throw new RuntimeException(e);
		}

	}

	// =============READING AREA===============

	@Override
	protected String getSelectQuery() {
		final String READ_BY_ID_SQL = "select * from contract";
		return READ_BY_ID_SQL;
	}

	@Override
	protected Class<Contract> getMappedClass() {
		return Contract.class;
	}

	// =============UPDATE AREA===============

	@Override
	protected String getUpdateQuery() {
		final String UPDATE_SQL = "update contract set created = ?, contract_status = ?, pay_form = ?, pay_status = ?,"
				+ " customer_id = ?, total_price = ? where id = ?";
		return UPDATE_SQL;
	}

	@Override
	protected void prepareStatementForUpdate(PreparedStatement ps, Contract entity) {
		try {
			ps.setTimestamp(1, entity.getCreated());
			ps.setString(2, entity.getContractStatus().name());
			ps.setString(3, entity.getPayForm().name());
			ps.setString(4, entity.getPayStatus().name());
			ps.setInt(5, entity.getCustomerId());
			ps.setBigDecimal(6, entity.getTotalPrice());
			ps.setInt(7, entity.getId());
		} catch (SQLException e) {
			LOGGER.error("Error: prepareStatementForInsert is broken", e);
			// !!!TO REFACTOR!!!
			throw new RuntimeException(e);
		}
	}

	// =============DELETE AREA===============

	@Override
	protected String getDeleteQuery() {
		final String DELETE_BY_ID_SQL = "delete from contract";
		return DELETE_BY_ID_SQL;
	}

	// =============TOTAL PRICE CALCULATE===============

	@SuppressWarnings("deprecation")
	public BigDecimal calculateContractTotalPrice(Integer id) {
		BigDecimal totalPrice;
		if (id == null) {
			String errMsg = String.format("Error: as the id was sent a null reference.");
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg);
		}
		final String SELECT_BY_ID_SQL = "select sum(invoice.quantity*iv.variant_price)"
				+ " from contract left join item_variant_in_contract as invoice on contract.id=invoice.contract_id"
				+ " left join item_variant as iv on iv.id=invoice.item_variant_id"
				+ "  left join public.item as item on iv.item_id=item.id" + " where contract.id=?"
				+ " GROUP BY contract.id";
		try {

			totalPrice = BigDecimal.valueOf(jdbcTemplate.queryForInt(SELECT_BY_ID_SQL, new Object[] { id }));

		} catch (EmptyResultDataAccessException e) {
			String errMsg = String.format(
					"You want to READ the [%s] with id = [%s], but it doesn't exist in the storage.",
					getMappedClass().getSimpleName(), id);
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg, e);
		}
		return totalPrice.setScale(2, BigDecimal.ROUND_HALF_EVEN);
	}

	// =============TOTAL PRICE UPDATE===============

	@Override
	public void updateContractTotalPrice(Integer contractId, BigDecimal totalPrice) {

		final String UPDATE_BY_SQL = "update contract set total_price = " + totalPrice + " where contract.id= "
				+ contractId;

		int i = jdbcTemplate.update(UPDATE_BY_SQL);
		if (i == 0) {
			String errMsg = String.format(
					"You want to UPDATE field totalPrice of the [%s] with id = [%s], but it doesn't exist in the storage.",
					getMappedClass().getSimpleName(), contractId);
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg);
		}

	}
}

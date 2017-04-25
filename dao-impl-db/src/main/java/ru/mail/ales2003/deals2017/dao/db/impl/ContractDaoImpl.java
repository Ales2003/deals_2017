package ru.mail.ales2003.deals2017.dao.db.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	/*
	 * @Override public void contractTotalPriceCalculate(Integer id) {
	 * jdbcTemplate.update(new PreparedStatementCreator() {
	 * 
	 * @Override public PreparedStatement createPreparedStatement(Connection
	 * connection) throws SQLException { // Value of "id" field is not required
	 * here, so I deleted it: // PreparedStatement ps = //
	 * connection.prepareStatement(UPDATE_SQL, new String[] { "id" // });
	 * PreparedStatement ps = connection.prepareStatement(UPDATE_SQL);
	 * prepareStatementForUpdate(ps, entity);
	 * 
	 * } }); }
	 */
/*
	@Override
	public void contractTotalPriceCalculate(Integer id) {
		final String UPDATE_TOTAL_PRICE_SQL = "update contract set total_price="
				+ "(select sum(invoice.quantity*iv.variant_price)"
				+ " from contract left join item_variant_in_contract as invoice on contract.id=invoice.contract_id"
				+ " left join item_variant as iv on iv.id=invoice.item_variant_id"
				+ " left join public.item as item on iv.item_id=item.id"
				+ " where contract.id=? GROUP BY contract.id)"; //where contract.id=?";

		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(UPDATE_TOTAL_PRICE_SQL, new String[] { "id"});
				ps.setInt(1, id);
				ps.setInt(2, id);
				return ps;
				
			}
			
		});

	}
*/
}

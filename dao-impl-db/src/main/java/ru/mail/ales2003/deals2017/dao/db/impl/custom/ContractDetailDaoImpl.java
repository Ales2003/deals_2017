package ru.mail.ales2003.deals2017.dao.db.impl.custom;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.api.customdao.IContractDetailDao;
import ru.mail.ales2003.deals2017.dao.api.customentities.ContractDetail;
import ru.mail.ales2003.deals2017.dao.db.impl.mapper.ContractDetailMapper;
import ru.mail.ales2003.deals2017.datamodel.Contract;

@Repository
public class ContractDetailDaoImpl implements IContractDetailDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContractDetailDaoImpl.class);

	private String contactClassName = Contract.class.getSimpleName();

	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<ContractDetail> getDetails(Integer contractId) {
		if (contractId == null) {
			String errMsg = String.format("Error: as the itemVariantId was sent a null reference.");
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg);
		}

		final String READ_BY_ID_SQL = "select c.id as contract_id, ivic.id as item_variant_id, item.name as item_name, "
				+ "ivic.quantity as item_variant_qnt, iv.variant_price as item_variant_price, "
				+ "ivic.quantity*iv.variant_price as item_variant_total_price "
				+ "from contract as c left join item_variant_in_contract as ivic on c.id=ivic.contract_id"
				+ " left join item_variant as iv on iv.id=ivic.item_variant_id"
				+ " left join item as item on iv.item_id=item.id where c.id=? order by ivic.id";
		try {
			List<ContractDetail> details = jdbcTemplate.query(READ_BY_ID_SQL, new Object[] { contractId },
					new ContractDetailMapper());
			return details;
		} catch (EmptyResultDataAccessException e) {
			String errMsg = String.format("Class [%s] storage returns incorrect entity count.", contactClassName);
			LOGGER.error("Error: {}", errMsg);
			throw e;
		}
	}

}

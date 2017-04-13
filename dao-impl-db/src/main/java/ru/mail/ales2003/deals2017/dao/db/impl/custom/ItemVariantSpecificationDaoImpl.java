package ru.mail.ales2003.deals2017.dao.db.impl.custom;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import ru.mail.ales2003.deals2017.dao.api.custom.IDetailDao;
import ru.mail.ales2003.deals2017.dao.api.custom.IItemVariantSpecificationDao;
import ru.mail.ales2003.deals2017.dao.api.custom.entities.ItemVariantSpecification;
import ru.mail.ales2003.deals2017.datamodel.Contract;

@Resource
public class ItemVariantSpecificationDaoImpl implements IItemVariantSpecificationDao {

	@Inject
	IDetailDao detailDao;

	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public ItemVariantSpecification getByItemVariant(Integer itemVariantId) {
		/*try {
			return jdbcTemplate.queryForObject("select * from contract where id = ? ", new Object[] { id },
					new BeanPropertyRowMapper<Contract>(Contract.class));
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("Error: contract with id = " + id + " don't exist in storage)", e);
			return null;
		}*/
		return null;}

}

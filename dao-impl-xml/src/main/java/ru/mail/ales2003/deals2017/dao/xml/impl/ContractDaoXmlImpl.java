package ru.mail.ales2003.deals2017.dao.xml.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.api.IContractDao;
import ru.mail.ales2003.deals2017.datamodel.Contract;

@Repository
public class ContractDaoXmlImpl implements IContractDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ru.mail.ales2003.deals2017.dao.api.GenericDao#delete(java.lang.Object)
	 */
	@Override
	public void delete(Integer arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ru.mail.ales2003.deals2017.dao.api.GenericDao#get(java.lang.Object)
	 */
	@Override
	public Contract get(Integer arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ru.mail.ales2003.deals2017.dao.api.GenericDao#getAll()
	 */
	@Override
	public List<Contract> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ru.mail.ales2003.deals2017.dao.api.GenericDao#insert(java.lang.Object)
	 */
	@Override
	public Contract insert(Contract arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ru.mail.ales2003.deals2017.dao.api.GenericDao#update(java.lang.Object)
	 */
	@Override
	public void update(Contract arg0) {
		// TODO Auto-generated method stub

	}

	public BigDecimal calculateContractTotalPrice(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}


	public void updateContractTotalPrice(Integer contractId, BigDecimal totalPrice) {
		// TODO Auto-generated method stub

	}

}

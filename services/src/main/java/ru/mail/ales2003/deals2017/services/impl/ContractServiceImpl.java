package ru.mail.ales2003.deals2017.services.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import ru.mail.ales2003.deals2017.dao.impl.db.IContractDao;
import ru.mail.ales2003.deals2017.datamodel.Contract;
import ru.mail.ales2003.deals2017.services.IContractService;

@Service
public class ContractServiceImpl implements IContractService {

	
	@Inject
	private IContractDao contractDao;
	
	@Override
	public void save (Contract contract) {
		if (contract.getId() == null) {
			System.out.println("Insert new Contract");
			contractDao.insert(contract);
		} else {
			//
		}

	}

	@Override
	public Contract get(Integer id) {
		return contractDao.get(id);
	}

	@Override
	public void update(Contract contract) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

}

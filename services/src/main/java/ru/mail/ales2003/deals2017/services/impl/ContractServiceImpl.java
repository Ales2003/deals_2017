package ru.mail.ales2003.deals2017.services.impl;

import java.util.List;

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
	public Contract get(Integer id) {
		return contractDao.get(id);
	}

	@Override
	public List<Contract> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Contract contract) {
		if (contract.getId() == null) {
			System.out.println("Insert new Contract");
			contractDao.insert(contract);
		} else {
			//
		}

	}

	@Override
	public void saveMultiple(Contract... contract) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

}

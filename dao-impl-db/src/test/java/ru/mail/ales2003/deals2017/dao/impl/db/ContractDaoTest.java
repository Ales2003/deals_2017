package ru.mail.ales2003.deals2017.dao.impl.db;

import java.sql.Timestamp;
import java.util.Date;

import javax.inject.Inject;

import org.junit.Test;

import ru.mail.ales2003.deals2017.datamodel.Contract;
import ru.mail.ales2003.deals2017.datamodel.ContractStatus;
import ru.mail.ales2003.deals2017.datamodel.PayForm;
import ru.mail.ales2003.deals2017.datamodel.PayStatus;

public class ContractDaoTest extends AbstractTest {

	@Inject
	private IContractDao contractDao;

	@Test
	public void insertTest() {
		System.out.println("======================insertTest()===========================");
		System.out.println("creating a new contract");

		Contract contract = new Contract();
		contract.setСreated(new Timestamp(new Date().getTime()));
		contract.setContractStatus(ContractStatus.CONTRACT_PREPARATION);
		contract.setPayForm(PayForm.CASH);
		contract.setPayStatus(PayStatus.UNPAID);
		contract.setCustomerId(1);

		System.out.println("contract after creating: " + contract);
		System.out.println("saving the contract in DB");

		contractDao.insert(contract);

		System.out.println("group after save in DB: " + contract);
	}

}

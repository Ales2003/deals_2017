package ru.mail.ales2003.deals2017.webapp;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.mail.ales2003.deals2017.datamodel.Contract;
import ru.mail.ales2003.deals2017.datamodel.ContractStatus;
import ru.mail.ales2003.deals2017.datamodel.PayForm;
import ru.mail.ales2003.deals2017.datamodel.PayStatus;
import ru.mail.ales2003.deals2017.services.IContractService;

public class DateTest {
	public static void main(String[] args) {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("web-context.xml");

		IContractService service = context.getBean(IContractService.class);

		Contract c = new Contract();
		Timestamp t = new Timestamp(new Date().getTime());
		System.out.println(t);
		c.setCreated(t);
		c.setContractStatus(ContractStatus.CONTRACT_PREPARATION);
		c.setPayForm(PayForm.CASH);
		c.setPayStatus(PayStatus.UNPAID);
		c.setCustomerId(1);
		service.saveContract(c);
		Contract d = service.getContract(c.getId());
		Timestamp l = d.getCreated();
		System.out.println("Полная форма");
		System.out.println(l);
		System.out.println("Короткая форма");
		System.out.println(DateFormat.getDateInstance(DateFormat.SHORT).format(l));

		// samples on training
		java.util.Date date = new java.util.Date();
		System.out.println(date);

		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		System.out.println(sqlDate);

		java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(date.getTime());
		System.out.println(sqlTimestamp);
	}
}

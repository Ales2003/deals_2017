package ru.mail.ales2003.deals2017.webapp;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.mail.ales2003.deals2017.datamodel.Contract;
import ru.mail.ales2003.deals2017.services.IContractService;

public class DateTest {
	public static void main(String[] args) {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("web-context.xml");

		IContractService service = context.getBean(IContractService.class);
		
		
		Contract c = new Contract();
		Timestamp t = new Timestamp(new Date().getTime());
		System.out.println(t);
		c.setСreated(t);
		service.save(c);
		Contract d = service.get(c.getId());
		Timestamp l = d.getСreated();
		System.out.println("Полная форма");
		System.out.println(l);
		System.out.println("Короткая форма");
		System.out.println(DateFormat.getDateInstance(DateFormat.SHORT).format(l));
	}
}

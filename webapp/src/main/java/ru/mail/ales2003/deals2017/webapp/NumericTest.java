package ru.mail.ales2003.deals2017.webapp;

import java.math.BigDecimal;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.mail.ales2003.deals2017.datamodel.Item;
import ru.mail.ales2003.deals2017.services.IItemService;

public class NumericTest {
	public static void main(String[] args) {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("web-context.xml");

		IItemService service = context.getBean(IItemService.class);
		
		Item item = new Item();
		item.setName("Table");
		item.setDescription("very good & nice table for relax");
		item.setBasicPrice(BigDecimal.valueOf(102.54));
		service.save(item);
		
		Item itemNew = service.get(item.getId());
		System.out.println(item);
		System.out.println(itemNew);
		
		BigDecimal l = item.getBasicPrice().add(BigDecimal.valueOf(10.2525));
		System.out.println( l);
		item.setBasicPrice(l);
		service.save(item);
		itemNew = service.get(item.getId());
	}
}

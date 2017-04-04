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
		System.out.println("=======Item before saving in DB");
		System.out.println(item);
		System.out.println("=======Item from DB after saving: item before saving equals item from DB");
		System.out.println(itemNew);
		
		BigDecimal bD = item.getBasicPrice();
		System.out.println( bD);
		bD =bD.add(BigDecimal.valueOf(10.2525));
		System.out.println( bD);
		item.setBasicPrice(bD);
		service.save(item);
		System.out.println("=======Item before saving in DB: field price was changed with 4 symbols after coma");
		System.out.println(item);
		itemNew = service.get(item.getId());
		System.out.println("=======Item from DB after saving: field price became with 2 symbols after coma how NUMERIC requires");
		System.out.println(itemNew);
	}
}

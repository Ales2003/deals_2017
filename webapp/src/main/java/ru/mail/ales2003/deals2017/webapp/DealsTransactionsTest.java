package ru.mail.ales2003.deals2017.webapp;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.mail.ales2003.deals2017.datamodel.Customer;
import ru.mail.ales2003.deals2017.services.ICustomerService;

public class DealsTransactionsTest {

	public static void main(String[] args) {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("web-context.xml");
		ICustomerService customerService = context.getBean(ICustomerService.class);
		
		/*//before add @Transactional over "CustomerServiceImpl.saveMultiple"
		Customer customer1 = new Customer();
		customer1.setFirstName("Customer1");
		customer1.setManagerId(1);
		customer1.setCustomerGroupId(1);
		
		Customer customer2 = new Customer();
		customer2.setFirstName("Customer2");
		
		customerService.saveMultiple(customer1, customer2);*/
		
		////before add @Transactional over "saveMultiple"
		Customer customer1 = new Customer();
		customer1.setFirstName("Customer1");
		customer1.setManagerId(1);
		customer1.setCustomerGroupId(58);
		
		Customer customer2 = new Customer();
		customer2.setFirstName("Customer2");
		customer2.setManagerId(2);
		customer2.setCustomerGroupId(58);
		
		customerService.saveMultiple(customer1, customer2);
		
		
		
		
		
		
	}
}

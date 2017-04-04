package ru.mail.ales2003.deals2017.webapp;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.mail.ales2003.deals2017.datamodel.Customer;
import ru.mail.ales2003.deals2017.services.ICustomerService;

public class WithJdbcTemplateServicesTest {
	
	public static void main(String[] args) {

		ClassPathXmlApplicationContext context = 
				new ClassPathXmlApplicationContext("web-context.xml");
		
		ICustomerService service = context.getBean(ICustomerService.class);
		
		System.out.println(service);
		
		Customer customer = new Customer();
		customer.setFirstName("new1 Customer");
        customer.setPatronymic("Customerovich");
        customer.setLastName("Customerov");
        customer.setCompanyName(null);
        customer.setAddress("Grodno");
        customer.setPhoneNumber("123345");
        customer.setCustomerGroupId(58);
        customer.setManagerId(1);
		service.save(customer);

        System.out.println("Saved customer:" + customer);

        System.out.println(service.get(customer.getId()));
        service.delete(customer.getId());
        System.out.println(service.get(customer.getId()));
        Customer c9;
        c9 = service.get(9);
        System.out.println("c9: "+c9);
        c9.setCompanyName("any company");
        service.save(c9);
        System.out.println("c9: "+c9);
        
        Customer customer2 = new Customer();
		customer2.setFirstName("new1 Customer");
        customer2.setPatronymic("Customerovich");
        customer2.setLastName("Customerov");
        customer2.setCompanyName(null);
        customer2.setAddress("Grodno");
        customer2.setPhoneNumber("123345");
        customer2.setCustomerGroupId(58);
        customer2.setManagerId(1);
		service.save(customer2);
		System.out.println(customer2);
		service.delete(customer2.getId());
		System.out.println(service.get(customer2.getId()));
		
		
		
		
		
		
	}

}

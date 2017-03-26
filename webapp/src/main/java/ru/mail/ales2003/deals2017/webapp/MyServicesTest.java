package ru.mail.ales2003.deals2017.webapp;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.mail.ales2003.deals2017.dao.impl.db.IItemDao;
import ru.mail.ales2003.deals2017.dao.impl.db.impl.ItemDaoImpl;
import ru.mail.ales2003.deals2017.services.IItemService;
import ru.mail.ales2003.deals2017.services.impl.ItemServiceImpl;

public class MyServicesTest {

	public static void main(String[] args) {

		/*
		 * контекст приложения ClassPathXmlApplicationContext загружает
		 * определения компонентов из XML-файла, расположенного в библиотеке
		 * классов (classpath), и обрабатывает файлы с определениями контекстов
		 * как ресурсы
		 */

		ClassPathXmlApplicationContext context = 
				new ClassPathXmlApplicationContext("web-context.xml");
		System.out.println(context);

		System.out.println("======Тестируем несколько методов context====");
		// вытягиваем бин из контекста и печатаем его
		IItemDao item = context.getBean(IItemDao.class);
		System.out.println(item);
		// печатаем имя класса
		System.out.println(item.getClass());
		//печатаем объект Item
		System.out.println(item.get(1));
		
		// !!! когда имплементаций несколько, контекст не отдаст бин по
		// интерфейсу, поэтому должен указываться класс, как здесь:
		IItemDao impl = context.getBean(ItemDaoImpl.class);
		System.out.println(impl);
		// какой-то тустринг, хочется лучше
		impl.toString();

		// интерфейс IItemDao не является бином, поэтому контекст его не
		// содержит
		System.out.println(context.containsBeanDefinition("iItemDao"));

		// а класс ItemDao является бином, поэтому контекст словил
		System.out.println(context.containsBeanDefinition("itemDaoImpl"));

		System.out.println("======Получаем массив бинов и распечатывем их имена====");
	
		String[] beanDefinitionNames = context.getBeanDefinitionNames();
		System.out.println("Бобы в банке:");
		for (String beanName : beanDefinitionNames) {
			System.out.println(beanName);
		}

		System.out.println("======Тестируем app.properties====");
		IItemService itemService = context.getBean(ItemServiceImpl.class);
		
		
		
	}

}

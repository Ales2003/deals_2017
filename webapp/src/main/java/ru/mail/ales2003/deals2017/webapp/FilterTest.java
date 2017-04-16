package ru.mail.ales2003.deals2017.webapp;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.mail.ales2003.deals2017.dao.api.custom.entities.ItemVariantBasicInfo;
import ru.mail.ales2003.deals2017.dao.api.filters.PaginationParams;
import ru.mail.ales2003.deals2017.dao.api.filters.SortingParams;
import ru.mail.ales2003.deals2017.dao.db.filters.impl.ItemVariantBasicInfoFilter;
import ru.mail.ales2003.deals2017.services.IItemVariantService;

public class FilterTest {
	public static void main(String[] args) {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("web-context.xml");
		IItemVariantService service = context.getBean(IItemVariantService.class);
		
		ItemVariantBasicInfoFilter filter = new ItemVariantBasicInfoFilter();
		String itemVariantDescription = "good";  
		String itemVariantName = "Table";
		BigDecimal itemVariantPrice = new BigDecimal("105.00");
		
		PaginationParams paginationParams = new PaginationParams();
		
		SortingParams sortingParams =new SortingParams();
		
		filter.setItemVariantDescription(itemVariantDescription);
		filter.setItemVariantName(itemVariantName);
		filter.setItemVariantPrice(itemVariantPrice);
		filter.setPaginationParams(paginationParams);
		filter.setSortingParams(sortingParams);
		filter.filterInitialize();

		System.out.println("filter:" + filter);
		List<ItemVariantBasicInfo> listAll = service.getBasicInfoForEach();
		for (ItemVariantBasicInfo itemVariantBasicInfo : listAll) {
			System.out.println(  itemVariantBasicInfo);
		}
		List<ItemVariantBasicInfo> list = service.getFilteredBasicInfo(filter);
		if (list == null){
			System.out.println("EMPTY");
		} else {
			for (ItemVariantBasicInfo itemVariantBasicInfo : list) {
				System.out.println(  itemVariantBasicInfo);
			}
		}
	}
}

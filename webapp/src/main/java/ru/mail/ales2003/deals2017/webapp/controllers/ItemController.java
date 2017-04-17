package ru.mail.ales2003.deals2017.webapp.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.mail.ales2003.deals2017.datamodel.Item;
import ru.mail.ales2003.deals2017.services.IItemService;
import ru.mail.ales2003.deals2017.webapp.models.ItemModel;

@RestController
@RequestMapping("/items")
public class ItemController {

	@Inject
	private IItemService itemService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll(@RequestParam(required = false) String name) {
		List<Item> allItems;
		
			allItems = itemService.getAll();
		
		
		List<ItemModel> convertedItems = new ArrayList<>();
		for (Item item : allItems) {
			convertedItems.add(entity2model(item));
		}

		return new ResponseEntity<List<ItemModel>>(convertedItems, HttpStatus.OK);
	}

	private ItemModel entity2model(Item item) {
		ItemModel itemModel = new ItemModel();
		itemModel.setName(item.getName());
		itemModel.setDescription(item.getDescription());
		return itemModel;
	}
}

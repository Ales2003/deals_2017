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

import ru.mail.ales2003.deals2017.dao.api.custom.entities.ItemVariantBasicInfo;
import ru.mail.ales2003.deals2017.dao.api.filters.IItemVariantFilter;
import ru.mail.ales2003.deals2017.dao.db.filters.impl.ItemVariantBasicInfoFilter;
import ru.mail.ales2003.deals2017.services.IItemVariantService;
import ru.mail.ales2003.deals2017.webapp.models.ItemVariantBasicInfoModel;

@RestController
@RequestMapping("/itemvariant")
public class ItemVariantBasicInfoController {

	@Inject
	private IItemVariantService itemVariantService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll(@RequestParam(required = false) String name) {
		List<ItemVariantBasicInfo> allBasicInfos;
		if (name == null) {
			allBasicInfos = itemVariantService.getBasicInfoForEach();
		} else {
			IItemVariantFilter basicInfoFilter = new ItemVariantBasicInfoFilter();

			try {
				
				basicInfoFilter.setItemVariantName(name);
				basicInfoFilter.filterInitialize();
			} catch (IllegalArgumentException e) {
				String msg = String.format("Name [%s] is not supported. Please use one of: %s", name,
						"Table, Chea, Window, Door");
				return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
			}
			allBasicInfos = itemVariantService.getFilteredBasicInfo(basicInfoFilter);
		}
		// return new ResponseEntity<List<ItemVariantBasicInfo>>(allBasicInfos,
		// HttpStatus.OK);

		List<ItemVariantBasicInfoModel> convertedBasicInfos = new ArrayList<>();
		for (ItemVariantBasicInfo info : allBasicInfos) {
			convertedBasicInfos.add(entity2model(info));
		}

		return new ResponseEntity<List<ItemVariantBasicInfoModel>>(convertedBasicInfos, HttpStatus.OK);
	}

	private ItemVariantBasicInfoModel entity2model(ItemVariantBasicInfo basicInfo) {
		ItemVariantBasicInfoModel basicInfokModel = new ItemVariantBasicInfoModel();
		basicInfokModel.setItemName(basicInfo.getItemName());
		basicInfokModel.setItemDescription(basicInfo.getItemDescription());
		basicInfokModel.setItemVariantPrice(basicInfo.getItemVariantPrice().toString());
		return basicInfokModel;
	}
}

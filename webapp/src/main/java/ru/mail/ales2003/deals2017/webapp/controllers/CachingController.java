package ru.mail.ales2003.deals2017.webapp.controllers;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.mail.ales2003.deals2017.services.impl.QueryDataPersistKeeper;

@RestController
@RequestMapping("/cache")
public class CachingController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CachingController.class);

	@Inject
	private ApplicationContext context;

	@RequestMapping(value = "/save", method = RequestMethod.HEAD)
	public ResponseEntity<?> toPersistendFile() {

		// Direct implementation of the method
		QueryDataPersistKeeper keeper = context.getBean(QueryDataPersistKeeper.class);

		try {
			keeper.write();
		} catch (Exception e) {
			String msg = String.format(e.getMessage());
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		String msg = String.format("%s is succefull persistend saved.", "CACHE_ITEM_COMMON_INFO");
		LOGGER.info(msg);
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}

	@RequestMapping(value = "/recover", method = RequestMethod.HEAD)
	public ResponseEntity<?> fromPersistendFile() {

		// Direct implementation of the method
		QueryDataPersistKeeper keeper = context.getBean(QueryDataPersistKeeper.class);

		try {
			keeper.read();
		} catch (Exception e) {
			String msg = String.format(e.getMessage());
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		String msg = String.format("%s is succefull recovered from persistend storage.", "CACHE_ITEM_COMMON_INFO");
		LOGGER.info(msg);
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}

}

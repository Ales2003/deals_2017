package ru.mail.ales2003.deals2017.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogLevelSample {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerGroupServiceImpl.class);

	public static void main(String[] args) {

		LOGGER.debug("debug");
		LOGGER.info("info");
		LOGGER.warn("warn");
		LOGGER.error("error", new Exception());

	}
}

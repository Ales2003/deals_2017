package ru.mail.ales2003.deals2017.webapp.translate;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class StaticTranslator {

	private static final Logger LOGGER = LoggerFactory.getLogger(StaticTranslator.class);

	/**
	 * @param name
	 *            - variable to translate
	 * @param locale
	 * @return translated variable
	 */
	public static String translate(String key, Locale locale) {
		LOGGER.info("Getting key={}, locale = {}", key, locale);
		if (key == null) {
			return null;
		}
		if (locale == null) {
			locale = new Locale("en_EN");
		}
		PropertyResourceBundle pr = null;
		String translated = null;
		LOGGER.info("Start translate word ={} used locale ={}", translated, locale);
		try {
			pr = (PropertyResourceBundle) PropertyResourceBundle
					.getBundle("ru.mail.ales2003.deals2017.webapp.translate.entitieNames", locale);
			if (pr == null) {
				throw new MissingResourceException("Property file not found!",
						"ru.mail.ales2003.deals2017.webapp.translate.entitieNames", key);
			}
			LOGGER.info("Continue translate key = {}, word ={} used locale ={}", key, translated, locale);
			translated = pr.getString(key);
			LOGGER.info("Continue translate key = {}, word ={} used locale ={}", key, translated, locale);
			// Perhaps extra - it works without it - he starts using the key in
			// the absence of a pair.

			if (translated == null)
				throw new MissingResourceException("Key not found!",
						"ru.mail.ales2003.deals2017.webapp.translate.entitieNames", key);
		} catch (MissingResourceException e) {
			LOGGER.error("{}, className = {}, key = {}.", e.getMessage(), e.getClassName(), e.getKey());
			return key;
		}
		LOGGER.info("Transladed word ={} used locale ={}", translated, locale);
		return translated;
	}

}

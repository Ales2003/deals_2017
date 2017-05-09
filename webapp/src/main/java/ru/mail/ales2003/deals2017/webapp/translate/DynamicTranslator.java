package ru.mail.ales2003.deals2017.webapp.translate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.inject.Inject;

import ru.mail.ales2003.deals2017.datamodel.I18N;
import ru.mail.ales2003.deals2017.datamodel.Language;
import ru.mail.ales2003.deals2017.services.II18NService;
import ru.mail.ales2003.deals2017.services.impl.I18NServiceImpl;

public class DynamicTranslator {
	public static void main(String[] args) {
		DynamicTranslator d = new DynamicTranslator();
		d.refreshDictonary();
System.out.println(dictonary.size());
	}

	@Inject
	private II18NService i18nService;

	private Locale locale;
	private String key;
	private String valueTranslated;

	private HashMap<Language, String> wordsLine;

	private static HashMap<String, HashMap<Language, String>> dictonary;

	public void refreshDictonary() {
		fillDictonary();
	}

	private void fillDictonary() {

		II18NService i = new I18NServiceImpl();
		dictonary = new HashMap<>();

		Set<String> keys = new HashSet<>();
		ArrayList<I18N> i18ns = (ArrayList<I18N>) i.getAll();
		for (I18N i18n : i18ns) {
			String keyword = i18n.getKeyword();
			keys.add(keyword);
		}
		for (String key : keys) {
			HashMap<Language, String> langValue = new HashMap<>();
			for (I18N i18n : i18ns) {
				if (i18n.getKeyword().equals(key)) {
					Language l = i18n.getLanguage();
					String v = i18n.getValue();
					langValue.put(l, v);
				}
			}
			dictonary.put(key, langValue);
		}
	}

	public static String translate(String key, Locale locale) {
		if (key == null) {
			return null;
		}
		if (locale == null) {
			return key;
		}
		Language lang = getLanguageFromLocale(locale);

		return key;

	}

	private static Language getLanguageFromLocale(Locale locale) {

		Language language;
		switch (locale.toString()) {
		case "ru_RU":
			language = Language.RU;
			break;

		default:
			return Language.EN;
		}

		return language;
	}

}

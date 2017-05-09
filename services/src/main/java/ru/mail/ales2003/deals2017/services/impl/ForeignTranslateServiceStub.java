package ru.mail.ales2003.deals2017.services.impl;

import org.springframework.stereotype.Component;

import ru.mail.ales2003.deals2017.datamodel.Language;

@Component
public class ForeignTranslateServiceStub {

	private static final Language ENG = Language.EN;

	// RU, BY, EN, DE, PL, FR

	private static final String RU_TRANSLATE = "\u043F\u0435\u0440\u0435\u0432\u043E\u0434 \u043D\u0430 \u0440\u0443\u0441\u0441\u043A\u0438\u0439 \u044F\u0437\u044B\u043A";
	private static final String BY_TRANSLATE = "\u043F\u0435\u0440\u0430\u043A\u043B\u0430\u0434 \u043D\u0430 \u0431\u0435\u043B\u0430\u0440\u0443\u0441\u043A\u0443\u044E \u043C\u043E\u0432\u0443";
	// private static final String EN_TRANSLATE = "";
	private static final String DE_TRANSLATE = "Übersetzung ins Deutsche";
	private static final String PL_TRANSLATE = "przet\u0142umaczone na j\u0119zyk polski";
	private static final String FR_TRANSLATE = "traduction en français";

	public static String translateFromEnglish(Language toLanguage, String fromWord) {

		String translatedWord;
		switch (toLanguage) {
		case RU:
			translatedWord = RU_TRANSLATE;
			break;
		case BY:
			translatedWord = BY_TRANSLATE;
			break;
		case DE:
			translatedWord = DE_TRANSLATE;
			break;
		case PL:
			translatedWord = PL_TRANSLATE;
			break;
		case FR:
			translatedWord = FR_TRANSLATE;
			break;
		default:
			return fromWord;
		}

		return String.format("%s from word %s", translatedWord, fromWord);
	}

}

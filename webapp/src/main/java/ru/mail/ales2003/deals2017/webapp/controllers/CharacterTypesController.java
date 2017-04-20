package ru.mail.ales2003.deals2017.webapp.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.mail.ales2003.deals2017.datamodel.CharacterType;
import ru.mail.ales2003.deals2017.services.ICharacterTypeService;
import ru.mail.ales2003.deals2017.webapp.models.CharacterTypeModel;
import ru.mail.ales2003.deals2017.webapp.translate.Translator;

@RestController
@RequestMapping("/charactertypes")
public class CharacterTypesController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CharacterTypesController.class);

	// this variable need to get his value instead hard but dynamically - from a
	// request header.
	Locale locale = new Locale("en_US");

	@Inject
	private Translator translator;

	@Inject
	private ICharacterTypeService service;

	/**
	 * @return List&ltCharacterTypeModel&gt convertedEntitys
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll() {
		List<CharacterType> allEntitys;
		try {
			allEntitys = service.getAll();
		} catch (EmptyResultDataAccessException e) {
			String msg = String.format("[%s]. Store returns incorrect entity count.", CharacterType.class);
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (allEntitys.isEmpty()) {
			String msg = String.format("An entity store of class [%s] is empty", CharacterType.class);
			return new ResponseEntity<String>(msg, HttpStatus.OK);
		}
		List<CharacterTypeModel> convertedEntitys = new ArrayList<>();
		for (CharacterType entity : allEntitys) {
			convertedEntitys.add(entity2model(entity));
		}
		//System.out.println(Locale.getDefault());
		return new ResponseEntity<List<CharacterTypeModel>>(convertedEntitys, HttpStatus.OK);
	}

	/**
	 * @param entity
	 *            - the entity of the class CharacterType
	 * 
	 * @return CharacterTypeModel entityModel converted from entity
	 */
	private CharacterTypeModel entity2model(CharacterType entity) {
		CharacterTypeModel entityModel = new CharacterTypeModel();
		entityModel.setId(entity.getId());
		entityModel.setName(entity.getName() == null ? null : translator.translate(entity.getName().name(), locale));
		return entityModel;
	}

	/**
	 * @param name
	 *            - variable to translate
	 * @param locale
	 * @return translated variable
	 */
	/*
	 * private String translate(String key, Locale locale) {
	 * PropertyResourceBundle pr = null; String translated; try { pr =
	 * (PropertyResourceBundle) PropertyResourceBundle .getBundle(
	 * "ru.mail.ales2003.deals2017.webapp.controllers.i18n.entitieNames",
	 * locale); if (pr == null) throw new
	 * MissingResourceException("Property file not found!",
	 * "ru.mail.ales2003.deals2017.webapp.controllers.i18n.entitieNames", key);
	 * translated = pr.getString(key); // Perhaps extra - it works without it -
	 * he starts using the key in // the absence of a pair. if (translated ==
	 * null) throw new MissingResourceException("Key not found!",
	 * "ru.mail.ales2003.deals2017.webapp.controllers.i18n.entitieNames", key);
	 * } catch (MissingResourceException e) {
	 * LOGGER.error("{}, className = {}, key = {}.", e.getMessage(),
	 * e.getClassName(), e.getKey()); return key; } return translated; }
	 */
}

package ru.mail.ales2003.deals2017.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ru.mail.ales2003.deals2017.dao.api.IUserAuthDao;
import ru.mail.ales2003.deals2017.datamodel.UserAuth;
import ru.mail.ales2003.deals2017.services.IUserAuthService;
import ru.mail.ales2003.deals2017.services.servicesexceptions.DuplicationKeyInformationException;

@Service
public class UserAuthServiceImpl implements IUserAuthService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthServiceImpl.class);

	@Inject
	private IUserAuthDao userAuthDao;

	private String className = UserAuth.class.getSimpleName();

	private static Set<String> loginSet = new HashSet<>();

	@Override
	public UserAuth get(Integer id) {
		if (userAuthDao.get(id) == null) {
			String errMsg = String.format("[%s] entity with id = [%s] don't exist in storage", className, id);
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg);
		} else {
			UserAuth entity = userAuthDao.get(id);
			LOGGER.info("Read one {} entity: {}", className, entity.toString());
			return entity;
		}
	}

	@Override
	public List<UserAuth> getAll() {
		LOGGER.info("{} entities storage returns {} entities.", className, userAuthDao.getAll().size());
		LOGGER.info("Read all {} entities:", className);
		for (UserAuth cT : userAuthDao.getAll()) {
			LOGGER.info("{} entity = {}", className, cT.toString());
		}
		return userAuthDao.getAll();
	}

	@Override
	public void save(UserAuth entity) {
		if (entity == null) {
			LOGGER.error("Error: as the {} entity was sent a null reference", className);
			return;
		} else if (entity.getId() == null) {

			LOGGER.info("Refresh of logins set of {}.", className);
			refreshLoginSet();

			LOGGER.info("Check for existence such a login = [{}] in storage.", entity.getLogin());
			if (isLoginExist(entity)) {
				String errMsg = String.format(
						"You want to insert as login: [%s]. But such exist already. Please try another one.",
						entity.getLogin());
				LOGGER.error("Error: Warning: {}", errMsg);
				throw new DuplicationKeyInformationException(errMsg);
			}

			userAuthDao.insert(entity);
			LOGGER.info("Inserted new {} entity: {}", className, entity.toString());
		} else {
			userAuthDao.update(entity);
			LOGGER.info("Updated one {} entity: {}", className, entity.toString());
		}

	}

	@Override
	public void saveMultiple(UserAuth... entityArray) {
		for (UserAuth entity : entityArray) {
			LOGGER.info("Inserted new {} entity from array: {}", className, entity.toString());
			save(entity);
		}
		LOGGER.info("{} entities from array were inserted", className);

	}

	@Override
	public void delete(Integer id) {
		if (id == null) {
			LOGGER.error("Error: as the id was sent a null reference");
			return;
		} else {
			userAuthDao.delete(id);
			LOGGER.info("Deleted {} entity by id: {}", className, id);
		}

	}

	@Override
	public UserAuth getByLogin(String login) {
		if (userAuthDao.getByLogin(login) == null) {
			String errMsg = String.format("[%s] entity with login = [%s] don't exist in storage", className, login);
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg);
		} else {
			UserAuth entity = userAuthDao.getByLogin(login);
			LOGGER.info("Read one {} entity: {}", className, entity.toString());
			return entity;
		}
	}

	@Override
	public UserAuth getByManagerId(Integer managerId) {
		if (userAuthDao.getByManagerId(managerId) == null) {
			String errMsg = String.format("[%s] entity with managerOrCustomerId = [%s] don't exist in storage",
					className, managerId);
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg);
		} else {
			UserAuth entity = userAuthDao.getByManagerId(managerId);
			LOGGER.info("Read one {} entity: {}", className, entity.toString());
			return entity;
		}
	}

	@Override
	public UserAuth getByCustomerId(Integer customerId) {
		if (userAuthDao.getByCustomerId(customerId) == null) {
			String errMsg = String.format("[%s] entity with managerOrCustomerId = [%s] don't exist in storage",
					className, customerId);
			LOGGER.error("Error: {}", errMsg);
			throw new IllegalArgumentException(errMsg);
		} else {
			UserAuth entity = userAuthDao.getByCustomerId(customerId);
			LOGGER.info("Read one {} entity: {}", className, entity.toString());
			return entity;
		}
	}

	// Methods to avoid duplication of logins
	// LOGGING
	// @Override
	private void refreshLoginSet() {
		clearLoginsSet();
		getAllLogins();
	}

	// LOGGING
	@Override
	public boolean isLoginExist(UserAuth entity) {
		Boolean isExist = false;
		String login = entity.getLogin();
		if (isExist = loginSet.contains(login)) {
			return isExist;
		}
		return isExist;
	}

	@Override
	public Set<String> getAllLogins() {
		// Set<String> lSet = new HashSet<>();
		LOGGER.info("{} entities storage returns {} logins.", className, userAuthDao.getAll().size());
		LOGGER.info("Read all {} entities:", className);
		for (UserAuth cT : userAuthDao.getAll()) {
			String login = cT.getLogin();
			LOGGER.info("{} login = {}", className, login);
			loginSet.add(login);
			LOGGER.info("{} login = {} added to HashSet", className, login);
		}
		return loginSet;
	}

	// LOGGING
	// @Override
	private void clearLoginsSet() {
		loginSet.clear();
	}

}

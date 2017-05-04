package ru.mail.ales2003.deals2017.services;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import ru.mail.ales2003.deals2017.datamodel.UserAuth;

public interface IUserAuthService {
	/**
	 * @param id
	 * @return UserAuth entity
	 */
	UserAuth get(Integer id);

	/**
	 * @return List&ltUserAuth&gt entities
	 */
	List<UserAuth> getAll();

	/**
	 * @param UserAuth
	 *            entity
	 */
	@Transactional
	void save(UserAuth entity);

	/**
	 * @param UserAuth
	 *            entityArray
	 */
	@Transactional
	void saveMultiple(UserAuth... entityArray);

	/**
	 * @param id
	 */
	@Transactional
	void delete(Integer id);

	/**
	 * @param login
	 * @return
	 */
	UserAuth getByLogin(String login);

	UserAuth getByManagerId(Integer managerId);

	/**
	 * @return Set&ltString&gt entitie logins
	 */

	// Methods to avoid duplication of logins

	Set<String> getAllLogins();

	// void refreshLoginSet();

	boolean isLoginExist(UserAuth entity);

	// void clearLoginsSet();

}

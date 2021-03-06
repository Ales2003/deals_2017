package ru.mail.ales2003.deals2017.dao.api;

import java.util.Set;

import ru.mail.ales2003.deals2017.datamodel.UserAuth;

public interface IUserAuthDao extends GenericDao<UserAuth, Integer> {

	UserAuth getByLogin(String login);

	UserAuth getByManagerId(Integer managerId);

	UserAuth getByCustomerId(Integer customerId);

	Set<String> getAllLogins();

}

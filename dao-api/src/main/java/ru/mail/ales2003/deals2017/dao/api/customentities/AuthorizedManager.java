package ru.mail.ales2003.deals2017.dao.api.customentities;

import ru.mail.ales2003.deals2017.datamodel.Manager;
import ru.mail.ales2003.deals2017.datamodel.UserAuth;

public class AuthorizedManager {

	private Integer managerId;

	private Manager manager;

	private UserAuth authData;

	public Integer getManagerId() {
		return managerId;
	}

	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public UserAuth getAuthData() {
		return authData;
	}

	public void setAuthData(UserAuth authData) {
		this.authData = authData;
	}

	@Override
	public String toString() {
		return "AuthorizedManager [managerId=" + managerId + ", manager=" + manager + ", authData=" + authData + "]";
	}

	
}

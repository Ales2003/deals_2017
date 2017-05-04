package ru.mail.ales2003.deals2017.webapp.models;

public class AuthorizedManagerModel {

	private Integer managerId;

	private ManagerModel managerModel;

	private UserAuthModel authDataModel;

	public Integer getManagerId() {
		return managerId;
	}

	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}

	public ManagerModel getManagerModel() {
		return managerModel;
	}

	public void setManagerModel(ManagerModel managerModel) {
		this.managerModel = managerModel;
	}

	public UserAuthModel getAuthDataModel() {
		return authDataModel;
	}

	public void setAuthDataModel(UserAuthModel authDataModel) {
		this.authDataModel = authDataModel;
	}
	
	
}

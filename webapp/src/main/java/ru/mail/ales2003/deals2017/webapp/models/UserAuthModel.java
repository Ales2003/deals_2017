package ru.mail.ales2003.deals2017.webapp.models;

public class UserAuthModel {

	private Integer id;
	private Integer inOwnTableId;
	private String role;
	private String login;
	private String password;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getInOwnTableId() {
		return inOwnTableId;
	}

	public void setInOwnTableId(Integer inOwnTableId) {
		this.inOwnTableId = inOwnTableId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

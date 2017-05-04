package ru.mail.ales2003.deals2017.services.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.mail.ales2003.deals2017.datamodel.Role;

@Component
@Scope(value = "request")
public class UserAuthStorage {

	private Integer id;

	private Role role;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserAuthStorage [id=" + id + ", role=" + role + "]";
	}

}

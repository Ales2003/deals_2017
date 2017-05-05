package ru.mail.ales2003.deals2017.dao.api.customentities;

import ru.mail.ales2003.deals2017.datamodel.Customer;
import ru.mail.ales2003.deals2017.datamodel.Manager;
import ru.mail.ales2003.deals2017.datamodel.UserAuth;

public class AuthorizedCustomer {

	private Integer customerId;

	private Customer customer;

	private UserAuth authData;

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public UserAuth getAuthData() {
		return authData;
	}

	public void setAuthData(UserAuth authData) {
		this.authData = authData;
	}

	@Override
	public String toString() {
		return "AuthorizedCustomer [customerId=" + customerId + ", customer=" + customer + ", authData=" + authData
				+ "]";
	}

	

	
}

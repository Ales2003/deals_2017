package ru.mail.ales2003.deals2017.webapp.models;

public class AuthorizedCustomerModel {

	private Integer customerId;

	private CustomerModel customerModel;

	private UserAuthModel authDataModel;

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public CustomerModel getCustomerModel() {
		return customerModel;
	}

	public void setCustomerModel(CustomerModel customerModel) {
		this.customerModel = customerModel;
	}

	public UserAuthModel getAuthDataModel() {
		return authDataModel;
	}

	public void setAuthDataModel(UserAuthModel authDataModel) {
		this.authDataModel = authDataModel;
	}

	@Override
	public String toString() {
		return "AuthorizedCustomerModel [customerId=" + customerId + ", customerModel=" + customerModel
				+ ", authDataModel=" + authDataModel + "]";
	}

	
	
	
}

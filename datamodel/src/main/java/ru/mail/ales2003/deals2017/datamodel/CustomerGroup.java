package ru.mail.ales2003.deals2017.datamodel;

public class CustomerGroup {

	private Integer id;

	private CustomerType name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CustomerType getName() {
		return name;
	}

	public void setName(CustomerType name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CustomerGroup [id=" + id + ", name=" + name + "]";
	}

}

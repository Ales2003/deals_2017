package ru.mail.ales2003.deals2017.datamodel;

public class Item {

	private Integer id;
	private String name;
	private String description;
	private Double basicPrice;
	private Integer customerId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getBasicPrice() {
		return basicPrice;
	}

	public void setBasicPrice(Double basicPrice) {
		this.basicPrice = basicPrice;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", description=" + description + ", basicPrice=" + basicPrice
				+ ", customerId=" + customerId + "]";
	}

}

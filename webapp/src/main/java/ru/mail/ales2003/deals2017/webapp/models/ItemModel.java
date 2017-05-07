package ru.mail.ales2003.deals2017.webapp.models;

import java.math.BigDecimal;

public class ItemModel {

	private Integer id;
	
	private String name;
	
	private String description;
	
	private BigDecimal basicPrice;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getBasicPrice() {
		return basicPrice;
	}

	public void setBasicPrice(BigDecimal basicPrice) {
		this.basicPrice = basicPrice;
	}

	@Override
	public String toString() {
		return "ItemModel [id=" + id + ", name=" + name + ", description=" + description + ", basicPrice=" + basicPrice
				+ "]";
	}

	
	
	
	
	
}

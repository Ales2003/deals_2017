package ru.mail.ales2003.deals2017.dao.api.custom.entities;

import ru.mail.ales2003.deals2017.datamodel.Attribute;

public class Detail {

	private Integer id;

	private Attribute name;

	private String value;

	private String measure;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Attribute getName() {
		return name;
	}

	public void setName(Attribute name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getMeasure() {
		return measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}

}

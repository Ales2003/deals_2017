package ru.mail.ales2003.deals2017.dao.api.custom.entities;

import ru.mail.ales2003.deals2017.datamodel.Attribute;
import ru.mail.ales2003.deals2017.datamodel.Measure;

public class ItemVariantDetail {

	private Integer id;

	private Attribute attributeName;

	private String attributeValue;

	private Measure attributeMeasure;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Attribute getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(Attribute attributeName) {
		this.attributeName = attributeName;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public Measure getAttributeMeasure() {
		return attributeMeasure;
	}

	public void setAttributeMeasure(Measure attributeMeasure) {
		this.attributeMeasure = attributeMeasure;
	}

	@Override
	public String toString() {
		return "ItemVariantDetail [id=" + id + ", attributeName=" + attributeName + ", attributeValue=" + attributeValue
				+ ", attributeMeasure=" + attributeMeasure + "]";
	}

}

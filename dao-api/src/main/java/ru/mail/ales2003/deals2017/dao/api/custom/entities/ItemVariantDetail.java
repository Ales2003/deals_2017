package ru.mail.ales2003.deals2017.dao.api.custom.entities;

import ru.mail.ales2003.deals2017.datamodel.Attribute;
import ru.mail.ales2003.deals2017.datamodel.Measure;

public class ItemVariantDetail {

	private Integer itemVariantId;

	private Attribute attributeName;

	private String attributeValue;

	private Measure attributeMeasure;

	public Integer getItemVariantId() {
		return itemVariantId;
	}

	public void setItemVariantId(Integer itemVariantId) {
		this.itemVariantId = itemVariantId;
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
		return "ItemVariantDetail [itemVariantId=" + itemVariantId + ", attributeName=" + attributeName
				+ ", attributeValue=" + attributeValue + ", attributeMeasure=" + attributeMeasure + "]";
	}

}

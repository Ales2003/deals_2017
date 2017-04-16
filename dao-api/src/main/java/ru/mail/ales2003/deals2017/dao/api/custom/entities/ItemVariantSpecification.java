package ru.mail.ales2003.deals2017.dao.api.custom.entities;

import java.util.List;

public class ItemVariantSpecification {

	private Integer id;

	private ItemVariantBasicInfo info;

	private List<ItemVariantDetail> details;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ItemVariantBasicInfo getInfo() {
		return info;
	}

	public void setInfo(ItemVariantBasicInfo info) {
		this.info = info;
	}

	public List<ItemVariantDetail> getDetails() {
		return details;
	}

	public void setDetails(List<ItemVariantDetail> details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "ItemVariantSpecification [id=" + id + ", info=" + info.toString() + ", details=" + details.toString() + "]";
	}

}

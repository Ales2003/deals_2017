package ru.mail.ales2003.deals2017.dao.api.custom.entities;

import java.math.BigDecimal;
import java.util.List;

public class ItemVariantSpecification {

	private Integer id;

	private ItemVariantBasicInfo inform;
	
	private List<ItemVariantDetail> details;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ItemVariantBasicInfo getInform() {
		return inform;
	}

	public void setInform(ItemVariantBasicInfo inform) {
		this.inform = inform;
	}

	public List<ItemVariantDetail> getDetails() {
		return details;
	}

	public void setDetails(List<ItemVariantDetail> details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "ItemVariantSpecification [id=" + id + ", inform=" + inform + ", details=" + details + "]";
	}

}

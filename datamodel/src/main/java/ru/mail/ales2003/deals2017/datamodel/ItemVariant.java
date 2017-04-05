package ru.mail.ales2003.deals2017.datamodel;

import java.math.BigDecimal;

public class ItemVariant {

	private Integer id;
	private Integer itemId;
	private BigDecimal variantPrice;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public BigDecimal getVariantPrice() {
		return variantPrice;
	}

	public void setVariantPrice(BigDecimal variantPrice) {
		this.variantPrice = variantPrice;
	}

	@Override
	public String toString() {
		return "ItemVariant [id=" + id + ", itemId=" + itemId + ", variantPrice=" + variantPrice + "]";
	}

}

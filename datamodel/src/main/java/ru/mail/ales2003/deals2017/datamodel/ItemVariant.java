package ru.mail.ales2003.deals2017.datamodel;

public class ItemVariant {

	private Integer id;
	private Integer itemId;

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

	@Override
	public String toString() {
		return "ItemVariant [id=" + id + ", itemId=" + itemId + "]";
	}

}

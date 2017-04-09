package ru.mail.ales2003.deals2017.datamodel;

public class CharacterType2ItemVariant {

	private Integer id;
	private Integer itemVariantId;
	private Attribute attribute;
	private String value;
	private Integer characterTypeId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemVariantId() {
		return itemVariantId;
	}

	public void setItemVariantId(Integer itemVariantId) {
		this.itemVariantId = itemVariantId;
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getCharacterTypeId() {
		return characterTypeId;
	}

	public void setCharacterTypeId(Integer characterTypeId) {
		this.characterTypeId = characterTypeId;
	}

	@Override
	public String toString() {
		return "CharacterType2ItemVariant [id=" + id + ", itemVariantId=" + itemVariantId + ", attribute=" + attribute
				+ ", value=" + value + ", characterTypeId=" + characterTypeId + "]";
	}

}

package ru.mail.ales2003.deals2017.datamodel;

public class CharacterType2ItemVariant {

	private Integer id;
	private Integer itemVariantId;
	private Attribute name;
	private Double value;
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

	public Attribute getName() {
		return name;
	}

	public void setName(Attribute name) {
		this.name = name;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
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
		return "CharacterType2Variant [id=" + id + ", itemVariantId=" + itemVariantId + ", name=" + name + ", value="
				+ value + ", characterTypeId=" + characterTypeId + "]";
	}

}

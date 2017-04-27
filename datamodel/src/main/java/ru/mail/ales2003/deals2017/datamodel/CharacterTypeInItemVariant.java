package ru.mail.ales2003.deals2017.datamodel;

public class CharacterTypeInItemVariant {

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

	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CharacterTypeInItemVariant [id=" + id + ", itemVariantId=" + itemVariantId + ", attribute=" + attribute
				+ ", value=" + value + ", characterTypeId=" + characterTypeId + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attribute == null) ? 0 : attribute.hashCode());
		result = prime * result + ((characterTypeId == null) ? 0 : characterTypeId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((itemVariantId == null) ? 0 : itemVariantId.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CharacterTypeInItemVariant other = (CharacterTypeInItemVariant) obj;
		if (attribute != other.attribute)
			return false;
		if (characterTypeId == null) {
			if (other.characterTypeId != null)
				return false;
		} else if (!characterTypeId.equals(other.characterTypeId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (itemVariantId == null) {
			if (other.itemVariantId != null)
				return false;
		} else if (!itemVariantId.equals(other.itemVariantId))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}

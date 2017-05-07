package ru.mail.ales2003.deals2017.datamodel;

public class CharacterType {

	private Integer id;
	private Measure name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Measure getName() {
		return name;
	}

	public void setName(Measure name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CharacterType [id=" + id + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CharacterType other = (CharacterType) obj;
		if (name != other.name)
			return false;
		return true;
	}

}

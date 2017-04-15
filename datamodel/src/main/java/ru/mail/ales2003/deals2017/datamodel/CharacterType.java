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


}

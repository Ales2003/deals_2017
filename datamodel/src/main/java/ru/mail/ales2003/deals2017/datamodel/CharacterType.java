package ru.mail.ales2003.deals2017.datamodel;

public class CharacterType {

	private Integer id;
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CharacterType [id=" + id + ", name=" + name + "]";
	}

}

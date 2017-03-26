package ru.mail.ales2003.deals2017.datamodel;

public class Manager {

	private Integer id;
	private String firstName;
	private String patronymic;
	private String lastName;
	private String position;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPatronymic() {
		return patronymic;
	}

	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return "Manager [id=" + id + ", firstName=" + firstName + ", patronymic=" + patronymic + ", lastName="
				+ lastName + ", position=" + position + "]";
	}

	
	
}

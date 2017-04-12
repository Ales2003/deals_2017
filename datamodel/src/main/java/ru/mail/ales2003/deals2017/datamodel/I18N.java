package ru.mail.ales2003.deals2017.datamodel;

public class I18N {

	private Integer id;
	private Table tableName;
	private Integer memberId;
	private Language language;
	private String value;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Table getTableName() {
		return tableName;
	}

	public void setTableName(Table tableName) {
		this.tableName = tableName;
	}

	

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "I18N [id=" + id + ", tableName=" + tableName + ", memberId=" + memberId + ", language=" + language
				+ ", value=" + value + "]";
	}

	

	
}

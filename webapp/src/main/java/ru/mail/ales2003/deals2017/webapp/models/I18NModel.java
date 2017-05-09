package ru.mail.ales2003.deals2017.webapp.models;

public class I18NModel {

	private Integer id;
	private String keyword;
	private String tableName;
	private Integer memberId;
	private String language;
	private String value;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
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
		return "I18NModel [id=" + id + ", keyword=" + keyword + ", tableName=" + tableName + ", memberId=" + memberId
				+ ", language=" + language + ", value=" + value + "]";
	}

}

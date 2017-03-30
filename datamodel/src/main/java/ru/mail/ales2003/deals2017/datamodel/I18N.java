package ru.mail.ales2003.deals2017.datamodel;

public class I18N {

	private Integer id;
	private Table table;
	private Integer entityId;
	private Language language;
	private String value;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public Integer getEntityId() {
		return entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
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
		return "I18N [id=" + id + ", table=" + table + ", entityId=" + entityId + ", language=" + language + ", value="
				+ value + "]";
	}

}

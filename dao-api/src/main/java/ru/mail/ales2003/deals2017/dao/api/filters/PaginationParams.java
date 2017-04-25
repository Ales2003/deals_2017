package ru.mail.ales2003.deals2017.dao.api.filters;

public class PaginationParams {
	
	private Integer limit;

	private Integer offset;

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PaginationParams [limit=" + limit + ", offset=" + offset + "]";
	}
	
	
}

package ru.mail.ales2003.deals2017.dao.api.filters;

public class SortingParams {

	private ItemColumnNamesForSortingParams sortColumn;

	private OrderDirectionForSortingParams sortOrder;

	/**
	 * @return the sortColumn
	 */
	public ItemColumnNamesForSortingParams getSortColumn() {
		return sortColumn;
	}

	/**
	 * @param sortColumn
	 *            the sortColumn to set
	 */
	public void setSortColumn(ItemColumnNamesForSortingParams sortColumn) {
		this.sortColumn = sortColumn;
	}

	/**
	 * @return the sortOrder
	 */
	public OrderDirectionForSortingParams getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder
	 *            the sortOrder to set
	 */
	public void setSortOrder(OrderDirectionForSortingParams sortOrder) {
		this.sortOrder = sortOrder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SortingParams [sortColumn=" + sortColumn + ", sortOrder=" + sortOrder + "]";
	}

}

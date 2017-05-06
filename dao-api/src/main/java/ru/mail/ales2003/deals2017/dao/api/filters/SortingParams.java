package ru.mail.ales2003.deals2017.dao.api.filters;

public class SortingParams {

	private ItemColumnNamesForSortingParams itemSortColumn;

	private ContractColumnNamesForSortingParams contractSortColumn;

	private OrderDirectionForSortingParams sortOrder;

	/**
	 * @return the sortColumn
	 */
	public ItemColumnNamesForSortingParams getSortColumn() {
		return itemSortColumn;
	}

	/**
	 * @param sortColumn
	 *            the sortColumn to set
	 */
	public void setSortColumn(ItemColumnNamesForSortingParams itemSortColumn) {
		this.itemSortColumn = itemSortColumn;
	}

	public ContractColumnNamesForSortingParams getContractSortColumn() {
		return contractSortColumn;
	}

	public void setContractSortColumn(ContractColumnNamesForSortingParams contractSortColumn) {
		this.contractSortColumn = contractSortColumn;
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

	@Override
	public String toString() {
		return "SortingParams [itemSortColumn=" + itemSortColumn + ", contractSortColumn=" + contractSortColumn
				+ ", sortOrder=" + sortOrder + "]";
	}

}

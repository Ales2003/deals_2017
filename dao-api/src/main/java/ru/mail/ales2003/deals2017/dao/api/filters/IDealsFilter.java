package ru.mail.ales2003.deals2017.dao.api.filters;

public interface IDealsFilter {

	
	 void filterInitialize ();
	
	/**
	 * @return the full SQL Query for JdbcTemplate
	 */
	String getFullSqlQuery();

	/**
	 * @return the queryParamsArray
	 */

	Object[] getQueryParamsArray();

	/**
	 * @param sortingParams
	 *            the sorting parameters to set
	 */
	void setSortingParams(SortingParams sortingParams);

	/**
	 * @param paginationParams
	 *            the pagination parameters to set
	 */
	void setPaginationParams(PaginationParams paginationParams);
}

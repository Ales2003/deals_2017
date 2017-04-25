package ru.mail.ales2003.deals2017.dao.db.filters.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.api.filters.IItemVariantFilter;
import ru.mail.ales2003.deals2017.dao.api.filters.PaginationParams;
import ru.mail.ales2003.deals2017.dao.api.filters.SortingParams;

@Repository
public class ItemVariantCommonInfoFilter implements IItemVariantFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemVariantCommonInfoFilter.class);

	// list of args as parameter for JdbcTemplate;
	private Object[] queryParamsArray;
	// full SQL as parameter for JdbcTemplate;
	private String fullSqlQuery;

	// Incoming parameters
	private String itemVariantName;

	private BigDecimal itemVariantPrice;

	private String itemVariantDescription;

	private SortingParams sortingParams;

	private PaginationParams paginationParams;

	// Method interface for an external client

	/**
	 * @return the full SQL Query for JdbcTemplate
	 */
	public String getFullSqlQuery() {
		return fullSqlQuery;
	}

	/**
	 * @return the queryParamsArray
	 */
	public Object[] getQueryParamsArray() {
		return queryParamsArray;
	}

	/**
	 * @param itemVariantName
	 *            the name of item variant to set
	 */
	public void setItemVariantName(String itemVariantName) {
		this.itemVariantName = itemVariantName;
	}

	/**
	 * @param itemVariantPrice
	 *            the price of item variant to set
	 */
	public void setItemVariantPrice(BigDecimal itemVariantPrice) {
		this.itemVariantPrice = itemVariantPrice;
	}

	/**
	 * @param itemVariantDescription
	 *            the the description of item variant to set
	 */
	public void setItemVariantDescription(String itemVariantDescription) {
		this.itemVariantDescription = itemVariantDescription;
	}

	/**
	 * @param sortingParams
	 *            the sorting parameters to set
	 */
	public void setSortingParams(SortingParams sortingParams) {
		this.sortingParams = sortingParams;
	}

	/**
	 * @param paginationParams
	 *            the pagination parameters to set
	 */
	public void setPaginationParams(PaginationParams paginationParams) {
		this.paginationParams = paginationParams;
	}

	// Internal parameters
	private List<Object> paramsList = new ArrayList<Object>();

	private List<String> sqlWhereFragmentsAndList = new ArrayList<String>();

	public void filterInitialize() {

		StringBuilder sqlWhereBuilder = new StringBuilder("");

		if (itemVariantName != null) {
			//sqlWhereFragmentsAndList.add("name = ? ");
			sqlWhereFragmentsAndList.add("name like ? ");
			paramsList.add(itemVariantName);
		}

		if (itemVariantPrice != null) {
			sqlWhereFragmentsAndList.add("variant_price = ? ");
			paramsList.add(itemVariantPrice);
		}

		if (itemVariantDescription != null) {
			//sqlWhereFragmentsAndList.add("description like ? ");
			sqlWhereFragmentsAndList.add("description like ? ");
			paramsList.add(itemVariantDescription);
		}

		if (!paramsList.isEmpty()) {
			sqlWhereBuilder.insert(0, "where ");
		}

		sqlWhereBuilder.append(concatAnds(sqlWhereFragmentsAndList));

		if (sortingParams != null) {
			String column = sortingParams.getSortColumn() != null ? column = sortingParams.getSortColumn().name()
					: null;
			String direction = sortingParams.getSortOrder() != null ? sortingParams.getSortOrder().name() : null;
			if (column != null) {
				if (direction == null) {
					sqlWhereBuilder.append("order by ? ");
					paramsList.add(column);
				} else {
					sqlWhereBuilder.append("order by ? ?");
					paramsList.add(column);
					paramsList.add(direction);
				}
			}
		}

		if (paginationParams != null) {
			Integer limit = paginationParams.getLimit();
			Integer offset = paginationParams.getOffset();
			if (limit != null) {
				sqlWhereBuilder.append("limit ? ");
				paramsList.add(limit);
				if (offset != null) {
					sqlWhereBuilder.append("offset ? ");
					paramsList.add(offset);
				}
			}
		}
		fullSqlQuery = String
				.format("select v.id as id, i.name as name, i.description as description, v.variant_price as price"
						+ " from item_variant as v left join item as i on i.id=v.item_id %s ", sqlWhereBuilder);
		System.out.println(fullSqlQuery.toString());
		LOGGER.info("execute sql:{}", fullSqlQuery);
		LOGGER.info("execute sql sqlWhereBuilder:{}", sqlWhereBuilder);

		queryParamsArray = paramsList.toArray();

	}

	private String concatAnds(List<String> sqlParts) {
		StringBuilder stringBuilder = new StringBuilder("");
		for (int i = 0; i < sqlParts.size(); i++) {
			String string = sqlParts.get(i);
			if (i != 0) {
				stringBuilder.append("AND ");
			}
			stringBuilder.append(string + " ");
		}
		return stringBuilder.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ItemVariantCommonInfoFilter [queryParamsArray=" + Arrays.toString(queryParamsArray) + ", fullSqlQuery="
				+ fullSqlQuery + ", itemVariantName=" + itemVariantName + ", itemVariantPrice=" + itemVariantPrice
				+ ", itemVariantDescription=" + itemVariantDescription + ", sortingParams=" + sortingParams
				+ ", paginationParams=" + paginationParams + ", paramsList=" + paramsList
				+ ", sqlWhereFragmentsAndList=" + sqlWhereFragmentsAndList + "]";
	}

}

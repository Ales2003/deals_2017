package ru.mail.ales2003.deals2017.dao.db.filters.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.api.filters.IContractFilter;
import ru.mail.ales2003.deals2017.dao.api.filters.PaginationParams;
import ru.mail.ales2003.deals2017.dao.api.filters.SortingParams;
import ru.mail.ales2003.deals2017.datamodel.ContractStatus;
import ru.mail.ales2003.deals2017.datamodel.CustomerType;
import ru.mail.ales2003.deals2017.datamodel.PayForm;
import ru.mail.ales2003.deals2017.datamodel.PayStatus;

@Repository
public class ContractCommonInfoFilter implements IContractFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContractCommonInfoFilter.class);

	// list of args as parameter for JdbcTemplate;
	private Object[] queryParamsArray;
	// full SQL as parameter for JdbcTemplate;
	private String fullSqlQuery;

	// Incoming parameters

	private Integer customerId;
	private String customerLastName;
	private String customerCompany;
	private CustomerType customerType;

	private Integer managerId;
	private String managerLastName;

	private Timestamp createdFROM;
	private Timestamp createdTO;
	private BigDecimal amountMIN;
	private BigDecimal amountMAX;
	private ContractStatus contractStatus;
	private PayForm payForm;
	private PayStatus payStatus;

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

	@Override
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;

	}

	@Override
	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;

	}

	@Override
	public void setCustomerCompany(String customerCompany) {
		this.customerCompany = customerCompany;

	}

	@Override
	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;

	}

	@Override
	public void setManagerId(Integer managerId) {
		this.managerId = managerId;

	}

	@Override
	public void setManagerLastName(String managerLastName) {
		this.managerLastName = managerLastName;

	}

	@Override
	public void setCreatedFROM(Timestamp createdFROM) {
		this.createdFROM = createdFROM;

	}

	@Override
	public void setCreatedTO(Timestamp createdTO) {
		this.createdTO = createdTO;

	}

	@Override
	public void setAmountMIN(BigDecimal amountMIN) {
		this.amountMIN = amountMIN;
	}

	@Override
	public void setAmountMAX(BigDecimal amountMAX) {
		this.amountMAX = amountMAX;

	}

	@Override
	public void setContractStatus(ContractStatus contractStatus) {
		this.contractStatus = contractStatus;

	}

	@Override
	public void setPayForm(PayForm payForm) {
		this.payForm = payForm;

	}

	@Override
	public void setPayStatus(PayStatus payStatus) {
		this.payStatus = payStatus;

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
		
		//!!!basically does not accept pseudonyms of columns (...as []) - only name, maximum [table_alias].[column_name]
		if (customerId != null) {
			//Here is accepted since the alias is the same as the column name
			sqlWhereFragmentsAndList.add("customer_id = ? ");
			// sqlWhereFragmentsAndList.add("name like ? ");
			paramsList.add(customerId);
		}

		if (customerLastName != null) {
			sqlWhereFragmentsAndList.add("customer.last_name = ? ");
			// sqlWhereFragmentsAndList.add("name like ? ");
			paramsList.add(customerLastName);
		}

		if (customerCompany != null) {
			sqlWhereFragmentsAndList.add("customer.company_name = ? ");
			paramsList.add(customerCompany);
		}

		if (customerType != null) {
			sqlWhereFragmentsAndList.add("customer_group.name = ? ");
			paramsList.add(customerType.name());
		}

		if (managerId != null) {
			sqlWhereFragmentsAndList.add("manager_id = ? ");
			// sqlWhereFragmentsAndList.add("description like ? ");
			paramsList.add(managerId);
		}

		if (managerLastName != null) {
			sqlWhereFragmentsAndList.add("manager.last_name = ? ");
			// sqlWhereFragmentsAndList.add("description like ? ");
			paramsList.add(managerLastName);
		}
		if (createdFROM != null) {
			sqlWhereFragmentsAndList.add("c.created >= ? ");
			// sqlWhereFragmentsAndList.add("description like ? ");
			paramsList.add(createdFROM);
		}
		if (createdTO != null) {
			sqlWhereFragmentsAndList.add("c.created <= ? ");
			// sqlWhereFragmentsAndList.add("description like ? ");
			paramsList.add(createdTO);
		}
		if (amountMIN != null) {
			sqlWhereFragmentsAndList.add("c.total_price >= ? ");
			// sqlWhereFragmentsAndList.add("description like ? ");
			paramsList.add(amountMIN);
		}
		if (amountMAX != null) {
			sqlWhereFragmentsAndList.add("c.total_price <= ? ");
			// sqlWhereFragmentsAndList.add("description like ? ");
			paramsList.add(amountMAX);
		}
		if (contractStatus != null) {
			sqlWhereFragmentsAndList.add("c.contract_status = ? ");
			// sqlWhereFragmentsAndList.add("description like ? ");
			paramsList.add(contractStatus.name());
		}
		if (payForm != null) {
			sqlWhereFragmentsAndList.add("c.pay_form = ? ");
			// sqlWhereFragmentsAndList.add("description like ? ");
			paramsList.add(payForm.name());
		}

		if (payStatus != null) {
			sqlWhereFragmentsAndList.add("c.pay_status = ? ");
			// sqlWhereFragmentsAndList.add("description like ? ");
			paramsList.add(payStatus.name());
		}

		if (!paramsList.isEmpty()) {
			sqlWhereBuilder.insert(0, "where ");
		}

		sqlWhereBuilder.append(concatAnds(sqlWhereFragmentsAndList));

		if (sortingParams != null) {
			String column = (sortingParams.getSortColumn() != null) ? sortingParams.getSortColumn().name().toLowerCase()
					: null;
			String direction = (sortingParams.getSortOrder() != null)
					? sortingParams.getSortOrder().name().toLowerCase() : null;
			if (column != null) {
				sqlWhereBuilder.append("order by ? ");
				paramsList.add(column);
				if (direction == "asc") {
					sqlWhereBuilder.append("ASC ");
					// paramsList.add(direction);
				} else if (direction == "desc") {
					sqlWhereBuilder.append("DESC ");
					// paramsList.add(direction);
				}
			}
			/*
			 * { if (direction == null) { sqlWhereBuilder.append("order by ? ");
			 * paramsList.add(column); }
			 * 
			 * else { sqlWhereBuilder.append("order by ? ?");
			 * paramsList.add(column); paramsList.add(direction); }
			 * 
			 * 
			 * }
			 * 
			 */
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
		fullSqlQuery = String.format(
				"select c.id as contract_id, c.created as creation_date,"
						+ " c.contract_status as status, c.pay_form as pay_form, c.pay_status as pay_status, c.total_price as total_amount,"
						+ " customer_group.name as customer_type, c.customer_id as customer_id,"
						+ " customer.last_name as customer_name, customer.company_name as customer_company,"
						+ " customer.manager_id, manager.last_name as manager_name from contract as c"
						+ " left join customer on c.customer_id=customer.id"
						+ " left join manager on customer.manager_id=manager.id"
						+ " left join customer_group on customer.customer_group_id=customer_group.id %s ",
				sqlWhereBuilder);
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

	@Override
	public String toString() {
		return "ContractCommonInfoFilter [queryParamsArray=" + Arrays.toString(queryParamsArray) + ", fullSqlQuery="
				+ fullSqlQuery + ", customerId=" + customerId + ", customerLastName=" + customerLastName
				+ ", customerCompany=" + customerCompany + ", customerType=" + customerType + ", managerId=" + managerId
				+ ", managerLastName=" + managerLastName + ", createdFROM=" + createdFROM + ", createdTO=" + createdTO
				+ ", amountMIN=" + amountMIN + ", amountMAX=" + amountMAX + ", contractStatus=" + contractStatus
				+ ", payForm=" + payForm + ", payStatus=" + payStatus + ", sortingParams=" + sortingParams
				+ ", paginationParams=" + paginationParams + ", paramsList=" + paramsList
				+ ", sqlWhereFragmentsAndList=" + sqlWhereFragmentsAndList + "]";
	}

}

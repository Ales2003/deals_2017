package ru.mail.ales2003.deals2017.webapp.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import ru.mail.ales2003.deals2017.dao.api.filters.ItemColumnNamesForSortingParams;
import ru.mail.ales2003.deals2017.dao.api.filters.OrderDirectionForSortingParams;
import ru.mail.ales2003.deals2017.datamodel.CustomerType;
import ru.mail.ales2003.deals2017.datamodel.Measure;
import ru.mail.ales2003.deals2017.datamodel.Role;

/**
 * @author Aliaksandr Vishneuski ales_2003@mail.ru
 *
 */
public class EnumArrayToMessageConvertor {

	/**
	 * @return a string representation of the enumsarray from class Measure.
	 */
	public static String measureArrayToMessage() {
		StringBuilder stringBuilder = new StringBuilder("");
		List<Measure> measures = new ArrayList<Measure>(Arrays.asList(Measure.values()));
		for (Measure m : measures) {
			stringBuilder.append("" + m + ", ");
		}
		// sorting
		measures.sort(new Comparator<Measure>() {
			@Override
			public int compare(Measure o1, Measure o2) {
				return o1.name().compareTo(o2.name());
			}
		});

		String result = String.format("%s", stringBuilder);
		result = result.substring(0, result.length() - 2);
		return result;
	}

	/**
	 * @return a string representation of the enumsarray from class Measure.
	 */
	public static String customerTypeArrayToMessage() {
		StringBuilder stringBuilder = new StringBuilder("");
		List<CustomerType> types = new ArrayList<CustomerType>(Arrays.asList(CustomerType.values()));
		for (CustomerType m : types) {
			stringBuilder.append("" + m + ", ");
		}
		// sorting
		types.sort(new Comparator<CustomerType>() {
			@Override
			public int compare(CustomerType o1, CustomerType o2) {
				return o1.name().compareTo(o2.name());
			}
		});

		String result = String.format("%s", stringBuilder);
		result = result.substring(0, result.length() - 2);
		return result;
	}

	/**
	 * @return a string representation of the enumsarray from class
	 *         ColumnNamesForSortingParams.
	 */
	public static String itemColumnNameEnumArrayToMessage() {
		StringBuilder stringBuilder = new StringBuilder("");
		List<ItemColumnNamesForSortingParams> params = new ArrayList<ItemColumnNamesForSortingParams>(
				Arrays.asList(ItemColumnNamesForSortingParams.values()));
		for (ItemColumnNamesForSortingParams p : params) {
			stringBuilder.append("" + p + ", ");
		}
		// sorting
		params.sort(new Comparator<ItemColumnNamesForSortingParams>() {
			@Override
			public int compare(ItemColumnNamesForSortingParams o1, ItemColumnNamesForSortingParams o2) {
				return o1.name().compareTo(o2.name());
			}
		});

		String result = String.format("%s", stringBuilder);
		result = result.substring(0, result.length() - 2);
		return result;
	}

	/**
	 * @return a string representation of the enumsarray from class
	 *         ColumnNamesForSortingParams.
	 */
	public static String orderDirectionEnumArrayToMessage() {
		StringBuilder stringBuilder = new StringBuilder("");
		List<OrderDirectionForSortingParams> params = new ArrayList<OrderDirectionForSortingParams>(
				Arrays.asList(OrderDirectionForSortingParams.values()));
		for (OrderDirectionForSortingParams p : params) {
			stringBuilder.append("" + p + ", ");
		}
		// sorting
		params.sort(new Comparator<OrderDirectionForSortingParams>() {
			@Override
			public int compare(OrderDirectionForSortingParams o1, OrderDirectionForSortingParams o2) {
				return o1.name().compareTo(o2.name());
			}
		});

		String result = String.format("%s", stringBuilder);
		result = result.substring(0, result.length() - 2);
		return result;
	}

	/**
	 * @return a string representation of the enumsarray from class Role.
	 */
	public static String roleArrayToMessage() {
		StringBuilder stringBuilder = new StringBuilder("");
		List<Role> roles = new ArrayList<Role>(Arrays.asList(Role.values()));
		for (Role p : roles) {
			stringBuilder.append("" + p + ", ");
		}
		// sorting
		roles.sort(new Comparator<Role>() {
			@Override
			public int compare(Role o1, Role o2) {
				return o1.name().compareTo(o2.name());
			}
		});

		String result = String.format("%s", stringBuilder);
		result = result.substring(0, result.length() - 2);
		return result;
	}

	/**
	 * @return a string representation of the enumsarray from class Role.
	 */
	public static String validRoleArrayToMessage(Set<Role> s) {
		StringBuilder stringBuilder = new StringBuilder("");
		List<Role> roles = new ArrayList<Role>();
		roles.addAll(s);
		for (Role p : roles) {
			stringBuilder.append("" + p + ", ");
		}
		// sorting
		roles.sort(new Comparator<Role>() {
			@Override
			public int compare(Role o1, Role o2) {
				return o1.name().compareTo(o2.name());
			}
		});

		String result = String.format("%s", stringBuilder);
		result = result.substring(0, result.length() - 2);
		return result;
	}

}

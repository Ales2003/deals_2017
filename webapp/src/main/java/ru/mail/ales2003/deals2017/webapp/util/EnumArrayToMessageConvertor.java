package ru.mail.ales2003.deals2017.webapp.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.mail.ales2003.deals2017.dao.api.filters.ColumnNamesForSortingParams;
import ru.mail.ales2003.deals2017.dao.api.filters.OrderDirectionForSortingParams;
import ru.mail.ales2003.deals2017.datamodel.Measure;

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
		String result = String.format("%s", stringBuilder);
		result = result.substring(0, result.length() - 2);
		return result;
	}

	/**
	 * @return a string representation of the enumsarray from class ColumnNamesForSortingParams.
	 */
	public static String itemColumnNameEnumArrayToMessage() {
		StringBuilder stringBuilder = new StringBuilder("");
		List<ColumnNamesForSortingParams> params = new ArrayList<ColumnNamesForSortingParams>(
				Arrays.asList(ColumnNamesForSortingParams.values()));
		for (ColumnNamesForSortingParams p : params) {
			stringBuilder.append("" + p + ", ");
		}
		String result = String.format("%s", stringBuilder);
		result = result.substring(0, result.length() - 2);
		return result;
	}

	/**
	 * @return a string representation of the enumsarray from class ColumnNamesForSortingParams.
	 */
	public static String orderDirectionEnumArrayToMessage() {
		StringBuilder stringBuilder = new StringBuilder("");
		List<OrderDirectionForSortingParams> params = new ArrayList<OrderDirectionForSortingParams>(
				Arrays.asList(OrderDirectionForSortingParams.values()));
		for (OrderDirectionForSortingParams p : params) {
			stringBuilder.append("" + p + ", ");
		}
		String result = String.format("%s", stringBuilder);
		result = result.substring(0, result.length() - 2);
		return result;
	}
	
}

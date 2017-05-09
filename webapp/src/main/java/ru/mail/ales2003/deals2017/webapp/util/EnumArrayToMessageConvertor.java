package ru.mail.ales2003.deals2017.webapp.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import ru.mail.ales2003.deals2017.dao.api.filters.ContractColumnNamesForSortingParams;
import ru.mail.ales2003.deals2017.dao.api.filters.ItemColumnNamesForSortingParams;
import ru.mail.ales2003.deals2017.dao.api.filters.OrderDirectionForSortingParams;
import ru.mail.ales2003.deals2017.datamodel.Attribute;
import ru.mail.ales2003.deals2017.datamodel.ContractStatus;
import ru.mail.ales2003.deals2017.datamodel.CustomerType;
import ru.mail.ales2003.deals2017.datamodel.Language;
import ru.mail.ales2003.deals2017.datamodel.Measure;
import ru.mail.ales2003.deals2017.datamodel.PayForm;
import ru.mail.ales2003.deals2017.datamodel.PayStatus;
import ru.mail.ales2003.deals2017.datamodel.Role;
import ru.mail.ales2003.deals2017.datamodel.Table;

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
	 * @return a string representation of the CustomerType from class
	 *         CustomerGroup.
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
	 * @return a string representation of the ContractStatus from class
	 *         Contract.
	 */
	public static String contractStatusArrayToMessage() {
		StringBuilder stringBuilder = new StringBuilder("");
		List<ContractStatus> statuses = new ArrayList<ContractStatus>(Arrays.asList(ContractStatus.values()));
		for (ContractStatus status : statuses) {
			stringBuilder.append("" + status + ", ");
		}
		// sorting
		statuses.sort(new Comparator<ContractStatus>() {
			@Override
			public int compare(ContractStatus o1, ContractStatus o2) {
				return o1.name().compareTo(o2.name());
			}
		});

		String result = String.format("%s", stringBuilder);
		result = result.substring(0, result.length() - 2);
		return result;
	}

	/**
	 * @return a string representation of the payForm from class Contract.
	 */
	public static String payFormArrayToMessage() {
		StringBuilder stringBuilder = new StringBuilder("");
		List<PayForm> forms = new ArrayList<PayForm>(Arrays.asList(PayForm.values()));
		for (PayForm form : forms) {
			stringBuilder.append("" + form + ", ");
		}
		// sorting
		forms.sort(new Comparator<PayForm>() {
			@Override
			public int compare(PayForm o1, PayForm o2) {
				return o1.name().compareTo(o2.name());
			}
		});

		String result = String.format("%s", stringBuilder);
		result = result.substring(0, result.length() - 2);
		return result;
	}

	/**
	 * @return a string representation of the payStatus from class Contract.
	 */
	public static String payStatusArrayToMessage() {
		StringBuilder stringBuilder = new StringBuilder("");
		List<PayStatus> statuses = new ArrayList<PayStatus>(Arrays.asList(PayStatus.values()));
		for (PayStatus status : statuses) {
			stringBuilder.append("" + status + ", ");
		}
		// sorting
		statuses.sort(new Comparator<PayStatus>() {
			@Override
			public int compare(PayStatus o1, PayStatus o2) {
				return o1.name().compareTo(o2.name());
			}
		});

		String result = String.format("%s", stringBuilder);
		result = result.substring(0, result.length() - 2);
		return result;
	}

	public static String languagesArrayToMessage() {
		StringBuilder stringBuilder = new StringBuilder("");
		List<Language> languages = new ArrayList<Language>(Arrays.asList(Language.values()));
		for (Language language : languages) {
			stringBuilder.append("" + language + ", ");
		}
		// sorting
		languages.sort(new Comparator<Language>() {
			@Override
			public int compare(Language o1, Language o2) {
				return o1.name().compareTo(o2.name());
			}
		});

		String result = String.format("%s", stringBuilder);
		result = result.substring(0, result.length() - 2);
		return result;
	}

	public static String tableNamesArrayToMessage() {
		StringBuilder stringBuilder = new StringBuilder("");
		List<Table> tables = new ArrayList<Table>(Arrays.asList(Table.values()));
		for (Table table : tables) {
			stringBuilder.append("" + table + ", ");
		}
		// sorting
		tables.sort(new Comparator<Table>() {
			@Override
			public int compare(Table o1, Table o2) {
				return o1.name().compareTo(o2.name());
			}
		});

		String result = String.format("%s", stringBuilder);
		result = result.substring(0, result.length() - 2);
		return result;
	}

	public static String itemsAttributeArrayToMessage() {
		StringBuilder stringBuilder = new StringBuilder("");
		List<Attribute> attributes = new ArrayList<Attribute>(Arrays.asList(Attribute.values()));
		for (Attribute attribute : attributes) {
			stringBuilder.append("" + attribute + ", ");
		}
		// sorting
		attributes.sort(new Comparator<Attribute>() {
			@Override
			public int compare(Attribute o1, Attribute o2) {
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

	public static String contractColumnNameEnumArrayToMessage() {
		StringBuilder stringBuilder = new StringBuilder("");
		List<ContractColumnNamesForSortingParams> params = new ArrayList<ContractColumnNamesForSortingParams>(
				Arrays.asList(ContractColumnNamesForSortingParams.values()));
		for (ContractColumnNamesForSortingParams p : params) {
			stringBuilder.append("" + p + ", ");
		}
		// sorting
		params.sort(new Comparator<ContractColumnNamesForSortingParams>() {
			@Override
			public int compare(ContractColumnNamesForSortingParams o1, ContractColumnNamesForSortingParams o2) {
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

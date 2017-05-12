package ru.mail.ales2003.deals2017.webapp;

import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.webapp.util.AttributeFromEnumsToCsvWriter;
import ru.mail.ales2003.deals2017.webapp.util.ItemNamesFromEnumsToCsvWriter;

@Repository
public class EnumsToCsvWriterInitializer {

	private static String rootFolder = "D://REPO/GitHub/deals_2017/jmeter-root-folder/csvforenums/";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		measureUnitToCSV();
		attributeToCSV();
		itemNamesToCSV();
	}

	// FOR MEASUREUNIT
	private static void measureUnitToCSV() {
		// String fileName = ""+rootFolder + "measure.csv";

		System.out.println("Write CSV file:");
		AttributeFromEnumsToCsvWriter.writeCsvFile(getFileNameForMeasureUnit());

		/*
		 * System.out.println("\nRead CSV file:");
		 * CsvFileReader.readCsvFile(fileName);
		 */
	}

	private static String getFileNameForMeasureUnit() {
		String fileName = rootFolder + "measure.csv";
		return fileName;
	}

	// FOR ATTRIBUTE
	private static void attributeToCSV() {
		System.out.println("Write CSV file:");
		AttributeFromEnumsToCsvWriter.writeCsvFile(getFileNameForAttribute());
	}

	private static String getFileNameForAttribute() {
		String fileName = rootFolder + "attribute.csv";
		return fileName;
	}

	// FOR ITEM
	private static void itemNamesToCSV() {
		System.out.println("Write CSV file:");
		ItemNamesFromEnumsToCsvWriter.writeCsvFile(getFileNameForItemName());
	}

	private static String getFileNameForItemName() {
		String fileName = rootFolder + "item.csv";
		return fileName;
	}
}

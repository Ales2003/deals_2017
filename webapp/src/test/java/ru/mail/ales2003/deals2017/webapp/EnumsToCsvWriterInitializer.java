package ru.mail.ales2003.deals2017.webapp;

import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.webapp.util.MeasureFromEnumsToCsvWriter;

@Repository
public class EnumsToCsvWriterInitializer {

	private static String rootFolder = "D://REPO/GitHub/deals_2017/webapp/src/main/resources/csvforenums/";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// String fileName = ""+rootFolder + "measure.csv";

		System.out.println("Write CSV file:");
		MeasureFromEnumsToCsvWriter.writeCsvFile(getFileName());

		/*
		 * System.out.println("\nRead CSV file:");
		 * CsvFileReader.readCsvFile(fileName);
		 */

	}

	private static String getFileName() {
		String fileName = rootFolder + "measure.csv";
		return fileName;
	}
}

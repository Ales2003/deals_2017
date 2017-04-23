package ru.mail.ales2003.deals2017.dao.api;

import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.api.custom.enums.forjmeter.FromEnumsToCsvMeasureWriter;

@Repository
public class EnumsToCsvWriterTest {

	private static String rootFolder = "D://REPO/GitHub/deals_2017/dao-api/src/main/resources/";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// String fileName = ""+rootFolder + "measure.csv";

		System.out.println("Write CSV file:");
		FromEnumsToCsvMeasureWriter.writeCsvFile(getFileName());

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

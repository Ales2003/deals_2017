package ru.mail.ales2003.deals2017.dao.api;

import ru.mail.ales2003.deals2017.dao.api.custom.enums.forjmeter.FromEnumsCsvWriter;

public class EnumsToCsvWriterTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String fileName = "D:\\REPO\\GitHub\\deals_2017\\dao-api\\src\\main\\java\\ru\\mail\\ales2003\\deals2017\\dao\\api\\custom\\enums\\forjmeter\\measure.csv";

		System.out.println("Write CSV file:");
		FromEnumsCsvWriter.writeCsvFile(fileName);

		/*System.out.println("\nRead CSV file:");
		CsvFileReader.readCsvFile(fileName);*/

	}

}

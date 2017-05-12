package ru.mail.ales2003.deals2017.webapp.util;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.api.customentities.ItemName;

@Repository
public class ItemNamesFromEnumsToCsvWriter {

	// Delimiter used in CSV file
	private static final String NEW_LINE_SEPARATOR = "\n";

	/*
	 * // CSV file header private static final Object[] FILE_HEADER = { "name"
	 * };
	 */

	public static void writeCsvFile(String fileName) {

		// FileWriter fileWriter = null;

		// CSVPrinter csvFilePrinter = null;

		List<ItemName> items = null;

		// Create the CSVFormat object with "\n" as a record delimiter
		// CSVFormat csvFileFormat =
		// CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);

		try (CSVPrinter csvFilePrinter = new CSVPrinter(new FileWriter(fileName),
				CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR))) {

			// Create a new list of enums measure
			items = new ArrayList<ItemName>(Arrays.asList(ItemName.values()));

			for (ItemName item : items) {
				List<String> ms = new ArrayList<>();
				ms.add(item.name());
				csvFilePrinter.printRecord(ms);
			}

			// initialize FileWriter object
			// fileWriter = new FileWriter(fileName);

			// initialize CSVPrinter object
			// csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);

			/*
			 * // Create CSV file header
			 * csvFilePrinter.printRecord(FILE_HEADER);
			 */
			// Write the measure list to the CSV file
			// csvFilePrinter.printRecord(measures);

			System.out.println("CSV file was created successfully !!!");

		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} /*
			 * finally { try { fileWriter.flush(); fileWriter.close();
			 * csvFilePrinter.close(); } catch (IOException e) { System.out.
			 * println("Error while flushing/closing fileWriter/csvPrinter !!!"
			 * ); e.printStackTrace(); }
			 */
	}
}

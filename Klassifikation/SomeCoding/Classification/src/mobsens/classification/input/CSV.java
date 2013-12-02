package mobsens.classification.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;

import mobsens.classification.data.Location;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CSV {

	public static ArrayList<String[]> csvToArrayList(File file) {
		ArrayList<String[]> result = new ArrayList<>();

		try (Reader r = new BufferedReader(new FileReader(file));
				CSVParser parser = new CSVParser(r, CSVFormat.DEFAULT)) {

			for (CSVRecord record : parser.getRecords()) {
				String[] recordData = new String[record.size()];
				for (int i = 0; i < record.size(); i++) {
					recordData[i] = record.get(i);
				}
				result.add(recordData);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static ArrayList<Location> csvToLocation(File file) {
		ArrayList<Location> result = new ArrayList<>();
		
		ArrayList<String[]> csvArray = csvToArrayList(file);
		// remove description
		csvArray.remove(0);
		
		try {
			for (String[] record : csvArray) {
				result.add(new Location(parseDouble(record[0]),
						parseDouble(record[1]), 
						parseDouble(record[2]),
						parseDouble(record[3]), 
						parseDouble(record[4]),
						parseDouble(record[5])));
			}
		} catch (Exception e) {
			System.err.println("incorrect CSV-File/Format. "+e);
		}
		return result;
	}
	
	
	private static double parseDouble(String input){
		try{
			return Double.parseDouble(input);
		}catch(Exception e){
			return 0;
		}
	}

}

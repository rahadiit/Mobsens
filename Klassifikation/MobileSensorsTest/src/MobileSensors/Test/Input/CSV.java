package MobileSensors.Test.Input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import MobileSensors.Storage.Sensors.Location;

public class CSV {

	public static ArrayList<String[]> csvToArrayList(File file) {

		try (Reader r = new BufferedReader(new FileReader(file))) {
			return csvToArrayList(r);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static ArrayList<String[]> csvToArrayList(String input) {

		try (Reader r = new StringReader(input)) {
			return csvToArrayList(r);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<String[]> csvToArrayList(Reader r) {
		ArrayList<String[]> result = new ArrayList<>();

		try (CSVParser parser = new CSVParser(r, CSVFormat.DEFAULT)) {

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
		return csvToLocation(csvToArrayList(file));
	}
	
	public static ArrayList<Location> csvToLocation(String input){
		return csvToLocation(csvToArrayList(input));
	}

	public static ArrayList<Location> csvToLocation(ArrayList<String[]> input) {
		ArrayList<Location> result = new ArrayList<>();
		
		//TODO: rausnehmen wenn im Client gefixt
		input = FileInput.deleteDuplicates(input);
		
		// remove description
		input.remove(0);
		
		String recordCpy[]=null;
		try {
			for (String[] record : input) {
				recordCpy = record.clone();
				if(record.length==7){
					result.add(new Location(parseLong(record[0]),
							parseDouble(record[1]), parseDouble(record[2]),
							parseDouble(record[3]), parseDouble(record[4]),
							parseDouble(record[5]), parseDouble(record[6])));
				
				}else{
					for(String rec:record)
						System.out.print(rec+ " ");
				}
			}
		} catch (Exception e) {
			for(String rec:recordCpy)
				System.out.print(rec+ " ");
			System.err.println("incorrect CSV-File/Format. " + e );
		}
		return result;

	}

	/*
	public static ArrayList<Annotation> csvToAnnotation(String input){
		return csvToAnnotation(csvToArrayList(input));
	}
	public static ArrayList<Annotation> csvToAnnotation(ArrayList<String[]> input) {
		ArrayList<Annotation> result = new ArrayList<>();
		
		//TODO: rausnehmen wenn im Client gefixt
		input = FileOutput.deleteDuplicates(input);
		
		// remove description
		input.remove(0);
		
		String recordCpy[]=null;
		try {
			for (String[] record : input) {
				recordCpy = record.clone();
				if(record.length==2){
					result.add(new Annotation(parseDouble(record[0]),record[1]));
				
				}
			}
		} catch (Exception e) {
			for(String rec:recordCpy)
				System.out.print(rec+ " ");
			System.err.println("incorrect CSV-File/Format. " + e );
		}
		return result;

	}
	
	*/
	
	
	private static double parseDouble(String input) {
		try {
			return Double.parseDouble(input);
		} catch (Exception e) {
			return 0;
		}
	}
	private static long parseLong(String input) {
		try {
			return Long.parseLong(input);
		} catch (Exception e) {
			return 0;
		}
	}
	

}

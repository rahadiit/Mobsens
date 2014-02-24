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

import MobileSensors.Storage.Sensors.Accelerometer;
import MobileSensors.Storage.Sensors.Annotation;
import MobileSensors.Storage.Sensors.Location;
import MobileSensors.Storage.Sensors.Sensor.Sensor;


public class CSV {

	@SuppressWarnings("unchecked")
	public static <T extends Sensor> ArrayList<T> csvToSensor(
			ArrayList<String[]> input, Class<T> type) {
		ArrayList<T> result = new ArrayList<>();
		
		// TODO: rausnehmen wenn im Client gefixt
		input = FileInput.deleteDuplicates(input);

		// remove description
		input.remove(0);

		String recordCpy[] = null;
		try {
			for (String[] record : input) {
				recordCpy = record.clone();

				try {
					if (type == Location.class) {
						try{
						result.add((T) new Location(parseLong(record[0]),
								parseDouble(record[1]), parseDouble(record[2]),
								parseDouble(record[3]), parseDouble(record[4]),
								parseDouble(record[5]), parseDouble(record[6])));
						}catch(Exception e){
							result.add((T) new Location(parseLong(record[0]),
									parseDouble(record[1]), parseDouble(record[2]),
									0, parseDouble(record[3]),
									parseDouble(record[4]), parseDouble(record[5])));
						}
					} else if (type == Annotation.class) {
						result.add((T) new Annotation(parseLong(record[0]),
								record[1]));
					} else if(type==Accelerometer.class){
						result.add((T) new Accelerometer(parseLong(record[0]),
								parseDouble(record[1]), parseDouble(record[2]),
								parseDouble(record[3])));
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			for (String rec : recordCpy)
				System.out.print(rec + " ");
			System.err.println("incorrect CSV-File/Format. " + e);
		}
		return result;
	}

	public static <T extends Sensor> ArrayList<T> csvToSensor(File file,
			Class<T> type) {
		return csvToSensor(csvToArrayList(file), type);
	}

	public static <T extends Sensor> ArrayList<T> csvToSensor(String input,
			Class<T> type) {
		return csvToSensor(csvToArrayList(input), type);
	}

	public static ArrayList<String[]> csvToArrayList(File file) {

		try (Reader r = new BufferedReader(new FileReader(file))) {
			return csvToArrayList(r);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static ArrayList<Location> csvToLocation(File file) {
		return csvToSensor(csvToArrayList(file),Location.class);
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

package MobileSensors.Sensors.CSVParsers;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import MobileSensors.Sensors.Location;

public class LocationCSVParser implements SensorCSVParser<Location> {

	public final static String HEADER_TIME = "time";
	public final static String HEADER_LATITUDE = "latitude";
	public final static String HEADER_LONGITUDE = "longitude";
	public final static String HEADER_ALTITUDE = "altitude";
	public final static String HEADER_SPEED = "speed";
	public final static String HEADER_BEARING = "bearing";
	public final static String HEADER_ACCURANCY = "accurancy";
	
	
	@Override
	public ArrayList<Location> parse(Reader reader) throws IOException {
		
		CSVFormat format = CSVFormat.DEFAULT
				.withHeader(
						LocationCSVParser.HEADER_TIME,
						LocationCSVParser.HEADER_LATITUDE,
						LocationCSVParser.HEADER_LONGITUDE,
						LocationCSVParser.HEADER_ALTITUDE,
						LocationCSVParser.HEADER_SPEED,
						LocationCSVParser.HEADER_BEARING,
						LocationCSVParser.HEADER_ACCURANCY)
				.withSkipHeaderRecord(true);
		
		
		@SuppressWarnings("resource")
		CSVParser parser = new CSVParser(reader, format);
		
		ArrayList<Location> result = new ArrayList<Location>();
		
		for (CSVRecord record : parser) {
			
			long time = Long.parseLong(record.get(LocationCSVParser.HEADER_TIME));
			double latitude  = Double.parseDouble(record.get(LocationCSVParser.HEADER_LATITUDE));
			double longitude  = Double.parseDouble(record.get(LocationCSVParser.HEADER_LONGITUDE));
			double altitude  = Double.parseDouble(record.get(LocationCSVParser.HEADER_ALTITUDE));
			double speed  = Double.parseDouble(record.get(LocationCSVParser.HEADER_SPEED));
			double bearing  = Double.parseDouble(record.get(LocationCSVParser.HEADER_BEARING));
			double accurancy  = Double.parseDouble(record.get(LocationCSVParser.HEADER_ACCURANCY));
			
			result.add(new Location(time, latitude, longitude, altitude, speed, bearing, accurancy));
			
		}
		
		return result;
		
	}

}

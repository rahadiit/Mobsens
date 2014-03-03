package MobileSensors.Sensors.CSVParsers;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import MobileSensors.Sensors.Accelerometer;

public class AccelerometerCSVParser implements SensorCSVParser<Accelerometer> {

	public final static String HEADER_TIME = "time";
	public final static String HEADER_X = "x";
	public final static String HEADER_Y = "y";
	public final static String HEADER_Z = "z";
	
	@Override
	public ArrayList<Accelerometer> parse(Reader reader) throws IOException {
		
		CSVFormat format = CSVFormat.DEFAULT
				.withHeader(
						AccelerometerCSVParser.HEADER_TIME,
						AccelerometerCSVParser.HEADER_X,
						AccelerometerCSVParser.HEADER_Y,
						AccelerometerCSVParser.HEADER_Z)
				.withSkipHeaderRecord(true);
		
		
		@SuppressWarnings("resource")
		CSVParser parser = new CSVParser(reader, format);
		
		ArrayList<Accelerometer> result = new ArrayList<Accelerometer>();
		
		for (CSVRecord record : parser) {
			
			long time = Long.parseLong(record.get(AccelerometerCSVParser.HEADER_TIME));
			double x  = Double.parseDouble(record.get(AccelerometerCSVParser.HEADER_X));
			double y  = Double.parseDouble(record.get(AccelerometerCSVParser.HEADER_Y));
			double z  = Double.parseDouble(record.get(AccelerometerCSVParser.HEADER_Z));
			
			result.add(new Accelerometer(time, x, y, z));
			
		}
		
		return result;
		
	}

}

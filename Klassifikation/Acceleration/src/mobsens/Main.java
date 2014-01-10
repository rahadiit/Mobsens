package mobsens;

import java.io.*;
import java.util.*;

import mobsens.physics.Jerk;
import mobsens.sensors.AccelerometerValue;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Main {

	public static void main(String[] args) throws IOException {
		
		Reader r = new FileReader("./data/accelerometers.csv");
		
		
		@SuppressWarnings("resource")
		CSVParser parser = new CSVParser(r, CSVFormat.DEFAULT);
		
		List<AccelerometerValue> lala = new ArrayList<AccelerometerValue>();
		
		List<CSVRecord> records = parser.getRecords();
		
		for (int i=1; i < records.size(); i++) {
			
			CSVRecord record = records.get(i);
			
			
			AccelerometerValue av = new AccelerometerValue(
					Long.valueOf(record.get(0)).longValue(),
					Double.valueOf(record.get(1)).doubleValue(),
					Double.valueOf(record.get(2)).doubleValue(),
					Double.valueOf(record.get(3)).doubleValue());
			
			
			lala.add(av);
			
		}
		
		for (int i=1; i < lala.size(); i++) {
			
			AccelerometerValue a1 = lala.get(i-1);
			AccelerometerValue a2 = lala.get(i);
			
			Jerk j = a2.getXAcceleration().jerk(a1.getXAcceleration());
			
//			if (j.getValue() > 1 || j.getValue() < -1)
			
			System.out.println(j.getTime() + ", " + j.getValue());
			
		}
		
	}

}

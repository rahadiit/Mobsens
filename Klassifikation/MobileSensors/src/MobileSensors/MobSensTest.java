package MobileSensors;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import MobileSensors.Events.Classifiers.BrakeClassifier;
import MobileSensors.Events.Classifiers.DodgeClassifier;
import MobileSensors.Sensors.Accelerometer;
import MobileSensors.Sensors.SensorRecord;
import MobileSensors.Sensors.CSVParsers.AccelerometerCSVParser;

public class MobSensTest {

	public static void main (String[] args) throws Exception {
		
		AccelerometerCSVParser acp = new AccelerometerCSVParser();
		ArrayList<Accelerometer> values = acp.parse(new FileReader(new File("./test/accelerometers_test.csv")));
		
		ArrayList<SensorRecord> srs = new ArrayList<SensorRecord>();
		
		int windowWidth = 200;
		int windowDelta = 200;
		
		int i = 0;
		
		while (i < values.size() - windowWidth) {
			
			ArrayList<Accelerometer> window = new ArrayList<Accelerometer>();
			
			int j = i;
			
			while (j - i < windowWidth) {
				
				window.add(values.get(j));
				
				j++;
				
			}
			
			SensorRecord sr = new SensorRecord();
			sr.setAcceleration(window);
			
			srs.add(sr);
			
			i += windowDelta;
			
		}
		
		BrakeClassifier bc = new BrakeClassifier(new File("./model/Brake.model"));
		DodgeClassifier dc = new DodgeClassifier(new File("./model/Dodge.model"));
		
		int count = 0;
		
		for (SensorRecord sr : srs) {
			
			String str = dc.classify(sr);
			
			System.out.println(str);
			
			if (str.equals("DODGE")) count++;
			
		}
		
		System.out.println(count);
		
	}
	
	
}

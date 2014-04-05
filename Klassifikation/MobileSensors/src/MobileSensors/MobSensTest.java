package MobileSensors;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;

import MobileSensors.Events.Event;
import MobileSensors.Events.Classifiers.BrakeClassifier;
import MobileSensors.Events.Classifiers.DodgeClassifier;
import MobileSensors.Helpers.SensorWindowBuilder;
import MobileSensors.Helpers.DataWindowBuilder;
import MobileSensors.Sensors.Accelerometer;
import MobileSensors.Sensors.SensorRecord;
import MobileSensors.Sensors.Annotation;
import MobileSensors.Sensors.CSVParsers.AccelerometerCSVParser;
import MobileSensors.Sensors.CSVParsers.AnnotationCSVParser;

public class MobSensTest {

	public static void main (String[] args) throws Exception {
		
		ArrayList<Annotation> anns = (new AnnotationCSVParser()).parse(new FileReader(new File("./test/annotations.csv")));
		
		
		
		ArrayList<Accelerometer> values = (new AccelerometerCSVParser()).parse(new FileReader(new File("./test/accelerometers_test.csv")));
		
		ArrayList<SensorRecord> srs = new ArrayList<SensorRecord>();
		
		ArrayList<ArrayList<Accelerometer>> windows = new ArrayList<ArrayList<Accelerometer>>();
		ArrayList<Accelerometer> window = new ArrayList<Accelerometer>();
			
		
		windows = (new SensorWindowBuilder<Accelerometer>()).buildWindows(values, 2 * 1000);
		
		
		for (ArrayList<Accelerometer> accWindow : windows) {
			
			SensorRecord sr = new SensorRecord();
			sr.setAcceleration(accWindow);
			
			srs.add(sr);
			
		}
		
		BrakeClassifier bc = new BrakeClassifier(new File("./model/Brake.model"));
		DodgeClassifier dc = new DodgeClassifier(new File("./model/Dodge.model"));
		
		
		
		System.out.println(srs.size());
		
		ArrayList<Event> events = new ArrayList<Event>();
		
		for (SensorRecord sr : srs) {
			
			events.addAll(dc.classifyEvents(sr));
			
			
		}
		
		long min = 60 * 1000;
		
		long sMin = (anns.get(0).getTime()/min)*min;
		long eMin = (anns.get(anns.size()-1).getTime()/min)*min + min;
		
		long time = sMin;
		
		while (time < eMin) {
			
			System.out.println(new Date(time));
			
			for (Annotation ann : anns) {
				
				if (time < ann.getTime() && ann.getTime() <= time + min) {
					
					System.out.println("\t" + ann);
					
				}
				
			}
			
			for (Event e : events) {
				
				if (time < e.getStartTime() && e.getEndTime() <= time + min) {
					
					System.out.println("\t#" + events.indexOf(e) + " : " + e);
					
				}
				
			}
			
			time += min;
			
		}
		
		
		
	}
	
	
}

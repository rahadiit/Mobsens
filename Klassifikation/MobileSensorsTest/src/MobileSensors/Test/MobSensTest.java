package MobileSensors.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;

import com.sun.jersey.api.client.Client;

import MobileSensors.MobSens;
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
import MobileSensors.Test.Data.Recording;
import MobileSensors.Test.Data.SensorE;
import MobileSensors.Test.Data.URLS;
import MobileSensors.Test.Input.RESTful;

public class MobSensTest {

	private static final String username = "mlukas@gmx.net";
	private static final String password = "12345678";
	
	private static final long windowWidth = 3000;
	
	public static void main (String[] args) throws Exception {
		
		MobSens mobs = new MobSens();
		mobs.setBrakeModelFile(new File("../MobileSensors/model/Brake.model"));
		mobs.setDodgeModelFile(new File("../MobileSensors/model/Dodge.model"));
		mobs.setKerbstoneModelFile(new File("../MobileSensors/model/Kerbstone.model"));
		
		Client client = RESTful.login(URLS.LOGIN.getURL(), username, password);

		ArrayList<Recording> recordings = RESTful.recordingOutput(client, URLS.LIST_RECORDINGS.getURL()); 
		

		for (int i = 0; i < recordings.size(); i++) {

		
		
			Recording recording = recordings.get(i);

			if (545 <= recording.getId() && recording.getId() <= 547) {
				
				System.out.println("TESTING RECORD " + recording.getId());

				client = RESTful.login(URLS.LOGIN.getURL(), username, password);

				String accCSV = RESTful.getCSV(client, recording.getId(), URLS.CSV.getURL(), SensorE.ACCELEROMETERS);

				String annCSV = RESTful.getCSV(client, recording.getId(), URLS.CSV.getURL(), SensorE.ANNOTATIONS);

				ArrayList<Annotation> anns = (new AnnotationCSVParser()).parse(new StringReader(annCSV));
				
				ArrayList<Accelerometer> values = (new AccelerometerCSVParser()).parse(new StringReader(accCSV));
				
		
				System.out.println("TESTING RECORD " + recording.getId());
		
//				ArrayList<Annotation> anns = (new AnnotationCSVParser()).parse(new FileReader(new File("../MobileSensors/test/annotations.csv")));
//				
//				ArrayList<Accelerometer> values = (new AccelerometerCSVParser()).parse(new FileReader(new File("../MobileSensors/test/accelerometers_test.csv")));
//		
				ArrayList<ArrayList<Accelerometer>> windows = (new SensorWindowBuilder<Accelerometer>()).buildWindows(values, MobSensTest.windowWidth);
				
				ArrayList<Event> events = new ArrayList<Event>();
				
				for (ArrayList<Accelerometer> accWindow : windows) {
					
					SensorRecord sr = new SensorRecord();
					sr.setAcceleration(accWindow);
					
					events.addAll(mobs.classifyEvents(sr));
					
				}
				
				
				
				String evaluation = eval(anns, events);
				
				BufferedWriter bw = new BufferedWriter(new FileWriter(new File("./test/test-"+ recording.getId()+".txt")));
				bw.write("Test-" + + recording.getId());
				bw.newLine();
				bw.write("WindowWidth: " + MobSensTest.windowWidth + "ms");
				bw.newLine();
				bw.newLine();
				bw.write(recording.getTitle());
				bw.newLine();
				bw.write(evaluation);
				bw.flush();
				bw.close();
				
			}
		}
	
		
		
//		ArrayList<Annotation> anns = (new AnnotationCSVParser()).parse(new FileReader(new File("./test/annotations.csv")));
//		
//		
//		
//		ArrayList<Accelerometer> values = (new AccelerometerCSVParser()).parse(new FileReader(new File("./test/accelerometers_test.csv")));
//		
//		ArrayList<SensorRecord> srs = new ArrayList<SensorRecord>();
//		
//		ArrayList<ArrayList<Accelerometer>> windows = new ArrayList<ArrayList<Accelerometer>>();
//		ArrayList<Accelerometer> window = new ArrayList<Accelerometer>();
//			
//		
//		windows = (new SensorWindowBuilder<Accelerometer>()).buildWindows(values, 2 * 1000);
//		
//		
//		for (ArrayList<Accelerometer> accWindow : windows) {
//			
//			SensorRecord sr = new SensorRecord();
//			sr.setAcceleration(accWindow);
//			
//			srs.add(sr);
//			
//		}
//		
//		BrakeClassifier bc = new BrakeClassifier(new File("./model/Brake.model"));
//		DodgeClassifier dc = new DodgeClassifier(new File("./model/Dodge.model"));
//		
//		
//		
//		System.out.println(srs.size());
//		
//		ArrayList<Event> events = new ArrayList<Event>();
//		
//		for (SensorRecord sr : srs) {
//			
//			events.addAll(bc.classifyEvents(sr));
//			
//			
//		}
		
		
		
		
	}
	
	public static String eval (ArrayList<Annotation> anns, ArrayList<Event> events) {
		
		if (events.size() < 1) return "Nothing Found!";
		
		long min = 60000;
		
		long sMin = (events.get(0).getStartTime()/min)*min;
		long eMin = (events.get(events.size()-1).getEndTime()/min)*min + min;
		
		long time = sMin;
		
		String str = "";
		
		while (time < eMin) {
			
			str += new Date(time) + "\n";
			
			
			int annCount = 0;
			int evtCount = 0;
			
			for (Annotation ann : anns) {
				
				if (time < ann.getTime() && ann.getTime() <= time + min) {
					
					str += "\t" + ann + "\n";
					
					annCount++;
					
				}
				
			}
			
			for (Event e : events) {
				
				if (time < e.getStartTime() && e.getEndTime() <= time + min) {
					
					str += "\t" + e + "\n";
					
					evtCount++;
					
				}
				
			}
			
			str += "\tLogged Events: " + annCount/2 + "\n";
			str += "\tFound Events: " + evtCount + "\n";
			
//			double success = (evtCount/(annCount/2)) * 100;
//			
//			str += "\tSuccess: " + success + "%\n";
			
			time += min;
			
		}
		
//		System.out.println(str);
		
		return str;
		
	}
	
	
}

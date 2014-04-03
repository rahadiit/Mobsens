package MobileSensors.Test;

import java.beans.EventSetDescriptor;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.jaxrs.Annotations;

import com.sun.jersey.api.client.Client;

import MobileSensors.MobSens;
import MobileSensors.Deprecated.Accelerometer;
import MobileSensors.Deprecated.Annotation;
import MobileSensors.Deprecated.Location;
import MobileSensors.Deprecated.Timeable;
import MobileSensors.Events.Event;
import MobileSensors.Events.EventType;
import MobileSensors.Sensors.SensorRecord;
import MobileSensors.Test.Data.Recording;
import MobileSensors.Test.Data.SensorE;
import MobileSensors.Test.Data.URLS;
import MobileSensors.Test.Input.CSV;
import MobileSensors.Test.Input.RESTful;
import MobileSensors.Test.Output.CSVFiles;

public class Main {

	private static final String username = "mlukas@gmx.net";
	private static final String password = "12345678";

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {

		// Auf Server einloggen
		Client client = RESTful.login(URLS.LOGIN.getURL(), username, password);

		// JsonInput.parseJson(JsonInput.getJsonFromId(client, 389));

		ArrayList<Recording> recordings = RESTful.recordingOutput(client,
				URLS.LIST_RECORDINGS.getURL()); // + "?page=" + j
		int count = 0;

		for (int i = 0; i < recordings.size(); i++) {

			Recording recording = recordings.get(i);

			if (recording.getTitle() != null
					&& recording.getTitle().contains("uni_ausw")) {
				count++;
				System.out.println("starting "+count+". recording");
				client = RESTful.login(URLS.LOGIN.getURL(), username, password);

				String acceleroCSV = RESTful.getCSV(client, recording.getId(),
						URLS.CSV.getURL(), SensorE.ACCELEROMETERS);

				String annotationCSV = RESTful.getCSV(client,
						recording.getId(), URLS.CSV.getURL(),
						SensorE.ANNOTATIONS);

				String locationCSV = RESTful.getCSV(client, recording.getId(),
						URLS.CSV.getURL(), SensorE.LOCATIONS);

				ArrayList<Location> locations = CSV.csvToSensor(locationCSV,
						Location.class);
				ArrayList<Accelerometer> accelerometers = CSV.csvToSensor(
						acceleroCSV, Accelerometer.class);
				ArrayList<Annotation> annotations = CSV.csvToSensor(
						annotationCSV, Annotation.class);
				
				ArrayList<ArrayList<Accelerometer>> windows = new ArrayList<>();
				windows.addAll((Collection<? extends ArrayList<Accelerometer>>) Timeable.window(accelerometers,5000));
				
				ArrayList<Event> events = new ArrayList<>();
				
				for(ArrayList<Accelerometer> window:windows){
					
					ArrayList<MobileSensors.Sensors.Accelerometer> newAccel = new ArrayList<>();
					for(Accelerometer item:window){
						newAccel.add(new MobileSensors.Sensors.Accelerometer(item.getTime(), item.getX(), item.getY(), item.getZ()));
					}
					SensorRecord sR = new SensorRecord();
					sR.setAcceleration(newAccel);
					MobSens mS = new MobSens();
					mS.setBrakeModelFile(new File("/Users/henny/Desktop/uni/fp/newestgit/MobSens/Klassifikation/MobileSensors/model/Brake.model"));
					mS.setDodgeModelFile(new File("/Users/henny/Desktop/uni/fp/newestgit/MobSens/Klassifikation/MobileSensors/model/Dodge.model"));
					mS.setKerbstoneModelFile(new File("/Users/henny/Desktop/uni/fp/newestgit/MobSens/Klassifikation/MobileSensors/model/Kerbstone.model"));
					
					events.addAll(mS.classifyEvents(sR));
					
				}
				
				System.out.println("enjoy your events!");
				
				int countRightEvents = 0;
				for(Event event:events){
					
					for(Annotation anno:annotations){
						
						if(event.getStartTime()< anno.getTime() && event.getEndTime()> anno.getTime()){
							if(anno.getTag().toLowerCase().startsWith("bremsen st") && event.getEventType().equals(EventType.BRAKING)){
								countRightEvents++;
								System.out.println("bremsen richtig erkannt");
								
							}
							if(anno.getTag().toLowerCase().startsWith("ausweichmanöver start") && event.getEventType().equals(EventType.DODGE)){
								countRightEvents++;
								System.out.println("ausweichen richtig erkannt");
								
							}
							if(anno.getTag().toLowerCase().startsWith("bord") && event.getEventType().equals(EventType.KERBSTONE)){
								countRightEvents++;
								System.out.println("kerbstone richtig erkannt");
								
							}
						}
					}
				}
				System.out.println(countRightEvents+" / "+events.size()+"  richtig erkannt");
				
				
				System.out.println(annotations.size()/2+". vorgaenge ");
				
				for(Annotation anno:annotations){
					System.out.println(anno.getTag());
				}
				
			}

		}
		
		System.out.println(count + ". Recording(s) untersucht");

		System.out.println("done");
	}
}

/*
 * alter Code
 * 
 * accelerometer = CSV.csvToSensor(acceleroCSV, Accelerometer.class);
 * 
 * AcceleroCalc.accelerometerCalc(accelerometer, true);
 * 
 * // Chart.drawChart(recordings.get(i), accelerometer, // new
 * ArrayList<Annotation>(), // new ArrayList<Event>(), 2, 0, //
 * Accelerometer.class);
 * 
 * accelerometer = CSV.csvToSensor(acceleroCSV, Accelerometer.class);
 * 
 * AcceleroCalc.accelerometerCalc(accelerometer, true);
 * 
 * Collection<Collection<Accelerometer>> accelWindows = Accelerometer
 * .window(accelerometer, 3000);
 * 
 * MobSens c = new MobSens();
 * 
 * ArrayList<Event> events = new ArrayList<>(); for (Collection<Accelerometer>
 * accel : accelWindows) {
 * 
 * Window window = new Window();
 * window.setAcceleration((ArrayList<Accelerometer>) accel);
 * 
 * events.addAll(c.getEvents(window));
 * 
 * }
 * 
 * System.out.println("Anzahl dodges: " + events.size()); for (Event ev :
 * events) { System.out.println(ev.toString()); }
 * 
 * }
 * 
 * // WekaFile.writeFile(accelerometer, 1000, "Weka.csv");
 * 
 * // Chart.drawChart(recordings.get(i), accelerometer, // new
 * ArrayList<Annotation>(), new ArrayList<Event>(), 2, // 0, //
 * Accelerometer.class);
 * 
 * if (schleifeausfuehren) { // Alle Charts aus dem Bremsvorgang-Test for (int i
 * = 0; i < recordings.size(); i++) {
 * 
 * try { acceleroCSV = RESTful.getCSV(client, recordings.get(i) .getId(),
 * URLS.CSV.getURL(), SensorE.ACCELEROMETERS);
 * 
 * } catch (Exception e) { client = RESTful.login(URLS.LOGIN.getURL(), username,
 * password); i--; continue; } } }
 * 
 * 
 * // String acceleroCSV = FileUtils.readFileToString(new File( //
 * "/Users/henny/Desktop/uni/fp/data1/dodge/dodge_2.csv")); // String
 * annotationCSV = ""; // ArrayList<Accelerometer> accelerometer =
 * CSV.csvToSensor(acceleroCSV, // Accelerometer.class); // //
 * Chart.drawChart(new Recording(0, "0"), accelerometer, // new
 * ArrayList<Annotation>(), new ArrayList<Event>(), 2, 0, //
 * Accelerometer.class);
 * 
 * 
 * 
 * ArrayList<Recording> recordings = RESTful.recordingOutput(client,
 * URLS.LIST_RECORDINGS.getURL()); // + "?page=" + j
 */

// dodge/record_id/accelerometer.csv
// File file = new File("/Users/henny/weka/noevent");
// if (!file.exists()) {
// file.mkdirs();
// }
// CSVFiles.writeToFile(accelerometers, file, Accelerometer.class);
// CSVFiles.writeToFile(locations, new
// File("/Users/henny/weka/noevent/location"), Location.class);

// CSVFiles.splitAndWriteArrayList(
// accelerometers,
// annotations,
// file,
// Accelerometer.class);

// CSVFiles.splitAndWriteArrayList(
// locations,
// annotations,
// new File("/Users/henny/weka/braking/brake/location"),
// Location.class);

// CSVFiles.splitAndWriteArrayList(accelerometers, annotations, new
// File(
// "/Users/henny/braking/nobrake/accelerometer"), new File(
// "/Users/henny/braking/brake/accelerometer"),
// Accelerometer.class);
package MobileSensors.Test;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.jaxrs.Annotations;

import com.sun.jersey.api.client.Client;

import MobileSensors.Calculation.AcceleroCalc;
import MobileSensors.MobSens;
import MobileSensors.Classifiers.Window;
import MobileSensors.Deprecated.Accelerometer;
import MobileSensors.Storage.Events.Event;
import MobileSensors.Storage.Sensors.Annotation;
import MobileSensors.Test.Data.Recording;
import MobileSensors.Test.Data.SensorE;
import MobileSensors.Test.Data.URLS;
import MobileSensors.Test.Input.CSV;
import MobileSensors.Test.Input.RESTful;
import MobileSensors.Test.Output.Chart;
import MobileSensors.Test.Output.WekaFile;

public class Main {

	private static final String username = "mlukas@gmx.net";
	private static final String password = "12345678";

	public static void main(String[] args) throws IOException {

		// Auf Server einloggen
		Client client = RESTful.login(URLS.LOGIN.getURL(), username, password);
		ArrayList<Recording> recordings = RESTful.recordingOutput(client,
				URLS.LIST_RECORDINGS.getURL()); // + "?page=" + j

		String acceleroCSV = FileUtils.readFileToString(new File(
				"/Users/henny/Desktop/uni/fp/data1/dodge/dodge_2.csv"));
		ArrayList<Accelerometer> accelerometer = CSV.csvToSensor(acceleroCSV,
				Accelerometer.class);
		//
		// Chart.drawChart(new Recording(0, "0"), accelerometer,
		// new ArrayList<Annotation>(), new ArrayList<Event>(), 2, 0,
		// Accelerometer.class);

		boolean schleifeausfuehren = true;
		if (schleifeausfuehren) {
			// Alle Charts aus dem Bremsvorgang-Test
			for (int i = 0; i < recordings.size(); i++) {
				int id = recordings.get(i).getId();

				try {
					acceleroCSV = RESTful
							.getCSV(client, recordings.get(i).getId(),
									URLS.CSV.getURL(), SensorE.ACCELEROMETERS);
				} catch (ConnectException e) {
					client = RESTful.login(URLS.LOGIN.getURL(), username,
							password);
					i--;
					continue;
				}
				int devID = recordings.get(i).getDevice_id();
				if (devID == 12 || devID == 13) {

					// if
					// (recordings.get(i).getTitle().toLowerCase().contains("auswei"))
					// {
					if (acceleroCSV != null) {

						accelerometer = CSV.csvToSensor(acceleroCSV,
								Accelerometer.class);

						AcceleroCalc.accelerometerCalc(accelerometer, true);

						// Chart.drawChart(recordings.get(i), accelerometer,
						// new ArrayList<Annotation>(),
						// new ArrayList<Event>(), 2, 0,
						// Accelerometer.class);

						accelerometer = CSV.csvToSensor(acceleroCSV,
								Accelerometer.class);

						AcceleroCalc.accelerometerCalc(accelerometer, true);

						Collection<Collection<Accelerometer>> accelWindows = Accelerometer
								.window(accelerometer, 3000);

						MobSens c = new MobSens();

						ArrayList<Event> events = new ArrayList<>();
						for (Collection<Accelerometer> accel : accelWindows) {

							Window window = new Window();
							window.setAcceleration((ArrayList<Accelerometer>) accel);

							events.addAll(c.getEvents(window));

						}
						
						System.out.println("Anzahl dodges: " + events.size());
						for (Event ev : events) {
							System.out.println(ev.toString());
						}

					}

					// WekaFile.writeFile(accelerometer, 1000, "Weka.csv");

					// Chart.drawChart(recordings.get(i), accelerometer,
					// new ArrayList<Annotation>(), new ArrayList<Event>(), 2,
					// 0,
					// Accelerometer.class);

				}
			}
		}
		System.out.println("done");
	}
}
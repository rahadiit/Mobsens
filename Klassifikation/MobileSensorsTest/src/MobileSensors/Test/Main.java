package MobileSensors.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import com.sun.jersey.api.client.Client;

import MobileSensors.Storage.Sensors.Accelerometer;
import MobileSensors.Test.Data.Recording;
import MobileSensors.Test.Data.SensorE;
import MobileSensors.Test.Data.URLS;
import MobileSensors.Test.Input.CSV;
import MobileSensors.Test.Input.RESTful;
import MobileSensors.Test.Output.WekaFile;

public class Main {

	private static final String username = "mlukas@gmx.net";
	private static final String password = "12345678";	

	public static void main(String[] args) throws IOException {

		// Auf Server einloggen
		Client client = RESTful.login(URLS.LOGIN.getURL(), username, password);
		ArrayList<Recording> recordings = RESTful.recordingOutput(client,
				URLS.LIST_RECORDINGS.getURL()); // + "?page=" + j

		
		// String acceleroCSV = FileUtils.readFileToString(new
		// File("accelero.csv"));
		// ArrayList<Accelerometer> accelerometer = CSV.csvToSensor(acceleroCSV,
		// Accelerometer.class);

		boolean schleifeausfuehren = false;
		if (schleifeausfuehren) {
			// Alle Charts aus dem Bremsvorgang-Test
			for (int i = 0; i < recordings.size(); i++) {
				int id = recordings.get(i).getId();
				if (id == 170) {
					
					String acceleroCSV = RESTful.getCSV(client,
							recordings.get(i).getId(), URLS.CSV.getURL(),
							SensorE.ACCELEROMETERS);

					ArrayList<Accelerometer> accelerometer = CSV.csvToSensor(
							acceleroCSV, Accelerometer.class);

					WekaFile.writeFile(accelerometer, 1000, "Weka.csv");

				}
			}
		}
		System.out.println("done");
	}
}
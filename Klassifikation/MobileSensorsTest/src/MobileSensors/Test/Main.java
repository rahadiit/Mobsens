package MobileSensors.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.sun.jersey.api.client.Client;

import MobileSensors.Enums.AcceleroOption;
import MobileSensors.Enums.Axis;
import MobileSensors.Storage.Sensors.Accelerometer;
import MobileSensors.Storage.Sensors.Location;
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

		// Chart.drawSingleRecording(170, true, username, password);

		// Alle Charts aus dem Bremsvorgang-Test
		for (int i = 0; i < recordings.size(); i++) {
			int id = recordings.get(i).getId();
			 if (id == 170) {
			//if (recordings.get(i).getTitle().toLowerCase().contains("auswe")) {
				//System.out.println("...processing id: " + id);
				// hier Optionen und Achsen waehlen.

				 
//				Chart.drawSingleRecording(recordings.get(i), true, username,
//						password);
				 
				 String acceleroCSV = RESTful.getCSV(client, recordings.get(i).getId(), URLS.CSV.getURL(),
							SensorE.ACCELEROMETERS);
				 ArrayList<Accelerometer> accelerometer = CSV.csvToSensor(acceleroCSV,
								Accelerometer.class);
				 
				 WekaFile.writeFile(accelerometer, 1000, "Weka.csv");
							
			}
			// }
		}

		// Chart.drawAllRecordings(recordings, username, password);
		// Chart.drawSingleRecording(299, true, username, password);

		System.out.println("done");

	}
}

// ALTER CODE
// + DateFormatUtils.format(new Date(locations.get(0).getTime()),
// "MM-dd HH:mm:ss"));
package MobileSensors.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.sun.jersey.api.client.Client;

import MobileSensors.Storage.Sensors.Location;
import MobileSensors.Test.Data.Recording;
import MobileSensors.Test.Data.SensorE;
import MobileSensors.Test.Data.URLS;
import MobileSensors.Test.Input.CSV;
import MobileSensors.Test.Input.RESTful;
import MobileSensors.Test.Output.Chart;

public class Main {

	private static final String username = "mlukas@gmx.net";
	private static final String password = "12345678";

	public static void main(String[] args) throws IOException {

		for (int j = 1; j < 10; j++) {

			// Auf Server einloggen
			Client client = RESTful.login(URLS.LOGIN.getURL(), username,
					password);
			ArrayList<Recording> recordings = RESTful.recordingOutput(client,
					URLS.LIST_RECORDINGS.getURL() + "?page=2");

			// Chart.drawSingleRecording(170, true, username, password);

			// Alle Charts aus dem Bremsvorgang-Test
			for (int i = 0; i < recordings.size(); i++) {
				int id = recordings.get(i).getId();
				if (id >= 217) {
					System.out.println("...processing id: " + id);
					Chart.drawSingleRecording(recordings.get(i), true,
							username, password);
				}
			}
			// Chart.drawAllRecordings(recordings, username, password);
			// Chart.drawSingleRecording(299, true, username, password);
		}
		System.out.println("done");

	}

	public static void printCalcData(Collection<Location> locations) {
		for (Location location : locations) {
			System.out.println(location.getJerkCalc() + "jerk vs jerkfusion "
					+ location.getJerkFusion());
		}

		for (Location location : locations) {
			System.out.println(location.getSpeed() + " Sensor vs Calc "
					+ location.getSpeedCalcCo());
		}

		for (Location location : locations) {
			System.out
					.println(location.getDistanceCalcCo()
							+ " SensorDist vs CalcDist "
							+ location.getDistanceFusion());
		}

		for (Location location : locations) {
			System.out.println(location.getDistanceSumCalcCo()
					+ " SensorDistSum vs CalcDistSum "
					+ location.getDistanceFusionSum());
		}
	}

	public static void printStartStop(ArrayList<Location> locations) {
		System.out.println("start: "
				+ DateFormatUtils.format(new Date(locations.get(0).getTime()),
						"MM-dd HH:mm:ss"));
		System.out
				.println("stop: "
						+ DateFormatUtils.format(
								new Date(locations.get(locations.size() - 1)
										.getTime()), "HH:mm:ss"));
	}
}
// ALTER CODE

// Die CSV-Dateien zu ArrayLists

// ArrayList<Location> locations = CSV.csvToLocation(new
// File("/Users/henny/Downloads/locspazieren.csv"));

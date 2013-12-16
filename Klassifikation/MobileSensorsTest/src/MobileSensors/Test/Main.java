package MobileSensors.Test;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;

import com.sun.jersey.api.client.Client;

import MobileSensors.Calculation.GPS;
import MobileSensors.Calculation.LocationCalc;
import MobileSensors.Classifiers.DetectBreaking;
import MobileSensors.Classifiers.DetectStanding;
import MobileSensors.Storage.Event.Event;
import MobileSensors.Storage.Sensors.Accelerometer;
import MobileSensors.Storage.Sensors.Annotation;
import MobileSensors.Storage.Sensors.Location;
import MobileSensors.Test.Data.SensorE;
import MobileSensors.Test.Data.URLS;
import MobileSensors.Test.Input.CSV;
import MobileSensors.Test.Input.RESTful;
import MobileSensors.Test.Output.Chart;

public class Main {

	private static final String username = "mlukas@gmx.net";
	private static final String password = "12345678";

	public static void main(String[] args) throws IOException {

		// Auf Server einloggen
		Client client = RESTful.login(URLS.LOGIN.getURL(), username, password);
		int id = 102;

		// Laed verschiedene CSV-Dateien vom Server
		String locationCSV = RESTful.getCSV(client, id, URLS.CSV.getURL(),
				SensorE.LOCATIONS);
		// String annotationCSV = RESTful.getCSV(client, id, URLS.CSV.getURL(),
		// SensorE.ANNOTATIONS);
		// String acceleroCSV = RESTful.getCSV(client, id, URLS.CSV.getURL(),
		// SensorE.ACCELEROMETERS);

		// if (locationCSV!=null&& annotationCSV != null && acceleroCSV != null)
		// {

		// ArrayList<Location> locations = CSV.csvToLocation(new
		// File("/Users/henny/Downloads/locspazieren.csv"));

		ArrayList<Location> locations = CSV.csvToSensor(locationCSV,
				Location.class);

		System.out.println("start: "
				+ DateFormatUtils.format(new Date(locations.get(0).getTime()),
						"MM-dd HH:mm:ss"));
		System.out
				.println("stop: "
						+ DateFormatUtils.format(
								new Date(locations.get(locations.size() - 1)
										.getTime()), "HH:mm:ss"));

		// Die CSV-Dateien zu ArrayLists machen

		LocationCalc.locationCalc(locations);

		for (Location location : locations) {
			System.out.println("jerk" + location.getJerkCalc());
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

		// ArrayList<Annotation> annotations = CSV.csvToSensor(annotationCSV,
		// Annotation.class);
		// ArrayList<Accelerometer> accelerometer = CSV.csvToSensor(
		// acceleroCSV, Accelerometer.class);
		//
		// //Ausfuerung der Event-Erkennung, anschliessend Ausgabe
		//
		// ArrayList<Event> events = new DetectStanding(locations).getEvents();
		// events.addAll(new DetectBreaking(locations).getEvents());
		//
		// /*
		// for (Event event : events) {
		// System.out.println(event.getTime() + " : "
		// + event.getEventType().toString());
		// }*/
		//
		// //Erstellung der Charts. Erst fuer Geschwindigkeit dann Acceleromter
		// //Annotations und Events werden eingeblendet
		// XYPlot speedplot = Chart.speedPlot(locations);
		// Chart.addAnnotations(annotations, speedplot);
		// Chart.addEvents(events, speedplot);
		// JFreeChart speedchart = new JFreeChart(speedplot);
		//
		// ChartUtilities.saveChartAsPNG(new File("speedChart.png"),
		// speedchart, 3840, 1200);
		//
		//
		// XYPlot accelplot = Chart.acceleroPlot(accelerometer);
		//
		// Chart.addAnnotations(annotations, accelplot);
		// Chart.addEvents(events, accelplot);
		// JFreeChart accelchart = new JFreeChart(accelplot);
		//
		// ChartUtilities.saveChartAsPNG(new File("accelChart.png"),
		// accelchart, 3840, 1200);

		System.out.println("done");

	}

	// }
}

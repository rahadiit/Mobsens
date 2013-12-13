package MobileSensors.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;

import com.sun.jersey.api.client.Client;

import MobileSensors.Calculation.GPS;
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
		double lng1=0.13254520707352;
		double lat1=0.87869028221363;
		double lng2=0.13254550430309;
		double lat2=0.87869043039208;
		
		double[] co1= {lng1, lat1};
		double[] co2= {lng2, lat2};
		
		System.out.println(GPS.dist2(0.87869028221363, 0.13254520707352, 0.87869043039208, 0.13254550430309)*1000);
		System.out.println(GPS.distance(co1, co2));

		// Auf Server einloggen
//		Client client = RESTful.login(URLS.LOGIN.getURL(), username, password);
//		int id = 92;
//
//		// Laed verschiedene CSV-Dateien vom Server
////		String locationCSV = RESTful.getCSV(client, id, URLS.CSV.getURL(),
////				SensorE.LOCATIONS);
//		String annotationCSV = RESTful.getCSV(client, id, URLS.CSV.getURL(),
//				SensorE.ANNOTATIONS);
//		String acceleroCSV = RESTful.getCSV(client, id, URLS.CSV.getURL(),
//				SensorE.ACCELEROMETERS);

		

//		if ( annotationCSV != null && acceleroCSV != null) {
//			
//			//Die CSV-Dateien zu ArrayLists machen
////			ArrayList<Location> locations = CSV.csvToSensor(locationCSV,
////					Location.class);
//			
//			ArrayList<Location> locations = CSV.csvToLocation(new File("/Users/henny/Downloads/loc.csv"));
//			
//			
//			ArrayList<Annotation> annotations = CSV.csvToSensor(annotationCSV,
//					Annotation.class);
//			ArrayList<Accelerometer> accelerometer = CSV.csvToSensor(
//					acceleroCSV, Accelerometer.class);
//
//			double distance=0;
//			double speed=0;
//			double acceleration=0;
//			for(int i=1;i<locations.size();i++){
//				Location location  = locations.get(i);
//				Location prevLocation  = locations.get(i-1);
//				
//				double timeDelta = (location.getTime()-prevLocation.getTime())/1000;
//				
//				double singleDistance =GPS.distance(location,prevLocation);
//				
//				System.out.println("time "+timeDelta);
//				System.out.println("distance "+singleDistance);
//				distance += singleDistance;
//				
//				double actualSpeed =GPS.speed(singleDistance, timeDelta);
//				
//				System.out.println("speed: "+actualSpeed);
//				
//				double actualAcceleration = GPS.acceleration(speed, actualSpeed, timeDelta);
//				
//				double jerk = GPS.jerk(actualAcceleration, acceleration, timeDelta);
//				
//				
//				
//				acceleration=actualAcceleration;
//				speed= actualSpeed;
//			}
//			System.out.println("gesamtdistanz: "+distance);
//			
//			
//			//Ausfuerung der Event-Erkennung, anschliessend Ausgabe
//			 
//			ArrayList<Event> events = new DetectStanding(locations).getEvents();
//			events.addAll(new DetectBreaking(locations).getEvents());
//
//			/*
//			for (Event event : events) {
//				System.out.println(event.getTime() + " : "
//						+ event.getEventType().toString());
//			}*/
//
//			//Erstellung der Charts. Erst fuer Geschwindigkeit dann Acceleromter
//			//Annotations und Events werden eingeblendet
//			XYPlot speedplot = Chart.speedPlot(locations);
//			Chart.addAnnotations(annotations, speedplot);
//			Chart.addEvents(events, speedplot);
//			JFreeChart speedchart = new JFreeChart(speedplot);
//
//			ChartUtilities.saveChartAsPNG(new File("speedChart.png"),
//					speedchart, 3840, 1200);
//
//			
//			XYPlot accelplot = Chart.acceleroPlot(accelerometer);
//			
//			Chart.addAnnotations(annotations, accelplot);
//			Chart.addEvents(events, accelplot);
//			JFreeChart accelchart = new JFreeChart(accelplot);
//
//			ChartUtilities.saveChartAsPNG(new File("accelChart.png"),
//					accelchart, 3840, 1200);
//			
//			System.out.println("done");
//
//		}

	}
}

package MobileSensors.Test.Output;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.event.AnnotationChangeListener;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.sun.jersey.api.client.Client;

import MobileSensors.Calculation.AcceleroCalc;
import MobileSensors.Calculation.LocationCalc;
import MobileSensors.Classifiers.DetectBraking;
import MobileSensors.Classifiers.DetectJerk;
import MobileSensors.Classifiers.DetectStanding;
import MobileSensors.Storage.Event.Event;
import MobileSensors.Storage.Event.EventType;
import MobileSensors.Storage.Sensors.Accelerometer;
import MobileSensors.Storage.Sensors.Annotation;
import MobileSensors.Storage.Sensors.Location;
import MobileSensors.Storage.Sensors.Sensor.Sensor;
import MobileSensors.Test.Data.Recording;
import MobileSensors.Test.Data.SensorE;
import MobileSensors.Test.Data.URLS;
import MobileSensors.Test.Input.CSV;
import MobileSensors.Test.Input.RESTful;

public class Chart {

	private final static int X = 0;
	private final static int Y = 1;
	private final static int Z = 2;

	public static void addAnnotations(ArrayList<Annotation> annotations,
			XYPlot plot) {

		// System.out.println("annotations size: " + annotations.size());
		for (int i = 0; i < annotations.size(); i++) {
			Annotation annotation = annotations.get(i);

			// plot.addAnnotation(new XYTextAnnotation(annotation.getTag(),
			// annotation.getTime(), 0.2 + (0.2 * (i % 3))));

			// System.out.println(annotation.getTag());
			plot.addAnnotation(new XYTextAnnotation(annotation.getTag(),
					annotation.getTime(), 3));
		}
	}

	public static void addEvents(ArrayList<Event> events, XYPlot plot) {
		for (int i = 0; i < events.size(); i++) {
			Event event = events.get(i);
			double position = 0.8 + (0.2 * (i % 3));
			plot.addAnnotation(new XYTextAnnotation(event.getEventType()
					.toString(), event.getTime(), position));
		}
	}

	public static XYSeriesCollection dataset(ArrayList<XYSeries> series) {
		XYSeriesCollection result = new XYSeriesCollection();

		for (XYSeries serie : series) {
			result.addSeries(serie);
		}

		return result;
	}

	public static XYPlot acceleroPlot(ArrayList<Accelerometer> values) {
		return acceleroPlot(values, true, true, true, false);
	}

	public static XYPlot acceleroPlot(ArrayList<Accelerometer> values,
			boolean x, boolean y, boolean z, boolean jerk) {

		ArrayList<XYSeries> series = new ArrayList<>();
		if (x) {
			if (jerk)
				series.add(ChartData.accelData("JerkX", values, X, true));
			series.add(ChartData.accelData("X", values, X, false));
		}
		if (y) {
			if (jerk)
				series.add(ChartData.accelData("JerkY", values, Y, true));
			series.add(ChartData.accelData("Y", values, Y, false));
		}
		if (z) {
			if (jerk)
				series.add(ChartData.accelData("JerkZ", values, Z, true));
			series.add(ChartData.accelData("Z", values, Z, false));
		}
		return plot(dataset(series), "time", "speed", values);
	}

	public static XYPlot allSpeedPlot(ArrayList<Location> values) {

		ArrayList<XYSeries> series = new ArrayList<>();
		series.add(ChartData.getSpeed("getSpeed()", values, 0));
		// series.add(ChartData.getSpeed("getSpeedCalcCo()", values, 1));
		// series.add(ChartData.getSpeed("getSpeedFusion()", values, 2));
		series.add(ChartData.getSpeed("getJerk()", values, 3));

		return plot(dataset(series), "time", "speed", values);
	}

	public static XYPlot allDistancePlot(ArrayList<Location> values) {

		ArrayList<XYSeries> series = new ArrayList<>();
		series.add(ChartData.getDistance("getDistanceSumCalcCo()", values, 0));
		series.add(ChartData.getDistance("getDistanceSumCalcGs()", values, 1));
		series.add(ChartData.getDistance("getSpeedFusion()", values, 2));

		return plot(dataset(series), "time", "distance", values);
	}

	public static XYPlot speedPlot(ArrayList<Location> values) {

		ArrayList<XYSeries> series = new ArrayList<>();
		series.add(ChartData.getSpeed("getSpeed()", values));

		return plot(dataset(series), "time", "speed", values);
	}

	public static <T extends Sensor> XYPlot plot(XYSeriesCollection dataset,
			String xAxis, String yAxis, ArrayList<T> values) {
		// XYDotRenderer dot = new XYDotRenderer();
		// dot.setDotHeight(5);
		// dot.setDotWidth(5);
		//
		// return new XYPlot(dataset, new NumberAxis(xAxis),
		// new NumberAxis(yAxis), dot);
		//
		// new XYSplineRenderer()
		XYPlot plot = new XYPlot(dataset, new NumberAxis(xAxis),
				new NumberAxis(yAxis), new XYLineAndShapeRenderer());
		NumberAxis domain = (NumberAxis) plot.getDomainAxis();
		domain.setRange(values.get(0).getTime(), values.get(values.size() - 1)
				.getTime());
		return plot;

	}

	/*
	 * 0 == speed 1 == distance 2 == accelerometer
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Sensor> void drawChart(int id,
			Collection<T> values, ArrayList<Annotation> annotations,
			ArrayList<Event> events, int method, int prefix, Class<T> type) {

		XYPlot plot = null;
		String filename = "";
		int x = 3850;
		int y = 1200;

		if (values.size() > 5) {

			if (method == 0 && type == Location.class) {
				plot = Chart.allSpeedPlot((ArrayList<Location>) values);
				filename = "allSpeedMethodsJerk";
			} else if (method == 1 && type == Location.class) {
				plot = Chart.allDistancePlot((ArrayList<Location>) values);
				filename = "allDistanceMethods";
			} else if (method == 2 && type == Accelerometer.class) {
				AcceleroCalc.accelerometerCalc(
						(Collection<Accelerometer>) values, true);
				// alle achsen
				// plot = Chart.acceleroPlot((ArrayList<Accelerometer>) values);
				// nur x achse
				plot = Chart.acceleroPlot((ArrayList<Accelerometer>) values,
						true, false, false, false);

				filename = "linearAccelerometerValues";
				x = 50000;
			}

			filename = prefix != 0 ? filename + prefix : filename;

			Chart.addAnnotations(annotations, plot);
			Chart.addEvents(events, plot);

			JFreeChart speedchart = new JFreeChart(plot);

			int length = (id + "").length();
			String cutMeOf = "0000";
			int cut = (4 - length) < 0 ? 0 : 4 - length;
			cutMeOf = cutMeOf.substring(0, cut);

			try {
				ChartUtilities.saveChartAsPNG(new File("charts/" + cutMeOf + id
						+ " " + filename + ".png"), speedchart, x, y);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static void drawAllRecordings(Collection<Recording> recordings,
			String username, String password) {

		int i = 1;
		for (Recording recording : recordings) {
			System.out.println("doing " + i++ + " of " + recordings.size());
			int id = recording.getId();
			Chart.drawSingleRecording(id, false, username, password);

		}

	}

	public static void drawSingleRecording(int id, boolean accelero,
			String username, String password) {
		String acceleroCSV = "";
		String locationCSV = "";
		String annotationCSV = "";
		Client client = RESTful.login(URLS.LOGIN.getURL(), username, password);

		// Laed verschiedene CSV-Dateien vom Server
		locationCSV = RESTful.getCSV(client, id, URLS.CSV.getURL(),
				SensorE.LOCATIONS);
		annotationCSV = RESTful.getCSV(client, id, URLS.CSV.getURL(),
				SensorE.ANNOTATIONS);
		if (accelero) {
			acceleroCSV = RESTful.getCSV(client, id, URLS.CSV.getURL(),
					SensorE.LINEAR_ACCELERATIONS);
		}

		if (locationCSV != null && annotationCSV != null && acceleroCSV != null) {

			ArrayList<Location> locations = CSV.csvToSensor(locationCSV,
					Location.class);
			ArrayList<Annotation> annotations = CSV.csvToSensor(annotationCSV,
					Annotation.class);
			ArrayList<Accelerometer> accelerometer = null;
			if (accelero) {
				accelerometer = CSV.csvToSensor(acceleroCSV,
						Accelerometer.class);

			}
			// Berechnungen auf dem Locations-Array
			LocationCalc.locationCalc(locations);

			// Ausfuerung der Event-Erkennung
			ArrayList<Event> events = allEvents(locations);

			Chart.drawChart(id, locations, annotations, events, 0, 0,
					Location.class);
			// Chart.drawChart(id, locations, annotations, events, 1,
			// Location.class);
			if (accelero && !accelerometer.isEmpty()) {

				long from = accelerometer.get(0).getTime();
				long timespan = 1000 * 60 * 2; // 2min
				int i = 1;
				Collection<Accelerometer> accel = new ArrayList<>();
				do {
					System.out.println("drawing accelerometer-chart no. " + i);
					accel = Accelerometer.window(accelerometer, from, from
							+ timespan);

					Chart.drawChart(id, accel, annotations, events, 2, i++,
							Accelerometer.class);

					from += timespan;

				} while (!accel.isEmpty());

			}
		}
	}

	private static ArrayList<Event> allEvents(ArrayList<Location> locations) {

		ArrayList<Event> events = new DetectStanding(locations).getEvents();
		events.addAll(new DetectBraking(locations).getEvents());
		events.addAll(new DetectJerk(locations).getEvents());

		return events;
	}

}

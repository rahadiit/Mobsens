package MobileSensors.Test.Output;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.sun.jersey.api.client.Client;

import MobileSensors.Deprecated.AcceleroCalc;
import MobileSensors.Deprecated.AcceleroOption;
import MobileSensors.Deprecated.Accelerometer;
import MobileSensors.Deprecated.Axis;
import MobileSensors.Deprecated.DetectDodge;
import MobileSensors.Deprecated.DetectJerk;
import MobileSensors.Deprecated.DetectStanding;
import MobileSensors.Deprecated.Location;
import MobileSensors.Deprecated.LocationCalc;
import MobileSensors.Deprecated.Annotation;
import MobileSensors.Deprecated.Sensor;
import MobileSensors.Events.Event;
import MobileSensors.Test.Data.Recording;
import MobileSensors.Test.Data.SensorE;
import MobileSensors.Test.Data.URLS;
import MobileSensors.Test.Input.CSV;
import MobileSensors.Test.Input.RESTful;

@SuppressWarnings("deprecation")
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
					annotation.getTime(), 0));
		}
	}

	public static void addEvents(ArrayList<Event> events, XYPlot plot) {
		for (int i = 0; i < events.size(); i++) {
			Event event = events.get(i);
			double position = 0.1 + (0.2 * (i % 3));
			plot.addAnnotation(new XYTextAnnotation(event.getEventType()
					.toString(), event.getStartTime(), position));
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
		Collection<Axis> axis = new ArrayList<>();
		Collection<AcceleroOption> options = new ArrayList<>();
		
		axis.add(Axis.X);
		axis.add(Axis.Y);
		axis.add(Axis.Z);
		
		options.add(AcceleroOption.PLAIN);
		
		return acceleroPlot(values, axis, options);
	}

	public static XYPlot acceleroPlot(ArrayList<Accelerometer> values,
			Collection<Axis> axis, Collection<AcceleroOption> options) {

		ArrayList<XYSeries> series = new ArrayList<>();

		for (Axis a : axis) {
			for (AcceleroOption o : options) {
				series.add(ChartData.accelData(values, a, o));
			}
		}

		return plot(dataset(series), "Time", "Accelerometer Value", values);
	}

	public static XYPlot allSpeedPlot(ArrayList<Location> values) {

		ArrayList<XYSeries> series = new ArrayList<>();
		series.add(ChartData.getSpeed("getSpeed()", values, 0));
		// series.add(ChartData.getSpeed("getSpeedCalcCo()", values, 1));
		// series.add(ChartData.getSpeed("getSpeedFusion()", values, 2));
		//series.add(ChartData.getSpeed("getJerk()", values, 3));

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
		NumberAxis xDomain = (NumberAxis) plot.getDomainAxis();
		ValueAxis yDomain = plot.getRangeAxis();

		if (yAxis.toLowerCase().startsWith("accelero")) {

			 yDomain.setRange(-6, 6);

		}
		xDomain.setRange(values.get(0).getTime(), values.get(values.size() - 1)
				.getTime());

		return plot;

	}

	/*
	 * 0 == speed 1 == distance 2 == accelerometer
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Sensor> void drawChart(Recording id,
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

				// alle achsen
				// plot = Chart.acceleroPlot((ArrayList<Accelerometer>) values);
				// nur x achse
				
				Collection<Axis> axis = new ArrayList<>();
				Collection<AcceleroOption> options = new ArrayList<>();
				axis.add(Axis.X);
				options.add(AcceleroOption.MEAN_SHORT);
				//options.add(AcceleroOption.MEAN_LONG);
				options.add(AcceleroOption.DIFFERENCE);
				options.add(AcceleroOption.PLAIN);
				
				
				
				plot = Chart.acceleroPlot((ArrayList<Accelerometer>) values,
						axis,options);

				filename = "linearAccelerometerValues";
				x = 50000;

				x = values.size() * 6;
				x = x > 50000 ? 50000 : x;
			}

			filename = prefix != 0 ? filename + prefix : filename;

			Chart.addAnnotations(annotations, plot);
			Chart.addEvents(events, plot);

			JFreeChart speedchart = new JFreeChart(plot);

			int length = (id.getId() + "").length();
			String cutMeOf = "0000";
			int cut = (4 - length) < 0 ? 0 : 4 - length;
			cutMeOf = cutMeOf.substring(0, cut);

			try {
				ChartUtilities.saveChartAsPNG(
						new File("charts/" + cutMeOf + id.getId() + " "
								+ id.getTitle() + " " + filename + ".png"),
						speedchart, x, y);
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
			Chart.drawSingleRecording(recording, false, username, password);

		}

	}
	
	public static void drawSingleRecording(String acceleroCSV){
		
		
	}

	public static void drawSingleRecording(Recording id, boolean accelero,
			String username, String password) {
		String acceleroCSV = "";
		String locationCSV = "";
		String annotationCSV = "";
		Client client = RESTful.login(URLS.LOGIN.getURL(), username, password);

		// Laed verschiedene CSV-Dateien vom Server
		locationCSV = RESTful.getCSV(client, id.getId(), URLS.CSV.getURL(),
				SensorE.LOCATIONS);
		annotationCSV = RESTful.getCSV(client, id.getId(), URLS.CSV.getURL(),
				SensorE.ANNOTATIONS);
		if (accelero) {
			acceleroCSV = RESTful.getCSV(client, id.getId(), URLS.CSV.getURL(),
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
				AcceleroCalc.accelerometerCalc(
						(Collection<Accelerometer>) accelerometer, true);

			}
			// Berechnungen auf dem Locations-Array
			LocationCalc.locationCalc(locations);

			// Ausfuerung der Event-Erkennung
			ArrayList<Event> events = new ArrayList<>();
			if (accelero)
				events = allEvents(locations, accelerometer);
			else
				events = allEvents(locations);

			Chart.drawChart(id, locations, annotations, events, 0, 0,
					Location.class);
			// Chart.drawChart(id, locations, annotations, events, 1,
			// Location.class);
			if (accelero && !accelerometer.isEmpty()) {

				long timespanAfter = 5000; // 3sek
				long timespanBefore = 3000; // 0.5sek

				int i = 1;
//				for (Event ev : events) {
//
//					if (ev.getEventType() == EventType.DODGE) {
//
//						Collection<Accelerometer> afterDodge = Accelerometer
//								.window(accelerometer, ev.getTime()
//										- timespanBefore, ev.getTime()
//										+ timespanAfter);
//
//						Chart.drawChart(id, afterDodge, annotations, events, 2,
//								i++, Accelerometer.class);
//
//					}
//				}

				long timespan = 1000 * 60 * 2; // 2min

				Collection<Collection<Accelerometer>> windows = Accelerometer.window(accelerometer, timespan);

				int j = i + 1;
				for (Collection<Accelerometer> accel : windows) {
					System.out.println("drawing accelerometer-chart no. " + j);
					Chart.drawChart(id, accel, annotations, events, 2, j++,
							Accelerometer.class);
				}

				// long from = accelerometer.get(0).getTime();
				// int i = 1;
				// Collection<Accelerometer> accel = new ArrayList<>();
				// do {
				// System.out.println("drawing accelerometer-chart no. " + i);
				// accel = Accelerometer.window(accelerometer, from, from
				// + timespan);
				//
				// Chart.drawChart(id, accel, annotations, events, 2, i++,
				// Accelerometer.class);
				//
				// from += timespan;
				//
				// } while (!accel.isEmpty());

			}
		}
	}

	/*
	 * long time = System.currentTimeMillis(); events.addAll(new
	 * DetectDodge(locations).getEvents());
	 */

	private static ArrayList<Event> allEvents(ArrayList<Location> locations,
			ArrayList<Accelerometer> accelerometer) {
		ArrayList<Event> events = allEvents(locations);
		long time = System.currentTimeMillis();
		events.addAll(new DetectDodge(accelerometer).getEvents());
		System.out.println("detectDodge " + (System.currentTimeMillis() - time)
				+ "ms.");

		return events;
	}

	private static ArrayList<Event> allEvents(ArrayList<Location> locations) {

		ArrayList<Event> events = new DetectStanding(locations).getEvents();
//		events.addAll(new BrakingClassifier(locations).getEvents());
		events.addAll(new DetectJerk(locations).getEvents());

		return events;
	}

}

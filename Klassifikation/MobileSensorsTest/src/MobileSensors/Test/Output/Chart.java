package MobileSensors.Test.Output;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;

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

import MobileSensors.Storage.Event.Event;
import MobileSensors.Storage.Event.EventType;
import MobileSensors.Storage.Sensors.Accelerometer;
import MobileSensors.Storage.Sensors.Annotation;
import MobileSensors.Storage.Sensors.Location;
import MobileSensors.Storage.Sensors.Sensor.Sensor;

public class Chart {

	private final static int X = 0;
	private final static int Y = 1;
	private final static int Z = 2;

	public static void addAnnotations(ArrayList<Annotation> annotations,
			XYPlot plot) {

		for (int i = 0; i < annotations.size(); i++) {
			Annotation annotation = annotations.get(i);

			plot.addAnnotation(new XYTextAnnotation(annotation.getTag(),
					annotation.getTime(), 0.2 + (0.2 * (i % 3))));
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

		ArrayList<XYSeries> series = new ArrayList<>();
		series.add(ChartData.accelData("X", values, X));
		series.add(ChartData.accelData("Y", values, Y));
		series.add(ChartData.accelData("Z", values, Z));

		return plot(dataset(series), "time", "speed", values);
	}

	public static XYPlot allSpeedPlot(ArrayList<Location> values) {

		ArrayList<XYSeries> series = new ArrayList<>();
		series.add(ChartData.getSpeed("getSpeed()", values, 0));
		series.add(ChartData.getSpeed("getSpeedCalcCo()", values, 1));
		series.add(ChartData.getSpeed("getSpeedFusion()", values, 2));

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
		XYPlot plot = new XYPlot(dataset, new NumberAxis(xAxis),
				new NumberAxis(yAxis), new XYSplineRenderer());
		NumberAxis domain = (NumberAxis) plot.getDomainAxis();
		domain.setRange(values.get(0).getTime(), values.get(values.size() - 1)
				.getTime());
		return plot;

	}

}

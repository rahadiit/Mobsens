package mobsens.classification;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;

import com.sun.jersey.api.client.Client;

import mobsens.classification.data.Annotation;
import mobsens.classification.data.Location;
import mobsens.classification.data.SensorE;
import mobsens.classification.data.URLS;
import mobsens.classification.input.CSV;
import mobsens.classification.input.RESTful;
import mobsens.classification.output.Chart;

public class Main {

	private static final String username = "mlukas@gmx.net";
	private static final String password = "12345678";

	public static void main(String[] args) throws IOException {

		Client client = RESTful.login(URLS.LOGIN, username, password);
		// ArrayList<Recording> recordings =
		// RESTful.recordingOutput(client,URLS.LIST_RECORDINGS.getURL());

		int id=47;
		String locationCSV = RESTful.getCSV(client, id, URLS.CSV,
				SensorE.LOCATIONS);
		String annotationCSV = RESTful.getCSV(client, id, URLS.CSV,
				SensorE.ANNOTATIONS);
		

		if (locationCSV != null) {
			ArrayList<Location> csvTest = CSV.csvToLocation(locationCSV);
			ArrayList<Annotation> annotations = CSV.csvToAnnotation(annotationCSV);
			XYPlot plot = Chart.speedPlot(csvTest);
			Chart.addAnnotations(annotations, plot);
			
			JFreeChart chart = new JFreeChart(plot);
			
			ChartUtilities.saveChartAsPNG(new File("chart.png"), chart, 1024, 768);
		}

	}

}

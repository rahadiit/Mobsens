package MobileSensors.Test;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;

import com.sun.jersey.api.client.Client;

import MobileSensors.Classifiers.DetectBreaking;
import MobileSensors.Classifiers.DetectStanding;
import MobileSensors.Storage.Event.Event;
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
	
	public static void main(String[] args) throws IOException{
		
		Client client = RESTful.login(URLS.LOGIN.getURL(), username, password);
		int id=47;
		String locationCSV = RESTful.getCSV(client, id, URLS.CSV.getURL(),
				SensorE.LOCATIONS);
		String annotationCSV = RESTful.getCSV(client, id, URLS.CSV.getURL(),
				SensorE.ANNOTATIONS);
		
		if(locationCSV!=null){
			ArrayList<Location> locations = CSV.csvToSensor(locationCSV,Location.class);
			ArrayList<Annotation> annotations = CSV.csvToSensor(annotationCSV,Annotation.class);
			
			
			ArrayList<Event> events = new DetectStanding(locations).getEvents();
			events.addAll(new DetectBreaking(locations).getEvents());
			
			for(Event event:events){
				System.out.println(event.getTime()+" : "+event.getEventType().toString());
			}
			
			XYPlot plot = Chart.speedPlot(locations);
			Chart.addAnnotations(annotations, plot);
			Chart.addEvents(events, plot);
			
			JFreeChart chart = new JFreeChart(plot);
			
			ChartUtilities.saveChartAsPNG(new File("chart.png"), chart, 1024, 768);
		}
		
	}
}

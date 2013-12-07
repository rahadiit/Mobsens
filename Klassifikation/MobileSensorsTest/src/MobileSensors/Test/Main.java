package MobileSensors.Test;



import java.util.ArrayList;

import com.sun.jersey.api.client.Client;

import MobileSensors.Classifiers.DetectStanding;
import MobileSensors.Storage.Event.Event;
import MobileSensors.Storage.Sensors.Location;
import MobileSensors.Test.Data.SensorE;
import MobileSensors.Test.Data.URLS;
import MobileSensors.Test.Input.CSV;
import MobileSensors.Test.Input.RESTful;

public class Main {
	
	private static final String username = "mlukas@gmx.net";
	private static final String password = "12345678";
	
	public static void main(String[] args){
		
		Client client = RESTful.login(URLS.LOGIN.getURL(), username, password);
		int id=47;
		String locationCSV = RESTful.getCSV(client, id, URLS.CSV.getURL(),
				SensorE.LOCATIONS);
		
		if(locationCSV!=null){
			ArrayList<Location> locations = CSV.csvToLocation(locationCSV);
			ArrayList<Event> events = new DetectStanding(locations).getEvents();
			
			for(Event event:events){
				System.out.println(event.getTime()+" : "+event.getEventType().toString());
			}
		}
		
	}
}

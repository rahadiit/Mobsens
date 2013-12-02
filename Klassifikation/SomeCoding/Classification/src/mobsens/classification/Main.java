package mobsens.classification;

import java.util.ArrayList;

import com.sun.jersey.api.client.Client;

import mobsens.classification.data.Location;
import mobsens.classification.data.Recording;
import mobsens.classification.data.Sensor;
import mobsens.classification.data.URLS;
import mobsens.classification.input.CSV;
import mobsens.classification.input.RESTful;
import mobsens.classification.output.PrettyPrint;

public class Main {

	private static final String username = "mlukas@gmx.net";
	private static final String password = "12345678";

	public static void main(String[] args) {
		
		Client client = RESTful.login(URLS.LOGIN.getURL(), username, password);
		ArrayList<Recording> recordings = RESTful.recordingOutput(client,URLS.LIST_RECORDINGS.getURL());

		for (Recording rec : recordings) {
			client = RESTful.login(URLS.LOGIN.getURL(), username, password);
			String csv=RESTful.getCSV(client, rec.getId(), URLS.CSV.getURL(),Sensor.LOCATIONS);
			
			if(csv!=null){
				ArrayList<Location> csvTest = CSV.csvToLocation(csv);
				PrettyPrint.print(csvTest);
			}
		}
		
	}
	
}

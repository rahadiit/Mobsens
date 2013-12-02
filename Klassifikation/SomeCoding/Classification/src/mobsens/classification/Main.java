package mobsens.classification;

import java.io.File;
import java.util.ArrayList;

import com.sun.jersey.api.client.Client;

import mobsens.classification.data.Location;
import mobsens.classification.data.Recording;
import mobsens.classification.data.Sensor;
import mobsens.classification.data.URLS;
import mobsens.classification.input.CSV;
import mobsens.classification.input.RESTFul;
import mobsens.classification.output.PrettyPrint;

public class Main {

	private static final String username = "mlukas@gmx.net";
	private static final String password = "12345678";
	
	public static void main(String[] args) {

		ArrayList<Location> locations = CSV.csvToLocation(new File("locSpazieren.csv"));
		PrettyPrint.print(locations);

		Client client = RESTFul.login(URLS.LOGIN.getURL(), username, password);

		ArrayList<Recording> recordings = RESTFul.recordingOutput(client, URLS.LIST_RECORDINGS.getURL());
		
		for(Recording rec: recordings){
			rec.prettyPrint();
		}
		
		System.out.println(RESTFul.getCSV(client, 7,URLS.CSV.getURL(),Sensor.ACCELEROMETERS ));
		
	}
}

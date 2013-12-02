package mobsens.classification;

import java.io.File;
import java.util.ArrayList;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import mobsens.classification.data.Location;
import mobsens.classification.data.Recording;
import mobsens.classification.input.CSV;
import mobsens.classification.input.RESTFul;
import mobsens.classification.output.PrettyPrint;

public class Main {

	public static void main(String[] args) {

		ArrayList<Location> locations = CSV.csvToLocation(new File(
				"locSpazieren.csv"));
		PrettyPrint.print(locations);

		String URL_LOGIN = "http://mobilesensing.west.uni-koblenz.de/users/sign_in.json";
		String URL_DATA = "http://mobilesensing.west.uni-koblenz.de/recordings.json";
		
		String username = "mlukas@gmx.net";
		String password = "12345678";

		Client client = RESTFul.login(URL_LOGIN, username, password);

		ArrayList<Recording> recordings = RESTFul.recordingOutput(client, URL_DATA);
		
		for(Recording rec: recordings){
			rec.prettyPrint();
		}
		
	}
}

// $ curl -v -H "Accept: application/json" -H "Content-type: application/json"
// -c cookies.txt -X POST --data
// '{"user":{"email":"your@ema.il","password":"yourpassword"}}'
// 'http://localhost:3000/users/sign_in.json'
//
// IntentUpload.startService(Controller.this, file.getName(),
// "http://mobilesensing.west.uni-koblenz.de/users/sign_in.json",
// "mlukas@gmx.net", "12345678",
// "http://mobilesensing.west.uni-koblenz.de/recordings/upload", file,
// "application/text", "*/*", true);


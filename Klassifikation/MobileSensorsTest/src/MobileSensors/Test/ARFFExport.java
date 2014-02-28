package MobileSensors.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import MobileSensors.Storage.Sensors.Accelerometer;
import MobileSensors.Helpers.AccelerationFeatureVector;
import MobileSensors.Testtt.Data.Recording;
import MobileSensors.Testtt.Data.SensorE;
import MobileSensors.Testtt.Data.URLS;
import MobileSensors.Testtt.Input.CSV;
import MobileSensors.Testtt.Input.RESTful;

import com.sun.jersey.api.client.Client;

public class ARFFExport {

	private static final String username = "mlukas@gmx.net";
	private static final String password = "12345678";

	public static void main(String[] args) throws IOException {

		// Auf Server einloggen
		Client client = RESTful.login(URLS.LOGIN.getURL(), username, password);
		ArrayList<Recording> recordings = RESTful.recordingOutput(client,
				URLS.LIST_RECORDINGS.getURL()); // + "?page=" + j

		// Alle Charts aus dem Bremsvorgang-Test
		
		System.out.println("Generating " + recordings.size() + " ARFF files");
		
		for (int i = 0; i < recordings.size(); i++) {
			int id = recordings.get(i).getId();
			
			client = RESTful.login(URLS.LOGIN.getURL(), username, password);
			
			if (true) {
				
				 String acceleroCSV = RESTful.getCSV(client, recordings.get(i).getId(), URLS.CSV.getURL(),
							SensorE.ACCELEROMETERS);
				 
				 ArrayList<Accelerometer> accelerometer = CSV.csvToSensor(acceleroCSV,
								Accelerometer.class);
				 
			
				 
				 ArrayList<AccelerationFeatureVector> windows = new ArrayList<AccelerationFeatureVector>();
				 
//				 System.out.println(recordings.get(i).getTitle());
				 
				 String name = recordings.get(i).getId() + "-" + recordings.get(i).getTitle().replace(' ', '-');
				 
				 name.replace('\n', '-');
				 
				 String path = "./arff/" + name + "-accelerometer.arff";
				 
				 BufferedWriter bw = new BufferedWriter(new FileWriter(path));
				 
				 bw.write("@RELATION acc");
				 bw.newLine();
				 
				 bw.write("@ATTRIBUTE xArithMean NUMERIC");
				 bw.newLine();
				 bw.write("@ATTRIBUTE yArithMean NUMERIC");
				 bw.newLine();
				 bw.write("@ATTRIBUTE zArithMean NUMERIC");
				 bw.newLine();
				 
				 bw.write("@ATTRIBUTE xHarmMean NUMERIC");
				 bw.newLine();
				 bw.write("@ATTRIBUTE yHarmMean NUMERIC");
				 bw.newLine();
				 bw.write("@ATTRIBUTE zHarmMean NUMERIC");
				 bw.newLine();
				 
				 bw.write("@ATTRIBUTE xVariance NUMERIC");
				 bw.newLine();
				 bw.write("@ATTRIBUTE yVariance NUMERIC");
				 bw.newLine();
				 bw.write("@ATTRIBUTE zVariance NUMERIC");
				 bw.newLine();
				 
				 bw.write("@ATTRIBUTE xKurtosis NUMERIC");
				 bw.newLine();
				 bw.write("@ATTRIBUTE yKurtosis NUMERIC");
				 bw.newLine();
				 bw.write("@ATTRIBUTE zKurtosis NUMERIC");
				 bw.newLine();
				 
				 bw.write("@DATA");
				 bw.newLine();
				 
				 int width = 1000;
				 for (int m=0; m < accelerometer.size() - width; m++) {
					 
					 ArrayList<Accelerometer> window = new ArrayList<Accelerometer>();
					 
					 for (int n=m; n < width+m; n++) {
						 
						 window.add(accelerometer.get(n));
						 
					 }
					 
					 
					 AccelerationFeatureVector afv = new AccelerationFeatureVector(window, "lala");
					 
					 String data = "";
					 
					 data += afv.getXArithMean() + ",";
					 data += afv.getYArithMean() + ",";
					 data += afv.getZArithMean() + ",";
					 
					 data += afv.getXHarmMean() + ",";
					 data += afv.getYHarmMean() + ",";
					 data += afv.getZHarmMean() + ",";
					 
					 data += afv.getXVariance() + ",";
					 data += afv.getYVariance() + ",";
					 data += afv.getZVariance() + ",";
					 
					 data += afv.getXKurtosis() + ",";
					 data += afv.getYKurtosis() + ",";
					 data += afv.getZKurtosis();
					 
//					 System.out.println(data);
					 bw.write(data);
					 bw.newLine();
					 
					 
				 }
				 
				 bw.flush();
				 bw.close();
				 
				 System.out.println(name);
							
			}
		}
	}

}

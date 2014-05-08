package MobileSensors.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

<<<<<<< HEAD
import MobileSensors.Deprecated.AccelerationFeatureVector;
import MobileSensors.Deprecated.Accelerometer;
=======
import MobileSensors.Storage.Sensors.Accelerometer;
import MobileSensors.Deprecated.AccelerationFeatureVector;
>>>>>>> 9236f9aeb12d23f8ef69f38253c5ee8307eaa162
import MobileSensors.Test.Input.CSV;
import MobileSensors.Test.Output.ArffFile;

public class GenerateArff {
<<<<<<< HEAD

	final String AttrType = "NUMERIC";

=======
	
	final String AttrType = "NUMERIC";
	
>>>>>>> 9236f9aeb12d23f8ef69f38253c5ee8307eaa162
	private static String arffOut = "C:\\Users\\darjeeling\\Desktop\\dodge\\Dodge.arff";
	private static String pathLabel1 = "C:\\Users\\darjeeling\\Desktop\\dodge\\dodge";
	private static String label1 = "DODGE";
	private static String pathLabel2 = "C:\\Users\\darjeeling\\Desktop\\dodge\\nododge";
	private static String label2 = "NODODGE";

	private static int delta = 1;
	private static int windowSize = 100;
<<<<<<< HEAD

	public static void main(String[] args) throws IOException {

		String[] labels = { label1, label2 };

		ArffFile out = new ArffFile(arffOut, (new File(arffOut)).getName(),
				labels);

		out.addAttribute("xArithMean", "NUMERIC");
		out.addAttribute("yArithMean", "NUMERIC");
		out.addAttribute("zArithMean", "NUMERIC");

		out.addAttribute("xHarmMean", "NUMERIC");
		out.addAttribute("yHarmMean", "NUMERIC");
		out.addAttribute("zHarmMean", "NUMERIC");

		out.addAttribute("xVariance", "NUMERIC");
		out.addAttribute("yVariance", "NUMERIC");
		out.addAttribute("zVariance", "NUMERIC");

		out.addAttribute("xKurtosis", "NUMERIC");
		out.addAttribute("yKurtosis", "NUMERIC");
		out.addAttribute("zKurtosis", "NUMERIC");

		ReadDirs(out, pathLabel1, label1);
		ReadDirs(out, pathLabel2, label2);

		out.write();
		System.out.println(" o ");
		System.out.println("-|-");
		System.out.println("/ \\");
	}

	public static void ReadDirs(ArffFile out, String path, String label)
			throws IOException {
		File dir1 = new File(path);

		for (File csvFile : dir1.listFiles()) {
			if (csvFile.getPath().endsWith(".csv") && !csvFile.isDirectory()) {
				String acceleroCSV = FileUtils.readFileToString(csvFile);
				ArrayList<Accelerometer> accelerometer = CSV.csvToSensor(
						acceleroCSV, Accelerometer.class);
				// GENERATE WINDOW FROM accelerometer
=======
	
	public static void main(String[] args) throws IOException{
		
		String[] labels = {label1, label2};
		
		ArffFile out = new ArffFile(arffOut, (new File(arffOut)).getName(), labels);
		
		
		out.addAttribute("xArithMean", "NUMERIC");
		out.addAttribute("yArithMean", "NUMERIC");
		out.addAttribute("zArithMean", "NUMERIC");
		
		out.addAttribute("xHarmMean", "NUMERIC");
		out.addAttribute("yHarmMean", "NUMERIC");
		out.addAttribute("zHarmMean", "NUMERIC");
		
		out.addAttribute("xVariance", "NUMERIC");
		out.addAttribute("yVariance", "NUMERIC");
		out.addAttribute("zVariance", "NUMERIC");
		
		out.addAttribute("xKurtosis", "NUMERIC");
		out.addAttribute("yKurtosis", "NUMERIC");
		out.addAttribute("zKurtosis", "NUMERIC");
		
		
		ReadDirs(out, pathLabel1, label1);
		ReadDirs(out, pathLabel2, label2);
		
		out.write();
		System.out.println(" o ");
		System.out.println("-|-");
 		System.out.println("/ \\");
	}
	
	public static void ReadDirs(ArffFile out, String path, String label) throws IOException{
		File dir1 = new File(path);
		
		for (File csvFile : dir1.listFiles()) {
			if (csvFile.getPath().endsWith(".csv") &&! csvFile.isDirectory()){
				String acceleroCSV = FileUtils
						.readFileToString(csvFile);
				ArrayList<Accelerometer> accelerometer =  CSV.csvToSensor(acceleroCSV,
						Accelerometer.class);
				//GENERATE WINDOW FROM accelerometer
>>>>>>> 9236f9aeb12d23f8ef69f38253c5ee8307eaa162
				GenerateWindow(out, accelerometer, label);
				System.out.println(csvFile.getPath());
			}
		}
	}
<<<<<<< HEAD

	public static void GenerateWindow(ArffFile arffFile,
			ArrayList<Accelerometer> accelerometer, String label) {

		for (int i = 0; i < accelerometer.size() - windowSize; i += delta) {
			ArrayList<Accelerometer> window = new ArrayList<Accelerometer>();
			for (int j = i; j < windowSize + i; j++) {

				if (accelerometer.get(j) != null) {

					window.add(accelerometer.get(j));

				}
			}
			
			// calculate features
			AccelerationFeatureVector afv = new AccelerationFeatureVector(
					window, label);

=======
	public static void GenerateWindow(ArffFile arffFile, ArrayList<MobileSensors.Sensors.Accelerometer> accelerometer, String label){

		for (int i = 0; i<accelerometer.size()-windowSize; i+= delta){
			ArrayList<MobileSensors.Sensors.Accelerometer> window = new ArrayList<>();
			for (int j = i; j<windowSize+i; j++){
				
				if (accelerometer.get(j) != null) {
					
					window.add(accelerometer.get(j));
					
				}
				
				
				
				
			}
			//calculate features
			AccelerationFeatureVector afv = new AccelerationFeatureVector(window, label);
			
>>>>>>> 9236f9aeb12d23f8ef69f38253c5ee8307eaa162
			ArrayList<Double> v = new ArrayList<Double>();
			v.add((Double) afv.getXArithMean());
			v.add((Double) afv.getYArithMean());
			v.add((Double) afv.getZArithMean());

			v.add((Double) afv.getXHarmMean());
			v.add((Double) afv.getYHarmMean());
			v.add((Double) afv.getZHarmMean());

			v.add((Double) afv.getXVariance());
			v.add((Double) afv.getYVariance());
			v.add((Double) afv.getZVariance());

			v.add((Double) afv.getXKurtosis());
			v.add((Double) afv.getYKurtosis());
			v.add((Double) afv.getZKurtosis());
<<<<<<< HEAD

			String featureVector = StringUtils.join(v, ",") + "," + label;

			if (featureVector.contains("NaN")) {

				System.out.println(window);
				System.exit(0);

			}

			arffFile.addFeatureVector(featureVector);

=======
			
			String featureVector = StringUtils.join(v, ",") + "," + label;
			
			if (featureVector.contains("NaN")) {
				
				System.out.println(window);
				System.exit(0);
				
			}
			
			arffFile.addFeatureVector(featureVector);
			
>>>>>>> 9236f9aeb12d23f8ef69f38253c5ee8307eaa162
		}
	}
	// Read all csv

	// produce windows for each csv
	// label in arff

}

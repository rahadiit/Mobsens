package MobileSensors.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import MobileSensors.Storage.Sensors.Accelerometer;
import MobileSensors.Helpers.AccelerationFeatureVector;
import MobileSensors.Testtt.Input.CSV;
import MobileSensors.Testtt.Output.ArffFile;

public class GenerateArff {
	
	final String AttrType = "NUMERIC";
	
	private static String arffOut = "C:\\Users\\darjeeling\\Desktop\\dodge\\Dodge.arff";
	private static String pathLabel1 = "C:\\Users\\darjeeling\\Desktop\\dodge\\dodge";
	private static String label1 = "DODGE";
	private static String pathLabel2 = "C:\\Users\\darjeeling\\Desktop\\dodge\\nododge";
	private static String label2 = "NODODGE";

	private static int delta = 1;
	private static int windowSize = 100;
	
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
				ArrayList<Accelerometer> accelerometer = CSV.csvToSensor(acceleroCSV,
						Accelerometer.class);
				//GENERATE WINDOW FROM accelerometer
				GenerateWindow(out, accelerometer, label);
				System.out.println(csvFile.getPath());
			}
		}
	}
	public static void GenerateWindow(ArffFile arffFile, ArrayList<Accelerometer> accelerometer, String label){

		for (int i = 0; i<accelerometer.size()-windowSize; i+= delta){
			ArrayList<Accelerometer> window = new ArrayList<Accelerometer>();
			for (int j = i; j<windowSize+i; j++){
				
				if (accelerometer.get(j) != null) {
					
					window.add(accelerometer.get(j));
					
				}
				
				
				
				
			}
			//calculate features
			AccelerationFeatureVector afv = new AccelerationFeatureVector(window, label);
			
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
			
			String featureVector = StringUtils.join(v, ",") + "," + label;
			
			if (featureVector.contains("NaN")) {
				
				System.out.println(window);
				System.exit(0);
				
			}
			
			arffFile.addFeatureVector(featureVector);
			
		}
	}
	// Read all csv

	// produce windows for each csv
	// label in arff

}

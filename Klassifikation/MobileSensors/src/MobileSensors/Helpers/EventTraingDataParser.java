package MobileSensors.Helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;

import MobileSensors.Events.Labels.EventLabel;
import MobileSensors.Sensors.SensorCollection;
import MobileSensors.Sensors.CSVParsers.AccelerometerCSVParser;
import MobileSensors.Sensors.CSVParsers.LocationCSVParser;

/**
 * 
 * EventTrainingDataParser
 * 
 * - input_dir
 * 		- label_dir
 * 			- record_dir
 * 				- senor.csv
 * 
 * 
 * 
 * 
 * @author henny, thomas, max
 *
 */
public class EventTraingDataParser {
	
	/**
	 * 
	 * @author darjeeling
	 *
	 */
	private class LabelDirFilter implements FileFilter {

		private ArrayList<String> labels;
		
		public LabelDirFilter (ArrayList<String> labels) {
			
			this.labels = labels;
			
		}
		
		@Override
		public boolean accept(File file) {
			
			return file.isDirectory() && labels.contains(file.getName());
			
		}
		
	}
	
	/**
	 * 
	 * @author darjeeling
	 *
	 */
	private class RecordDirFilter implements FileFilter {

		private String pattern = "^record_\\d+";
		
		@Override
		public boolean accept(File file) {
			
			return file.isDirectory() && file.getName().matches(this.pattern);
			
		}
		
	}
	

	
	private File inputDir;
	private EventLabel[] eventLabels;
	
	public EventTraingDataParser (File inputDir, EventLabel[] eventLabels) {
		
		this.inputDir = inputDir;
		this.eventLabels = eventLabels;
		
	}
	
	public HashMap<EventLabel, ArrayList<SensorCollection>> parse () {
		
		HashMap<EventLabel, ArrayList<SensorCollection>> result = new HashMap<EventLabel, ArrayList<SensorCollection>>();
		
		for (EventLabel label : this.eventLabels) {
			
			ArrayList<String> strLabels = new ArrayList<String>();
			strLabels.add(label.toString().toLowerCase());
			
			for (File labelDir : this.inputDir.listFiles(new LabelDirFilter(strLabels))) {
				
				
				result.put(label, this.parseLabelDir(labelDir));
				
			}
			
		}
		
		return result;
		
	}
	
	private ArrayList<SensorCollection> parseLabelDir (File labelDir) {
		
		ArrayList<SensorCollection> result = new ArrayList<SensorCollection>();
		
		for (File recordDir : labelDir.listFiles(new RecordDirFilter())) {
			
						
			result.add(this.parseRecordDir(recordDir));
			
		}
		
		return result;
		
	}
	
	private SensorCollection parseRecordDir (File recordDir) {
		
		SensorCollection result = new SensorCollection();
		
		try {
			
			for (File sensorFile : recordDir.listFiles()) {

				
				
				if (sensorFile.getName().equals("accelerometer.csv")) {
										
					AccelerometerCSVParser accp = new AccelerometerCSVParser();
					
					result.setAcceleration(accp.parse(new BufferedReader(new FileReader(sensorFile))));
					
				}
				else if (sensorFile.getName().equals("location.csv")) {
					
					LocationCSVParser locp = new LocationCSVParser();
					
					result.setLocation(locp.parse(new BufferedReader(new FileReader(sensorFile))));
					
				}
				
			}
			
		}
		catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		return result;
		
		
	}
	
}

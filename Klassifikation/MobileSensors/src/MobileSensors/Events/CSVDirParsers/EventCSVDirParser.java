package MobileSensors.Events.CSVDirParsers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import MobileSensors.Events.Labels.EventLabel;
import MobileSensors.Sensors.SensorCollection;

public class EventCSVDirParser<L extends EventLabel> {

		
	private File dir;
	
	public EventCSVDirParser (File dir) {
		
		this.dir = dir;
		
	}
	
	private 
	
	public Map<SensorCollection, L> parse () {
		
		Map<SensorCollection, L> result = new HashMap<SensorCollection, L>();
		
		if (this.dir.exists() && this.dir.isDirectory()) {
			
			for (File file : dir.listFiles()) {
				
				SensorCollection sc = new SensorCollection();
				
				if (file.isDirectory() && file.getName() == "accelleromeer") {
					
					
					
				}
				
			}
			
		}
		
		return result;
		
	}
	
	
}

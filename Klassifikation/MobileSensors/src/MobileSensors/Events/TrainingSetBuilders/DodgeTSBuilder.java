package MobileSensors.Events.TrainingSetBuilders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import MobileSensors.MobSens;
import MobileSensors.Events.Labels.DodgeLabel;
import MobileSensors.Sensors.Accelerometer;
import MobileSensors.Sensors.SensorCollection;
import MobileSensors.Sensors.CSVParsers.AccelerometerCSVParser;

public class DodgeTSBuilder implements TrainingSetBuilder<DodgeLabel> {

	private void addSensorCollection (Map<SensorCollection, DodgeLabel> map, DodgeLabel label) {
		
		File dir = new File(MobSens.INDIR + "./" + label.toString().toLowerCase());
		
		if (dir.exists() && dir.isDirectory()) {
			
			for (File f : dir.listFiles()) {
				
				if (f.isFile() && f.getPath().endsWith(".csv")) {
					
					try {
						
						SensorCollection sc = new SensorCollection();
						sc.setAcceleration((new AccelerometerCSVParser()).parse(new BufferedReader(new FileReader(f))));
					
						map.put(sc, label);
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
										
				}
				
			}
			
		}
		
		
	}
	
	@Override
	public Map<SensorCollection, DodgeLabel> build() {
		
		Map<SensorCollection, DodgeLabel> result = new HashMap<SensorCollection, DodgeLabel>();
	
		this.addSensorCollection(result, DodgeLabel.DODGE);
		this.addSensorCollection(result, DodgeLabel.NODODGE);
		
		return result;
	
	}

}

package MobileSensors.Helpers;

import java.util.ArrayList;

import MobileSensors.Sensors.Sensor;

public class SensorWindowBuilder<S extends Sensor> {

	public ArrayList<ArrayList<S>> buildWindows (ArrayList<S> values, long windowWidth) {
		
		ArrayList<ArrayList<S>> windows = new ArrayList<ArrayList<S>>();
		
		if (values.size() > 0) {
			
			ArrayList<S> window = new ArrayList<S>();
			
			long sTime = values.get(0).getTime();
			
			for (S value : values) {
				
				long eTime = value.getTime();
				
				if (eTime - sTime > windowWidth) {
					
					windows.add(window);
					
					window = new ArrayList<S>();
					
					sTime = eTime;
					
				}
				
				window.add(value);
				
			}
			
			
		}
		
		return windows;
		
	}
	
}

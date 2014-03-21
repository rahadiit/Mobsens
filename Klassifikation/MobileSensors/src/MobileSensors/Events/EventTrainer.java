package MobileSensors.Events;

import java.util.ArrayList;
import java.util.HashMap;

import weka.core.Instances;
import MobileSensors.Events.ARFactories.ARFactory;
import MobileSensors.Events.Labels.EventLabel;
import MobileSensors.Sensors.Accelerometer;
import MobileSensors.Sensors.Location;
import MobileSensors.Sensors.Sensor;
import MobileSensors.Sensors.SensorCollection;

public abstract class EventTrainer<T extends EventLabel> {
	
	private class WindowBuilder<S extends Sensor> {
		
		public ArrayList<ArrayList<S>> buildWindows (
				ArrayList<S> values,
				int windowWidth,
				int windowDelta) {
			
			ArrayList<ArrayList<S>> result = new ArrayList<ArrayList<S>>();
			
			int windowCount = values.size() - windowWidth;
			
			for (int i=0; i < windowCount; i += windowDelta) {
				
				ArrayList<S> window = new ArrayList<S>();
				
				int windowSize = i + windowWidth - windowDelta;
				
				for (int j=i; j < windowSize; j++) {
					
					window.add(values.get(j));
					
				}
				
				result.add(window);
				
			}
			
			return result;
			
		}
		
	}
	
	protected Instances buildWindows (
			HashMap<T, ArrayList<SensorCollection>> scMap,
			ARFactory<T> arFactory,
			int windowWidth,
			int windowDelta) {
				
		for (T label : scMap.keySet()) {
			
			ArrayList<SensorCollection> scs = scMap.get(label);
			
			for (SensorCollection oldSc : scs) {
				
				ArrayList<ArrayList<Accelerometer>> accWindows = (new WindowBuilder<Accelerometer>())
					.buildWindows(oldSc.getAcceleration(), windowWidth, windowDelta);
				ArrayList<ArrayList<Location>> locWindows = (new WindowBuilder<Location>())
					.buildWindows(oldSc.getLocation(), windowWidth, windowDelta);
				
				SensorCollection newSc = new SensorCollection();
				
				for (int i=0; i < Math.max(accWindows.size(), locWindows.size()); i++) {
					
					newSc.setAcceleration(i < accWindows.size() ? accWindows.get(i) : new ArrayList<Accelerometer>());
					newSc.setLocation(i < locWindows.size() ? locWindows.get(i) : new ArrayList<Location>());
					
				}
				
				
				
			}
			
		}
		
		
		return null;
		
	}
	
	
	
}

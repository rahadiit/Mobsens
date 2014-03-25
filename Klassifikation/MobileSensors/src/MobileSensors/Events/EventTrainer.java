package MobileSensors.Events;

import java.util.ArrayList;
import java.util.HashMap;

import weka.core.Instances;
import MobileSensors.Events.ARFactories.ARFactory;
import MobileSensors.Events.Labels.EventLabel;
import MobileSensors.Sensors.Accelerometer;
import MobileSensors.Sensors.Location;
import MobileSensors.Sensors.Sensor;
import MobileSensors.Sensors.SensorRecord;

/**
 * 
 * @author darjeeling
 *
 * @param <T>
 */
public abstract class EventTrainer<T extends EventLabel> {
	
	/**
	 * 
	 * @author darjeeling
	 *
	 * @param <S>
	 */
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
	
	/**
	 * 
	 * @param scMap
	 * @param arFactory
	 * @param windowWidth
	 * @param windowDelta
	 * @return
	 */
	protected Instances buildWindows (
			HashMap<SensorRecord, T> scMap,
			ARFactory<T> arFactory,
			int windowWidth,
			int windowDelta) {
		
		//============================================================================================================
		// Variable Declarations:
		
		WindowBuilder<Accelerometer> accWindowBuilder;		// WindowBuilder for accelerometer sensor data
		WindowBuilder<Location> locWindowBuilder;			// WindowBuilder for location sensor data
		
		ArrayList<ArrayList<Accelerometer>> accWindows;		// List of accelerometer windows
		ArrayList<ArrayList<Location>> locWindows;			// List of location windows
		
		ArrayList<Accelerometer> accWindow;					// Accelerometer window
		ArrayList<Location> locWindow;						// Location window
		
		T label;											
		

		//============================================================================================================
		// Algorithm:
		
		accWindowBuilder = new WindowBuilder<Accelerometer>();
		locWindowBuilder = new WindowBuilder<Location>();
		
		for (SensorRecord oldSc : scMap.keySet()) {
			
			label = scMap.get(oldSc);
			
			accWindows = accWindowBuilder.buildWindows(oldSc.getAcceleration(), windowWidth, windowDelta);
			locWindows = locWindowBuilder.buildWindows(oldSc.getLocation(), windowWidth, windowDelta);
			
			for (int i=0; i < Math.min(accWindows.size(), locWindows.size()); i++) {
				
				accWindow = i < accWindows.size() ? accWindows.get(i) : new ArrayList<Accelerometer>();
				locWindow = i < locWindows.size() ? locWindows.get(i) : new ArrayList<Location>();
				
				SensorRecord newSc = new SensorRecord();
				
				newSc.setAcceleration(accWindow);
				newSc.setLocation(locWindow);
				
			}
			
			
			
		}
		
		
		return null;
		
	}
	
	
	
}

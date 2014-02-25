package MobileSensors.Classifiers;

import java.util.ArrayList;

import MobileSensors.Helper.AccelerationFeatureVector;
import MobileSensors.Storage.Event.Event;
import MobileSensors.Storage.Event.EventType;
import MobileSensors.Storage.Sensors.Accelerometer;

public class Consumer {

	public ArrayList<Event> classify (Window w) throws Exception {
		
		ArrayList<Event> result = new ArrayList<>();
		
		
		if (w.getAcceleration().size() > 0) {
			
			ArrayList<Accelerometer> acc = w.getAcceleration();
			
			AccelerationFeatureVector afv = new AccelerationFeatureVector(acc, "");
			
			DodgeClassifier dc = new DodgeClassifier();
			
			try {
				
				if (dc.classify(afv)) {
					
					result.add(new Event(acc.get(0).getTime(), EventType.DODGE));
					
				}
				
			}
			catch(Exception e) {
				
				
			}
			
			
		}
		
		
		return result;
		
	}
	
	
}

package MobileSensors;

import java.util.ArrayList;

import MobileSensors.Classifiers.DodgeClassifier;
import MobileSensors.Classifiers.EventClassifier;
import MobileSensors.Classifiers.Window;
import MobileSensors.Helper.AccelerationFeatureVector;
import MobileSensors.Storage.Event.Event;
import MobileSensors.Storage.Event.EventType;
import MobileSensors.Storage.Sensors.Accelerometer;

public class MobSens 
	implements EventClassifier {

	public ArrayList<Event> getEvents (Window win) {
		
		ArrayList<Event> result = new ArrayList<Event>();
		
		result.addAll((new DodgeClassifier()).getEvents(win));
		
		return result;
		
	}
	
	
}

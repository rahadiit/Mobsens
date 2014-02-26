package MobileSensors;

import java.util.ArrayList;

import MobileSensors.Classifiers.BrakingClassifier;
import MobileSensors.Classifiers.DodgeClassifier;
import MobileSensors.Classifiers.EventClassifier;
import MobileSensors.Classifiers.Window;
import MobileSensors.Deprecated.Accelerometer;
import MobileSensors.Helper.AccelerationFeatureVector;
import MobileSensors.Storage.Events.Event;
import MobileSensors.Storage.Events.EventType;

/**
 * 
 * MobSens Facade
 * 
 * @author henny, thomas, max
 * 
 */
public class MobSens 
	implements EventClassifier {

	/**
	 * 
	 */
	public ArrayList<Event> getEvents (Window win) {
		
		ArrayList<Event> result = new ArrayList<Event>();
		
		result.addAll((new DodgeClassifier()).getEvents(win));
		result.addAll((new BrakingClassifier()).getEvents(win));
		
		return result;
		
	}
	
	
}

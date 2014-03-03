package MobileSensors;

import java.util.ArrayList;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import MobileSensors.EventClassifiers.BrakingClassifier;
import MobileSensors.EventClassifiers.DodgeClassifier;
import MobileSensors.EventClassifiers.EventClassifier;
import MobileSensors.Events.Event;

/**
 * 
 * MobSens Facade
 * 
 * @author henny, thomas, max
 * 
 */
public class MobSens implements EventClassifier {

	/**
	 * 
	 */
	public ArrayList<Event> getEvents (Window win) {
		
		ArrayList<Event> result = new ArrayList<Event>();
		
		result.addAll((new DodgeClassifier()).getEvents(win));
		result.addAll((new BrakingClassifier()).getEvents(win));
		
		return result;
		
	}
	

	
	public static void main (String[] args) {
		
		
		
	}
	
}

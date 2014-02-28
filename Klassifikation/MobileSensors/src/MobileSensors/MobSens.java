package MobileSensors;

import java.util.ArrayList;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import MobileSensors.Classifiers.BrakingClassifier;
import MobileSensors.Classifiers.DodgeClassifier;
import MobileSensors.Classifiers.EventClassifier;
import MobileSensors.Classifiers.Window;
import MobileSensors.Events.Event;

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
	
	
	public static FastVector createVector () {
		
		FastVector v = new FastVector();
		v.addElement(new Attribute("lala"));
		
		return v;
		
	}
	
	public static void main (String[] args) {
		
		
		
	}
	
}

package MobileSensors.Classifiers;

import java.util.ArrayList;

import MobileSensors.Events.Event;

/**
 * 
 * Event Classifier
 * 
 * @author henny, thomas, max
 * 
 */
public interface EventClassifier {

	public ArrayList<Event> getEvents(Window win);

}
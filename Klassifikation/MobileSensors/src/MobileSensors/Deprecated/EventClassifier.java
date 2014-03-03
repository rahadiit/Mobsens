package MobileSensors.Deprecated;

import java.io.IOException;
import java.util.ArrayList;

import MobileSensors.Events.Event;
import MobileSensors.Sensors.SensorCollection;

/**
 * 
 * Event Classifier
 * 
 * @author henny, thomas, max
 * @deprecated
 * 
 */
public interface EventClassifier {

	public ArrayList<Event> getEvents(SensorCollection win);
		

}
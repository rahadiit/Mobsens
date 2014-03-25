package MobileSensors.Deprecated;

import java.io.IOException;
import java.util.ArrayList;

import MobileSensors.Events.Event;
import MobileSensors.Sensors.SensorRecord;

/**
 * 
 * Event Classifier
 * 
 * @author henny, thomas, max
 * @deprecated
 * 
 */
public interface EventClassifier {

	public ArrayList<Event> getEvents(SensorRecord win);
		

}
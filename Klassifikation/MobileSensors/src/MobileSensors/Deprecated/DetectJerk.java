package MobileSensors.Deprecated;

import java.util.ArrayList;

import MobileSensors.EventClassifiers.EventClassifier;
import MobileSensors.Events.Event;

public class DetectJerk  {

	private ArrayList<Location> locations;
	private ArrayList<Event> events;

	public DetectJerk(ArrayList<Location> locations) {
		this.locations = locations;
		this.events = new ArrayList<Event>();
	}

	
	public ArrayList<Event> getEvents() {

		for (Location location : locations) {
			if (location.getJerk() <=- 0.3) {
				this.events.add(new Event(location.getTime(),
						MobileSensors.Events.EventType.JERK));
			}
		}
		return events;
	}

}

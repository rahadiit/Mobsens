package MobileSensors.Classifiers;

import java.util.ArrayList;

import MobileSensors.Storage.Event.Event;
import MobileSensors.Storage.Sensors.Location;

public class DetectJerk implements EventClassifier {

	private ArrayList<Location> locations;
	private ArrayList<Event> events;

	public DetectJerk(ArrayList<Location> locations) {
		this.locations = locations;
		this.events = new ArrayList<Event>();
	}

	@Override
	public ArrayList<Event> getEvents() {

		for (Location location : locations) {
			if (location.getJerk() <=- 0.3) {
				this.events.add(new Event(location.getTime(),
						MobileSensors.Storage.Event.EventType.JERK));
			}
		}
		return events;
	}

}

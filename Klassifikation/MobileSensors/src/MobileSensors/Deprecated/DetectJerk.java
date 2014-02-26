package MobileSensors.Deprecated;

import java.util.ArrayList;

import MobileSensors.Classifiers.EventClassifier;
import MobileSensors.Storage.Events.Event;

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
						MobileSensors.Storage.Events.EventType.JERK));
			}
		}
		return events;
	}

}

package MobileSensors.Classifiers;

import java.util.ArrayList;
import MobileSensors.Storage.Event.Event;
import MobileSensors.Storage.GPS.Location;

public class DetectStanding implements EventClassifier {

	private ArrayList<Location> locations;
	private ArrayList<Event> events;

	public DetectStanding(ArrayList<Location> locations) {
		this.locations = locations;
		this.events = new ArrayList<Event>();
	}

	@Override
	public ArrayList<Event> getEvents() {
		for (Location location : this.locations) {
			if (location.getSpeed() == 0) {
				this.events.add(new Event(location.getTime(),
						MobileSensors.Storage.Event.EventType.STANDING));
			}
		}
		return this.events;
	}

}

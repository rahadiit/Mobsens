package MobileSensors.Classifiers;

import java.util.ArrayList;

import MobileSensors.Storage.Event.Event;
import MobileSensors.Storage.Sensors.Location;

public class DetectStanding implements EventClassifier {

	private ArrayList<Location> locations;
	private ArrayList<Event> events;

	public DetectStanding(ArrayList<Location> locations) {
		this.locations = locations;
		this.events = new ArrayList<Event>();
	}

	@Override
	public ArrayList<Event> getEvents() {
		
		for(int i=0;i<locations.size();i++){
			Location location=locations.get(i);
			if(location.getSpeed()<=0.2){
				this.events.add(new Event(location.getTime(),
						MobileSensors.Storage.Event.EventType.STANDING));
			}
			//nachfolgende locations mit getSpeed==0 ueberspringen. Nur ein Event fuer eine "Stand-Phase"
			while(locations.get(i++).getSpeed()<=0.2){
				if(i==locations.size())
					break;
			}
		}
		return this.events;
	}
}
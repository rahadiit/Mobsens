package MobileSensors.Classifiers;

import java.util.ArrayList;

import MobileSensors.Storage.Event.Event;
import MobileSensors.Storage.Sensors.Location;

public class DetectBreaking implements EventClassifier {

	private ArrayList<Location> locations;
	private ArrayList<Event> events;

	public DetectBreaking(ArrayList<Location> locations) {
		this.locations = locations;
		this.events = new ArrayList<Event>();
	}

	@Override
	public ArrayList<Event> getEvents() {

		for (int i = 0; i < locations.size()-1; i++) {
			Location location = locations.get(i);
			Location nextlocation = locations.get(i+1);
			
			if(location.getSpeed()-nextlocation.getSpeed() >= 2.5)
			{
				System.out.println("breaking: "+location.getSpeed()+" : "+nextlocation.getSpeed());
				this.events.add(new Event(location.getTime(),
						MobileSensors.Storage.Event.EventType.BRAKING));
				i++;
			}
			
			else if(nextlocation.getSpeed()<=0.3 && location.getSpeed()>1.5){
				System.out.println("breaking: "+location.getSpeed()+" : "+nextlocation.getSpeed());
				this.events.add(new Event(location.getTime(),
						MobileSensors.Storage.Event.EventType.BRAKING));
				i++;
			}
			
			// nachfolgende locations mit getSpeed==0 ueberspringen. Nur ein
			// Event fuer eine "Stand-Phase"
			while (locations.get(i++).getSpeed() <= 0.2) {
				if (i == locations.size()-1)
					break;
			}i--;
		}
		return this.events;
	}
}

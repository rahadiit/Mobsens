package MobileSensors.Deprecated;

import java.util.ArrayList;

import MobileSensors.Events.Event;
import MobileSensors.Sensors.Location;
import MobileSensors.Sensors.SensorCollection;

/**
 * 
 * Breaking Classifier
 * 
 * @author henny, thomas, max
 * @deprecated
 * 
 */
public class BrakingClassifier  {

	/**
	 * 
	 * @param win
	 * @return
	 */
	public ArrayList<Event> getEvents(SensorCollection win) {
		
		ArrayList<Event> events = new ArrayList<Event>();
		ArrayList<Location> locations = win.getLocation();
		
		for (int i = 0; i < locations.size() - 1; i++) {
			Location location = locations.get(i);
			Location nextLocation = locations.get(i + 1);

			boolean breaking = false;
			if (location.getSpeed() / nextLocation.getSpeed() > 1.5) {
				breaking = true;
				i++;
			} else if (i < locations.size() - 2) {
				Location locAfterNextLoc = locations.get(i + 2);

				if (location.getSpeed() / locAfterNextLoc.getSpeed() > 1.75) {
					breaking = true;
					i += 2;
				}
			}

			if (breaking) {
				// System.out.println("breaking: "+location.getSpeed()+" : "+nextlocation.getSpeed());
				events.add(new Event(location.getTime(),
						MobileSensors.Events.EventType.BRAKING));
			}

			// nachfolgende locations mit getSpeed==0 ueberspringen. Nur ein
			// Event fuer eine "Stand-Phase"
			while (i < locations.size() && locations.get(i++).getSpeed() <= 0.2) {
				if (i == locations.size() - 1)
					break;
			}

			i--;
		}
		
		return events;
	}

}

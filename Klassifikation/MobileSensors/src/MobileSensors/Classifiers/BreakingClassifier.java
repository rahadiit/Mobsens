package MobileSensors.Classifiers;

import java.util.ArrayList;

import MobileSensors.Helper.AccelerationFeatureVector;
import MobileSensors.Storage.Event.Event;
import MobileSensors.Storage.Event.EventType;
import MobileSensors.Storage.Sensors.Accelerometer;
import MobileSensors.Storage.Sensors.Location;

public class BreakingClassifier 
	implements EventClassifier{
	
	private boolean classify (ArrayList<Location> locations) {
		
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
//				events.add(new Event(location.getTime(),
//						MobileSensors.Storage.Event.EventType.BRAKING));
				
				return true;
			}

			// nachfolgende locations mit getSpeed==0 ueberspringen. Nur ein
			// Event fuer eine "Stand-Phase"
//			while (i < locations.size() && locations.get(i++).getSpeed() <= 0.2) {
//				if (i == locations.size() - 1)
//					break;
//			}

			i--;
		}
		
		return false;
		
	}
	
	@Override
	public ArrayList<Event> getEvents(Window win) {

		ArrayList<Event> result = new ArrayList<>();
		
		if (win.getLocation().size() > 0) {
			
			try {
				
				ArrayList<Location> locs = win.getLocation();
				
				if (this.classify(locs)) {
					
					result.add(new Event(locs.get(0).getTime(), EventType.BRAKING));
					
				}
				
			}
			catch(Exception e) {
				
				
			}
			
		}
		
		return result;
		
	}

}

package MobileSensors.Deprecated;

import java.util.ArrayList;

public class DetectStanding  {

	private ArrayList<Location> locations;
	private ArrayList<Event> events;

	public DetectStanding(ArrayList<Location> locations) {
		this.locations = locations;
		this.events = new ArrayList<Event>();
	}

	
	public ArrayList<Event> getEvents() {
		
		for(int i=0;i<locations.size()-1;i++){
			Location location=locations.get(i);
			//System.out.println(location.getTime()+" : "+location.getSpeed());
			if(location.getSpeed()<=0.2){
				this.events.add(new Event(location.getTime(),
						MobileSensors.Events.EventType.STANDING));
			}
			//nachfolgende locations mit getSpeed==0 ueberspringen. Nur ein Event fuer eine "Stand-Phase"
			while(locations.get(i++).getSpeed()<=0.25){
				if(i==locations.size())
					break;
			}
			i--;
			
		}
		return this.events;
	}
}
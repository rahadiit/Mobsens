package MobileSensors.Events.Classifiers;

import java.io.File;
import java.util.ArrayList;

import MobileSensors.Events.Event;
import MobileSensors.Events.EventType;
import MobileSensors.Events.ARFactories.KerbstoneARFactory;
import MobileSensors.Events.Labels.KerbstoneLabel;
import MobileSensors.Sensors.SensorRecord;

public class KerbstoneClassifier extends EventClassifier<KerbstoneLabel> {



	public KerbstoneClassifier(File modelFile) throws Exception {
		
		super(new KerbstoneARFactory(), modelFile);
	}

	@Override
	public ArrayList<Event> classifyEvents (SensorRecord sr) {
		
		ArrayList<Event> events = new ArrayList<Event>();
		
		try {
			
			if (this.classify(sr) > 0.5) {
				
				events.add(new Event(sr.getAcceleration().get(0).getTime(),sr.getAcceleration().get(sr.getAcceleration().size()-1).getTime(),EventType.KERBSTONE));
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return events;
	}
	
	
}

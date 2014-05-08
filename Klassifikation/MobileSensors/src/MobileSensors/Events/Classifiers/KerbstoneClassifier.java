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
			
			if (KerbstoneLabel.KERBSTONE.toString().equals(this.classify(sr))) {
				
				long startTime = sr.getAcceleration().get(0).getTime();
				long endTime = sr.getAcceleration().get(sr.getAcceleration().size()-1).getTime();
				
				events.add(new Event(startTime,endTime,EventType.KERBSTONE));
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return events;
	}
	
	
}

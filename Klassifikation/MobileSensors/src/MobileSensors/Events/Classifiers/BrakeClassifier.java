package MobileSensors.Events.Classifiers;

import java.io.File;
import java.util.ArrayList;

import MobileSensors.Events.Event;
import MobileSensors.Events.EventType;
import MobileSensors.Events.ARFactories.BrakeARFactory;
import MobileSensors.Events.Labels.BrakeLabel;
import MobileSensors.Sensors.SensorRecord;

public class BrakeClassifier extends EventClassifier<BrakeLabel> {

	public BrakeClassifier(File modelFile) throws Exception {
		
		super(new BrakeARFactory(), modelFile);

	}

	@Override
	public ArrayList<Event> classifyEvents (SensorRecord sr) {
		
		ArrayList<Event> events = new ArrayList<Event>();
		
		try {
			
			if (BrakeLabel.BRAKE.toString().equals(this.classify(sr))) {
				
				long startTime = sr.getAcceleration().get(0).getTime();
				long endTime = sr.getAcceleration().get(sr.getAcceleration().size()-1).getTime();
				
				events.add(new Event(startTime,endTime,EventType.BRAKING));
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return events;
	}
	
	
}

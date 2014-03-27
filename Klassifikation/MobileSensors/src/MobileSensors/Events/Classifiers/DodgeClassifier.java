package MobileSensors.Events.Classifiers;

import java.util.ArrayList;

import MobileSensors.MobSens;
import MobileSensors.Events.Event;
import MobileSensors.Events.EventType;
import MobileSensors.Events.ARFactories.DodgeARFactory;
import MobileSensors.Sensors.SensorRecord;

public class DodgeClassifier extends EventClassifier {

	private DodgeARFactory dodgeFactory;
	
	public DodgeClassifier() throws Exception {
		
		super(MobSens.MODELFILE_DODGE);
		
		this.dodgeFactory = new DodgeARFactory();
		
	}
	
	@Override
	public ArrayList<Event> classify (SensorRecord scWindow) {
		
		ArrayList<Event> events = new ArrayList<Event>();
		
		try {
			
			if (this.classifier.classifyInstance(
					this.dodgeFactory.createFeatureVector(scWindow)) < 0.5) {
				
				events.add(new Event(0,0,EventType.DODGE));
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return events;
	}
	
	
}

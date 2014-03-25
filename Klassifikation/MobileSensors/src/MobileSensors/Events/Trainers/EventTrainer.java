package MobileSensors.Events.Trainers;

import java.util.Map;
import java.util.HashMap;

import weka.classifiers.Classifier;
import MobileSensors.Events.Labels.EventLabel;
import MobileSensors.Sensors.SensorRecord;

public abstract class EventTrainer<L extends EventLabel> {

	abstract public Classifier train () throws Exception;
	
	protected Map<SensorRecord, L> sensorCollections;
		
	public EventTrainer () {
		
		this.sensorCollections = new HashMap<SensorRecord, L>();
		
	}
	
	public EventTrainer (Map<SensorRecord, L> sensorCollections) {
		
		this.sensorCollections = sensorCollections;
		
	}
	
	public void addSenorCollection (SensorRecord sc, L label) {
		
		this.sensorCollections.put(sc, label);
		
	}
	
	public void removeSensorCollection (SensorRecord sc) {
		
		this.sensorCollections.remove(sc);
		
	}
	
	public void empty () {
		
		this.sensorCollections.clear();
		
	}
	
	
}

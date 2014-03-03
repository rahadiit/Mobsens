package MobileSensors.Events.Trainers;

import java.util.Map;
import java.util.HashMap;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import MobileSensors.Events.Labels.EventLabel;
import MobileSensors.Sensors.SensorCollection;

public abstract class EventTrainer<L extends EventLabel> {

	abstract public Classifier train () throws Exception;
	
	protected Map<SensorCollection, L> sensorCollections;
		
	public EventTrainer () {
		
		this.sensorCollections = new HashMap<SensorCollection, L>();
		
	}
	
	public void addSenorCollection (SensorCollection sc, L label) {
		
		this.sensorCollections.put(sc, label);
		
	}
	
	public void removeSensorCollection (SensorCollection sc) {
		
		this.sensorCollections.remove(sc);
		
	}
	
	public void empty () {
		
		this.sensorCollections.clear();
		
	}
	
	
}

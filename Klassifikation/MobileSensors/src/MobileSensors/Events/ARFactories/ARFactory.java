package MobileSensors.Events.ARFactories;

import MobileSensors.Events.Labels.*;
import MobileSensors.Sensors.SensorCollection;
import weka.core.Instance;
import weka.core.Instances;

public interface ARFactory<T extends EventLabel> {

	public Instances createTrainingSet(int size);
	
	public Instance createFeatureVector(SensorCollection win);
	
	public Instance createFeatureVector(SensorCollection win, T label);
	
}

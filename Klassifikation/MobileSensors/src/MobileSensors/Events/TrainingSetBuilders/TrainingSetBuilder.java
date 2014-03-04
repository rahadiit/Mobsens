package MobileSensors.Events.TrainingSetBuilders;

import java.util.Map;

import MobileSensors.Events.Labels.EventLabel;
import MobileSensors.Sensors.SensorCollection;

public interface TrainingSetBuilder<T extends EventLabel> {

	public Map<SensorCollection, T> build ();
	
}

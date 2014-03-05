package MobileSensors.Events.CSVDirParsers;

import java.util.Map;

import MobileSensors.Events.Labels.EventLabel;
import MobileSensors.Sensors.SensorCollection;

public interface TSFactory<T extends EventLabel> {

	public Map<SensorCollection, T> buildTrainingSet ();
	
}

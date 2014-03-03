package MobileSensors.ARFactories;

import MobileSensors.Window;
import weka.core.Instance;
import weka.core.Instances;

public interface ARFactory {

	
	public Instances createTrainingSet(int size);
	public Instance createFeatureVector(Window win, String label);
	
	
}

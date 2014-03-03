package MobileSensors.EventTrainers;

import MobileSensors.Window;
import weka.classifiers.Classifier;

public interface EventTrainer {

	public Classifier train (Window win);
	
}

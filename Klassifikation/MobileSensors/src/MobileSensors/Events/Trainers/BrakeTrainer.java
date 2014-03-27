package MobileSensors.Events.Trainers;

import java.io.File;
import weka.classifiers.trees.J48;
import MobileSensors.Events.ARFactories.BrakeARFactory;
import MobileSensors.Events.Labels.BrakeLabel;

public class BrakeTrainer extends EventTrainer<BrakeLabel> {


	/**
	 * Number of items in a window used to create a feature vector
	 */
	private final static int WINDOW_WIDTH = 100;
	
	/**
	 * 
	 */
	private final static int WINDOW_DELTA = 1;
	
	
	/**
	 * Number folds used for cross validation
	 */
	private final static int VALIDATION_FOLDS = 10; 
	
	
	public BrakeTrainer(File modelFile) {
		
		super(new BrakeARFactory(),
				new J48(), 
				modelFile, 
				BrakeTrainer.WINDOW_WIDTH, 
				BrakeTrainer.WINDOW_DELTA,
				BrakeTrainer.VALIDATION_FOLDS);

	}
	
	
}

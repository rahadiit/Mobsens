package MobileSensors.Events.Trainers;

import java.io.File;
import weka.classifiers.trees.J48;
import MobileSensors.Events.ARFactories.DodgeARFactory;
import MobileSensors.Events.Labels.DodgeLabel;

/**
 * 
 * @author henny, thomas, max
 *
 */
public class DodgeTrainer extends EventTrainer<DodgeLabel> {

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
	
	
	public DodgeTrainer(File modelFile) {
		
		super(new DodgeARFactory(),
				new J48(), 
				modelFile, 
				DodgeTrainer.WINDOW_WIDTH, 
				DodgeTrainer.WINDOW_DELTA,
				DodgeTrainer.VALIDATION_FOLDS);

	}
	
}

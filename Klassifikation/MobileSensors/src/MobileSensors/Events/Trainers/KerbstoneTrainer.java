package MobileSensors.Events.Trainers;

import java.io.File;
import weka.classifiers.trees.J48;
import MobileSensors.Events.ARFactories.KerbstoneARFactory;
import MobileSensors.Events.Labels.KerbstoneLabel;

/**
 * 
 * @author henny, thomas, max
 *
 */
public class KerbstoneTrainer extends EventTrainer<KerbstoneLabel> {

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
	
	
	public KerbstoneTrainer(File modelFile) {
		
		super(new KerbstoneARFactory(),
				new J48(), 
				modelFile, 
				KerbstoneTrainer.WINDOW_WIDTH, 
				KerbstoneTrainer.WINDOW_DELTA,
				KerbstoneTrainer.VALIDATION_FOLDS);

	}
	
}

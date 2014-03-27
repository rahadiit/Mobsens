package MobileSensors.Events.Trainers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.SerializationHelper;
import MobileSensors.MobSens;
import MobileSensors.Events.ARFactories.ARFactory;
import MobileSensors.Events.ARFactories.BrakeARFactory;
import MobileSensors.Events.ARFactories.DodgeARFactory;
import MobileSensors.Events.Labels.DodgeLabel;
import MobileSensors.Sensors.Accelerometer;
import MobileSensors.Sensors.SensorRecord;

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

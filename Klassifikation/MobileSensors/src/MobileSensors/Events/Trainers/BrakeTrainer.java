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
import MobileSensors.Events.ARFactories.BrakeARFactory;
import MobileSensors.Events.ARFactories.DodgeARFactory;
import MobileSensors.Events.Labels.*;
import MobileSensors.Sensors.Accelerometer;
import MobileSensors.Sensors.SensorRecord;

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

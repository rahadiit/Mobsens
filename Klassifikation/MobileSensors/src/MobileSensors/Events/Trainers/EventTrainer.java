package MobileSensors.Events.Trainers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

import org.apache.commons.io.FilenameUtils;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.SerializationHelper;
import MobileSensors.Events.ARFactories.ARFactory;
import MobileSensors.Events.Labels.EventLabel;
import MobileSensors.Sensors.Accelerometer;
import MobileSensors.Sensors.Location;
import MobileSensors.Sensors.Sensor;
import MobileSensors.Sensors.SensorRecord;

public abstract class EventTrainer<L extends EventLabel> {
	
	private class WindowBuilder<S extends Sensor> {
		
		public ArrayList<ArrayList<S>> buildWindows (
				ArrayList<S> values,
				int windowWidth,
				int windowDelta) {
			
			ArrayList<ArrayList<S>> result = new ArrayList<ArrayList<S>>();
			//+------+
			// +------+
			int windowCount = values.size() - windowWidth;
			
			for (int i=0; i < windowCount; i += windowDelta) {
				
				ArrayList<S> window = new ArrayList<S>();
				
				int windowSize = i + windowWidth - windowDelta;
				
				for (int j=i; j < windowSize; j++) {
					
					window.add(values.get(j));
					
				}
				
				result.add(window);
				
			}
			
			return result;
			
		}
		
	}
	

	private ARFactory<L> arFactory;
	private Classifier eventClassifier;
	private Evaluation eventClassifierEvaluation;
	
	private int windowWidth;
	private int windowDelta;
	private int validationFolds;
	
	private File modelFile;
	private File arffFile;
	private File evalFile;
	
	protected Map<SensorRecord, L> sensorCollections;
	
	public EventTrainer (ARFactory<L> arFactory, Classifier eventClassifier, File modelFile,
			int windowWidth, int windowDelta, int validationFolds) {
		
		this.arFactory = arFactory;
		this.eventClassifier = eventClassifier;
		
		this.windowWidth = windowWidth;
		this.windowDelta = windowDelta;
		this.validationFolds = validationFolds;
		
		Path modelDir = Paths.get(modelFile.getParent());
		
		this.modelFile = modelFile;
		this.arffFile = modelDir.resolve(FilenameUtils.getBaseName(modelFile.getName()) + ".arff").toFile();
		this.evalFile = modelDir.resolve(FilenameUtils.getBaseName(modelFile.getName()) + ".eval").toFile();
		
		
	}
	
	private void log (String msg) {
		
		System.out.println(this.getClass().getSimpleName() + " :: " + msg);
		
	}
	
	protected Instances buildTrainingSet (Map<SensorRecord, L> sensorCollections, int windowWidth, int windowDelta) throws IOException {
		
		Date start;
		Date stop;
		
		start = new Date();
		
		//============================================================================================================
		// Variable Declarations:
		
		WindowBuilder<Accelerometer> accWindowBuilder;		// WindowBuilder for accelerometer sensor data
		WindowBuilder<Location> locWindowBuilder;			// WindowBuilder for location sensor data
		
		ArrayList<ArrayList<Accelerometer>> accWindows;		// List of accelerometer windows
		ArrayList<ArrayList<Location>> locWindows;			// List of location windows
		
		ArrayList<Accelerometer> accWindow;					// Accelerometer window
		ArrayList<Location> locWindow;						// Location window
		
		L label;											
		
		Map<SensorRecord, L> newSensorCollections;

		//============================================================================================================
		// Algorithm:
		
		accWindowBuilder = new WindowBuilder<Accelerometer>();
		locWindowBuilder = new WindowBuilder<Location>();
		
		newSensorCollections = new HashMap<SensorRecord, L>();
		
		this.log("Building windows");
		
		for (SensorRecord oldSc : sensorCollections.keySet()) {
			
			label = sensorCollections.get(oldSc);
			
			accWindows = accWindowBuilder.buildWindows(oldSc.getAcceleration(), windowWidth, windowDelta);
//			locWindows = locWindowBuilder.buildWindows(oldSc.getLocation(), windowWidth, windowDelta);
			
			for (int i=0; i < accWindows.size(); i++) {
				
				accWindow = i < accWindows.size() ? accWindows.get(i) : new ArrayList<Accelerometer>();
//				locWindow = i < locWindows.size() ? locWindows.get(i) : new ArrayList<Location>();
				
				SensorRecord newSc = new SensorRecord();
				
				newSc.setAcceleration(accWindow);
//				newSc.setLocation(locWindow);
				
				newSensorCollections.put(newSc, label);
				
			}
			
			
			
		}
		
		//============================================================================================================
		
		Instances trainingSet = this.arFactory.createTrainingSet(newSensorCollections);
		
		this.log("Writing training set to " + this.arffFile.getCanonicalPath());
		
		BufferedWriter arffWriter = new BufferedWriter(new FileWriter(this.arffFile));
		arffWriter.write(trainingSet.toString());
		arffWriter.flush();
		arffWriter.close();
		
		stop = new Date();
		
		this.log("Done after " + (stop.getTime() - start.getTime()) + "ms.");
		
		return trainingSet;
		
	}
	
	protected void buildClassifier (Instances trainingSet) throws Exception {
		
		Date start;
		Date stop;
		
		start = new Date();
		
		this.log("Building new " + eventClassifier.getClass().getName() + " classifier.");
		
		eventClassifier.buildClassifier(trainingSet);
		
		this.log("Writing classifier to " + this.modelFile.getCanonicalPath());
		
		SerializationHelper.write(this.modelFile.getCanonicalPath(), eventClassifier);
		
		stop = new Date();
		
		this.log("Done after " + (stop.getTime() - start.getTime()) + "ms.");
		
		
	}
	
	protected void crossValidateModel (Instances trainingSet, int folds) throws Exception {
		
		Date start;
		Date stop;
		
		start = new Date();
		
		this.log("Starting cross validation with " + folds + " folds.");
		
		Evaluation eval = new Evaluation(trainingSet);
		
		eval.crossValidateModel(this.eventClassifier, trainingSet, folds, new Random(1));
		
		this.log("Finished cross validation.");
		
		this.log("Writing evaluation to " + this.evalFile.getCanonicalPath());
		
		BufferedWriter evalWriter = new BufferedWriter(new FileWriter(this.evalFile));
		evalWriter.write("Summary:");
		evalWriter.newLine();
		evalWriter.write("========");
		evalWriter.newLine();
		evalWriter.write(eval.toSummaryString());
		evalWriter.newLine();
		evalWriter.write("Detailed Statistics:");
		evalWriter.newLine();
		evalWriter.write("====================");
		evalWriter.newLine();
		evalWriter.write(eval.toClassDetailsString());
		evalWriter.newLine();
		evalWriter.write("Confusion matrix :");
		evalWriter.newLine();
		evalWriter.write("==================");
		evalWriter.newLine();
		evalWriter.write(eval.toMatrixString());
		evalWriter.newLine();
		evalWriter.newLine();
		evalWriter.write(this.eventClassifier.toString());
		evalWriter.flush();
		evalWriter.close();
		
		stop = new Date();
		
		this.log("Done after " + (stop.getTime() - start.getTime()) + "ms.");
		
		
	}
	
	public void train (Map<SensorRecord, L> sensorCollections) throws Exception {
		
		System.out.println();
		
		this.log("Started training!");
		
		Instances trainingSet = this.buildTrainingSet(sensorCollections, this.windowWidth, this.windowDelta);
		
		this.buildClassifier(trainingSet);
		this.crossValidateModel(trainingSet, this.validationFolds);
		
		this.log("Finished training!");
		
		System.out.println();
		
	}
	
}

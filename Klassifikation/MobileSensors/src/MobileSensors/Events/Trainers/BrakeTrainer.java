package MobileSensors.Events.Trainers;

import java.io.BufferedWriter;
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
	
	public BrakeTrainer (Map<SensorRecord, BrakeLabel> sensorCollections) {
		
		super(sensorCollections);
		
	}
	
	private void addSensorCollectionToTrainingSet(SensorRecord sc, BrakeLabel label, Instances trainingSet) {
		
		ArrayList<Accelerometer> accs = sc.getAcceleration();
		
		for (int i=0; i <= accs.size()-BrakeTrainer.WINDOW_WIDTH; i+=BrakeTrainer.WINDOW_DELTA) {
			
			ArrayList<Accelerometer> BrakeAccs = new ArrayList<Accelerometer>();
			
			for (int j=i; j <= i+BrakeTrainer.WINDOW_WIDTH-BrakeTrainer.WINDOW_DELTA; j++) {
				
				BrakeAccs.add(accs.get(j));
				
			}
			
			
			SensorRecord BrakeSC = new SensorRecord();
			BrakeSC.setAcceleration(BrakeAccs);
			
			trainingSet.add((new BrakeARFactory()).createFeatureVector(BrakeSC, label));
			
		}
		
	}
	
	/**
	 * 
	 */
	@Override
	public Classifier train() throws Exception {
		
		
		Instances trainingSet = (new BrakeARFactory()).createTrainingSet(); 
		
		for (SensorRecord sc : this.sensorCollections.keySet()) {
			
			BrakeLabel label = this.sensorCollections.get(sc);
			
			this.addSensorCollectionToTrainingSet(sc, label, trainingSet);
			
		}
			
		BufferedWriter arffWriter = new BufferedWriter(new FileWriter(MobSens.ARFFFILE_Brake));
		arffWriter.write(trainingSet.toString());
		arffWriter.flush();
		arffWriter.close();
		
		Classifier BrakeJ48 = new J48();
		
		BrakeJ48.buildClassifier(trainingSet);
		
		SerializationHelper.write(MobSens.MODELFILE_Brake, BrakeJ48);
		
		Evaluation eval = new Evaluation(trainingSet);	
		eval.crossValidateModel(BrakeJ48, trainingSet, BrakeTrainer.VALIDATION_FOLDS, new Random(1));
		
		
		BufferedWriter evalWriter = new BufferedWriter(new FileWriter(MobSens.EVALFILE_Brake));
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
		evalWriter.write(BrakeJ48.toString());
		evalWriter.flush();
		evalWriter.close();
		
		System.out.println("finished braking, bitches");
		
		
		return BrakeJ48;
		
		
	}

	
	
}

package MobileSensors.Events.Trainers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.SerializationHelper;
import MobileSensors.MobSens;
import MobileSensors.Events.ARFactories.DodgeARFactory;
import MobileSensors.Events.Labels.DodgeLabel;
import MobileSensors.Sensors.Accelerometer;
import MobileSensors.Sensors.SensorCollection;

public class DodgeTrainer extends EventTrainer<DodgeLabel> {

	private final static int WINDOW_WIDTH = 100;
	private final static int WINDOW_DELTA = 1;
	private final static int VALIDATION_FOLDS = 10; 
	
		
	private void addSensorCollectionToTrainingSet(SensorCollection sc, DodgeLabel label, Instances trainingSet) {
		
		ArrayList<Accelerometer> accs = sc.getAcceleration();
		
		for (int i=0; i <= accs.size()-DodgeTrainer.WINDOW_WIDTH; i+=DodgeTrainer.WINDOW_DELTA) {
			
			ArrayList<Accelerometer> dodgeAccs = new ArrayList<Accelerometer>();
			
			for (int j=i; j <= i+DodgeTrainer.WINDOW_WIDTH-DodgeTrainer.WINDOW_DELTA; j++) {
				
				dodgeAccs.add(accs.get(j));
				
			}
			
			
			SensorCollection dodgeSC = new SensorCollection();
			dodgeSC.setAcceleration(dodgeAccs);
			
			
			
			
			trainingSet.add((new DodgeARFactory()).createFeatureVector(dodgeSC, label));
			
		}
		
	}
	
	@Override
	public Classifier train() throws Exception {
		
		
		Instances trainingSet = (new DodgeARFactory()).createTrainingSet(0); 
		
		for (SensorCollection sc : this.sensorCollections.keySet()) {
			
			DodgeLabel label = this.sensorCollections.get(sc);
			
			this.addSensorCollectionToTrainingSet(sc, label, trainingSet);
			
		}
			
		BufferedWriter arffWriter = new BufferedWriter(new FileWriter(MobSens.ARFFFILE_DODGE));
		arffWriter.write(trainingSet.toString());
		arffWriter.flush();
		arffWriter.close();
		
		Classifier dodgeJ48 = new J48();
		
		dodgeJ48.buildClassifier(trainingSet);
		
		SerializationHelper.write(MobSens.MODELFILE_DODGE, dodgeJ48);
		
		Evaluation eval = new Evaluation(trainingSet);	
		eval.crossValidateModel(dodgeJ48, trainingSet, DodgeTrainer.VALIDATION_FOLDS, new Random(1));
		
		
		BufferedWriter evalWriter = new BufferedWriter(new FileWriter(MobSens.EVALFILE_DODGE));
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
		evalWriter.write(dodgeJ48.toString());
		evalWriter.flush();
		evalWriter.close();
		
		
		
		return dodgeJ48;
		
		
	}

	

}

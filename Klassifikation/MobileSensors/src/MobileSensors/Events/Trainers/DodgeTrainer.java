package MobileSensors.Events.Trainers;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
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
	
	private DodgeARFactory dodgeFactory;
	
	public DodgeTrainer () {
		
		super();
		
		this.dodgeFactory = new DodgeARFactory();
		
		
		
	}
	
	private void addSensorCollectionToTrainingSet(SensorCollection sc, DodgeLabel label, Instances trainingSet) {
		
		ArrayList<Accelerometer> accs = sc.getAcceleration();
		
		for (int i=0; i <= accs.size()-DodgeTrainer.WINDOW_WIDTH; i+=DodgeTrainer.WINDOW_DELTA) {
			
			ArrayList<Accelerometer> dodgeAccs = new ArrayList<Accelerometer>();
			
			for (int j=i; j <= i+DodgeTrainer.WINDOW_WIDTH-DodgeTrainer.WINDOW_DELTA; j++) {
				
				dodgeAccs.add(accs.get(j));
				
			}
			
			
			SensorCollection dodgeSC = new SensorCollection();
			dodgeSC.setAcceleration(dodgeAccs);
			
			
			
			
			trainingSet.add(dodgeFactory.createFeatureVector(dodgeSC, label));
			
		}
		
	}
	
	@Override
	public Classifier train() throws Exception {
		
		
		Instances trainingSet = dodgeFactory.createTrainingSet(0); 
		
		for (SensorCollection sc : this.sensorCollections.keySet()) {
			
			DodgeLabel label = this.sensorCollections.get(sc);
			
			this.addSensorCollectionToTrainingSet(sc, label, trainingSet);
			
		}
			
		BufferedWriter w = new BufferedWriter(new FileWriter(MobSens.ARFFFILE_DODGE));
		w.write(trainingSet.toString());
		w.flush();
		w.close();
		
		System.out.println(trainingSet.numInstances());
		
		Classifier dodgeJ48 = new J48();
		
		dodgeJ48.buildClassifier(trainingSet);
		
		SerializationHelper.write(MobSens.MODELFILE_DODGE, dodgeJ48);
		
		Evaluation eval = new Evaluation(trainingSet);
		
		System.out.println(trainingSet.numInstances());
		
		eval.crossValidateModel(dodgeJ48, trainingSet, DodgeTrainer.VALIDATION_FOLDS, new Random(1));
		BufferedWriter b = new BufferedWriter(new FileWriter(MobSens.EVALFILE_DODGE));
		b.write("Summary:");
		b.newLine();
		b.write("========");
		b.newLine();
		b.write(eval.toSummaryString());
		b.newLine();
		b.write("Detailed Statistics:");
		b.newLine();
		b.write("====================");
		b.newLine();
		b.write(eval.toClassDetailsString());
		b.newLine();
		b.write("Confusion matrix :");
		b.newLine();
		b.write("==================");
		b.newLine();
		b.write(eval.toMatrixString());
		b.flush();
		b.close();
		
		
		
		return dodgeJ48;
		
		
	}

	

}

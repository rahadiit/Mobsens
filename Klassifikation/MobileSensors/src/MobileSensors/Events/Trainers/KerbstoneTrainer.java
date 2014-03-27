package MobileSensors.Events.Trainers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.SerializationHelper;
import MobileSensors.MobSens;
import MobileSensors.Events.ARFactories.KerbstoneARFactory;
import MobileSensors.Events.Labels.KerbstoneLabel;
import MobileSensors.Sensors.Accelerometer;
import MobileSensors.Sensors.SensorRecord;

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
	
	public KerbstoneTrainer (Map<SensorRecord, KerbstoneLabel> sensorCollections) {
		
		super(sensorCollections);
		
	}
	
	private void addSensorCollectionToTrainingSet(SensorRecord sc, KerbstoneLabel label, Instances trainingSet) {
		
		ArrayList<Accelerometer> accs = sc.getAcceleration();
		
		for (int i=0; i <= accs.size()-KerbstoneTrainer.WINDOW_WIDTH; i+=KerbstoneTrainer.WINDOW_DELTA) {
			
			ArrayList<Accelerometer> KerbstoneAccs = new ArrayList<Accelerometer>();
			
			for (int j=i; j <= i+KerbstoneTrainer.WINDOW_WIDTH-KerbstoneTrainer.WINDOW_DELTA; j++) {
				
				KerbstoneAccs.add(accs.get(j));
				
			}
			
			
			SensorRecord KerbstoneSC = new SensorRecord();
			KerbstoneSC.setAcceleration(KerbstoneAccs);
			
			trainingSet.add((new KerbstoneARFactory()).createFeatureVector(KerbstoneSC, label));
			
		}
		
	}
	
	/**
	 * 
	 */
	@Override
	public Classifier train() throws Exception {
		
		Date start = new Date();
		Date stop;
		
		Instances trainingSet = (new KerbstoneARFactory()).createTrainingSet(); 
		
		for (SensorRecord sc : this.sensorCollections.keySet()) {
			
			KerbstoneLabel label = this.sensorCollections.get(sc);
			
			this.addSensorCollectionToTrainingSet(sc, label, trainingSet);
			
		}
			
		BufferedWriter arffWriter = new BufferedWriter(new FileWriter(MobSens.ARFFFILE_Kerbstone));
		arffWriter.write(trainingSet.toString());
		arffWriter.flush();
		arffWriter.close();
		
		stop = new Date();
		
		System.out.println("Generated arff: " + (stop.getTime() - start.getTime()) +  " ms");
		
		start = new Date();
		
		Classifier KerbstoneJ48 = new J48();
		
		KerbstoneJ48.buildClassifier(trainingSet);
		
		SerializationHelper.write(MobSens.MODELFILE_Kerbstone, KerbstoneJ48);
		
		
		stop = new Date();
		
		System.out.println("Generated model: " + (stop.getTime() - start.getTime()) +  " ms");
		
		start = new Date();
		
		
		
		Evaluation eval = new Evaluation(trainingSet);	
		eval.crossValidateModel(KerbstoneJ48, trainingSet, KerbstoneTrainer.VALIDATION_FOLDS, new Random(1));
		
		
		BufferedWriter evalWriter = new BufferedWriter(new FileWriter(MobSens.EVALFILE_Kerbstone));
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
		evalWriter.write(KerbstoneJ48.toString());
		evalWriter.flush();
		evalWriter.close();
		

		stop = new Date();
		
		System.out.println("Generated eval: " + (stop.getTime() - start.getTime()) +  " ms");
		
		return KerbstoneJ48;
		
		
	}

	

}

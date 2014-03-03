package MobileSensors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import MobileSensors.Events.Classifiers.DodgeClassifier;
import MobileSensors.Events.Event;
import MobileSensors.Events.Labels.DodgeLabel;
import MobileSensors.Events.Trainers.DodgeTrainer;
import MobileSensors.Sensors.SensorCollection;
import MobileSensors.Sensors.CSVParsers.AccelerometerCSVParser;

/**
 * 
 * MobSens Facade
 * 
 * @author henny, thomas, max
 * 
 */
public class MobSens {

	private final static String INDIR = "./csv";
	
	private final static String OUTDIR_ARFF = "./arff";
	private final static String OUTDIR_MODEL = "./model";
	
	private final static String EXTENSION_MODEL = ".model";
	private final static String EXTENSION_ARFF  = ".arff";
	private final static String EXTENSION_EVAL  = ".eval";
	
	private final static String FILENAME_DODGE   = "./Dodge";
	private final static String FILENAME_BRAKING = "./Braking";
	
	public final static String MODELFILE_DODGE = MobSens.OUTDIR_MODEL + MobSens.FILENAME_DODGE + MobSens.EXTENSION_MODEL;
	public final static String ARFFFILE_DODGE  = MobSens.OUTDIR_MODEL + MobSens.FILENAME_DODGE + MobSens.EXTENSION_ARFF;
	public final static String EVALFILE_DODGE  = MobSens.OUTDIR_MODEL + MobSens.FILENAME_DODGE + MobSens.EXTENSION_EVAL;
	
	/**
	 * 
	 */
	public ArrayList<Event> getEvents (SensorCollection win) {
		
		ArrayList<Event> result = new ArrayList<Event>();
		
		try {
				
			result.addAll((new DodgeClassifier()).classify(win));
		
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		return result;
		
	}
	
	private static void trainDodges (DodgeTrainer trainer, DodgeLabel label) throws FileNotFoundException, IOException {
		
		AccelerometerCSVParser accParser = new AccelerometerCSVParser();
		File dir = new File(MobSens.INDIR + "./" + label.toString().toLowerCase());
		
		if (dir.exists() && dir.isDirectory()) {
			
			for (File f : dir.listFiles()) {
				
				if (f.isFile() && f.getPath().endsWith(".csv")) {
					
					SensorCollection sc = new SensorCollection();
					sc.setAcceleration(accParser.parse(new BufferedReader(new FileReader(f))));
					
					trainer.addSenorCollection(sc, label);
					
				}
				
			}
			
		}
		
		
	}

	
	public static void main (String[] args) throws Exception {
		
		DodgeTrainer dt = new DodgeTrainer();
		
		trainDodges(dt, DodgeLabel.DODGE);
		trainDodges(dt, DodgeLabel.NODODGE);
		
		dt.train();
		
		
	}
	
}

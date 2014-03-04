package MobileSensors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import MobileSensors.Events.Classifiers.DodgeClassifier;
import MobileSensors.Events.Event;
import MobileSensors.Events.Labels.DodgeLabel;
import MobileSensors.Events.Trainers.DodgeTrainer;
import MobileSensors.Events.TrainingSetBuilders.DodgeTSBuilder;
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

	public final static String INDIR = "./input";
	
	public final static String OUTDIR = "./model";
	
	public final static String EXTENSION_MODEL = ".model";
	public final static String EXTENSION_ARFF  = ".arff";
	public final static String EXTENSION_EVAL  = ".eval";
	
	public final static String FILENAME_DODGE   = "./Dodge";
	public final static String MODELFILE_DODGE = MobSens.OUTDIR + MobSens.FILENAME_DODGE + MobSens.EXTENSION_MODEL;
	public final static String ARFFFILE_DODGE  = MobSens.OUTDIR + MobSens.FILENAME_DODGE + MobSens.EXTENSION_ARFF;
	public final static String EVALFILE_DODGE  = MobSens.OUTDIR + MobSens.FILENAME_DODGE + MobSens.EXTENSION_EVAL;
	
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
		DodgeTSBuilder db = new DodgeTSBuilder();
		
//		trainDodges(dt, DodgeLabel.DODGE);
//		trainDodges(dt, DodgeLabel.NODODGE);
		
		Map<SensorCollection, DodgeLabel> ts = db.build();
		
		for (SensorCollection sc : ts.keySet()) {
			
			dt.addSenorCollection(sc, ts.get(sc));
			
		}
		
		dt.train();
		
		System.out.println("Finished Training. Ready to Rock!");
		
	}
	
}

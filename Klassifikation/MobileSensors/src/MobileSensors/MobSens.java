package MobileSensors;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;

import MobileSensors.Events.CSVDirParsers.DodgeTSFactory;
import MobileSensors.Events.Classifiers.DodgeClassifier;
import MobileSensors.Events.Event;
import MobileSensors.Events.Labels.BrakeLabel;
import MobileSensors.Events.Labels.DodgeLabel;
import MobileSensors.Events.Labels.EventLabel;
import MobileSensors.Events.Trainers.DodgeTrainer;
import MobileSensors.Helpers.EventTraingDataParser;
import MobileSensors.Sensors.SensorCollection;

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
	 * @param args
	 * @throws Exception
	 */
	public static void main (String[] args) {
		
		EventLabel[] labels = {
				DodgeLabel.DODGE,
				DodgeLabel.NODODGE,
				BrakeLabel.BRAKE,
				BrakeLabel.NOBRAKE
				};
		
		EventTraingDataParser etdp = new EventTraingDataParser(new File("./input"), labels);
		
		HashMap<EventLabel, ArrayList<SensorCollection>> data = etdp.parse();
		
		System.out.println(data);
		
		
		
		
//		MobSens m = new MobSens();
//		
//		
//		
//		if (m.trainEvents() > -1) {
//			
//			System.out.println("Finished Training. Ready to Rumble!");
//			
//		}
//		else {
//			
//			System.out.println("Something went horribly wrong!");
//			
//		}
		
		
	}
	
	
	
	/**
	 * 
	 * @param sc
	 * @return
	 */
	public ArrayList<Event> classifyEvents (SensorCollection sc) {
		
		ArrayList<Event> result = new ArrayList<Event>();
		
		try {
				
			result.addAll((new DodgeClassifier()).classify(sc));
			//result.addAll((new BrakingClassifier()).classify(win));
		
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		return result;
		
	}
	
	/**
	 * Trains event classifiers
	 */
	public int trainEvents () {
		
		DodgeTSFactory db = new DodgeTSFactory();
		DodgeTrainer dt = new DodgeTrainer(db.buildTrainingSet());
		
				
		try {
			
			dt.train();
		
		} catch (Exception e) {
		
			e.printStackTrace();
			
			return -1;
		
		}
		
		return 0;
		
	}
	
	
}

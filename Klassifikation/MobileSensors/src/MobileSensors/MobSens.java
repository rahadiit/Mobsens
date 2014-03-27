package MobileSensors;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;

import MobileSensors.Events.Classifiers.DodgeClassifier;
import MobileSensors.Events.Event;
import MobileSensors.Events.Labels.BrakeLabel;
import MobileSensors.Events.Labels.DodgeLabel;
import MobileSensors.Events.Labels.EventLabel;
import MobileSensors.Events.Labels.KerbstoneLabel;
import MobileSensors.Events.Trainers.BrakeTrainer;
import MobileSensors.Events.Trainers.DodgeTrainer;
import MobileSensors.Events.Trainers.KerbstoneTrainer;
import MobileSensors.Helpers.EventRawDataParser;
import MobileSensors.Sensors.SensorRecord;

/**
 * 
 * MobSens Facade
 * 
 * @author henny, thomas, max
 * 
 */
public class MobSens {

	public final static String INDIR = "./input";
	
	public final static String OUTDIR = "./model/";
	
	public final static String EXTENSION_MODEL = ".model";
	public final static String EXTENSION_ARFF  = ".arff";
	public final static String EXTENSION_EVAL  = ".eval";
	
	public final static String FILENAME_DODGE   = "./Dodge";
	public final static String MODELFILE_DODGE = MobSens.OUTDIR + MobSens.FILENAME_DODGE + MobSens.EXTENSION_MODEL;
	public final static String ARFFFILE_DODGE  = MobSens.OUTDIR + MobSens.FILENAME_DODGE + MobSens.EXTENSION_ARFF;
	public final static String EVALFILE_DODGE  = MobSens.OUTDIR + MobSens.FILENAME_DODGE + MobSens.EXTENSION_EVAL;
	
	public final static String FILENAME_Brake   = "./Brake";
	public final static String MODELFILE_Brake = MobSens.OUTDIR + MobSens.FILENAME_Brake + MobSens.EXTENSION_MODEL;
	public final static String ARFFFILE_Brake  = MobSens.OUTDIR + MobSens.FILENAME_Brake + MobSens.EXTENSION_ARFF;
	public final static String EVALFILE_Brake  = MobSens.OUTDIR + MobSens.FILENAME_Brake + MobSens.EXTENSION_EVAL;
	
	public final static String FILENAME_Kerbstone   = "./Kerbstone";
	public final static String MODELFILE_Kerbstone = MobSens.OUTDIR + MobSens.FILENAME_Kerbstone + MobSens.EXTENSION_MODEL;
	public final static String ARFFFILE_Kerbstone  = MobSens.OUTDIR + MobSens.FILENAME_Kerbstone + MobSens.EXTENSION_ARFF;
	public final static String EVALFILE_Kerbstone  = MobSens.OUTDIR + MobSens.FILENAME_Kerbstone + MobSens.EXTENSION_EVAL;
	
	private final static EventLabel[] allEventLabels = {
		DodgeLabel.DODGE,
		DodgeLabel.NODODGE,
		BrakeLabel.BRAKE,
		BrakeLabel.NOBRAKE
		};
	
	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main (String[] args) throws Exception {
		
		EventLabel[] labels = {
				DodgeLabel.DODGE,
				DodgeLabel.NODODGE,
				BrakeLabel.BRAKE,
				BrakeLabel.NOBRAKE,
				KerbstoneLabel.KERBSTONE,
				KerbstoneLabel.NOKERBSTONE
				};
		
		EventRawDataParser etdp = new EventRawDataParser(new File("./input"), labels);
		
		HashMap<SensorRecord, EventLabel> data = etdp.parse();
		
		HashMap<SensorRecord, DodgeLabel> dodgeData = new HashMap<SensorRecord, DodgeLabel>();
		HashMap<SensorRecord, BrakeLabel> brakeData = new HashMap<SensorRecord, BrakeLabel>();
		HashMap<SensorRecord, KerbstoneLabel> kerbstoneData = new HashMap<SensorRecord, KerbstoneLabel>();
		
		
		for (SensorRecord sr : data.keySet()) {
			
			EventLabel label = data.get(sr);
			
			if (label.getClass().equals(DodgeLabel.class)) {
				
				dodgeData.put(sr, (DodgeLabel) label);
				
			}
			
			if (label.getClass().equals(BrakeLabel.class)) {
				
				brakeData.put(sr, (BrakeLabel) label);
				
			}
			
			if (label.getClass().equals(KerbstoneLabel.class)) {
				
				kerbstoneData.put(sr, (KerbstoneLabel) label);
				
			}
			
		}
//		
//		DodgeTrainer dt = new DodgeTrainer(dodgeData);
//		dt.train();
		
//		BrakeTrainer bt = new BrakeTrainer(brakeData);
//		bt.train();
		
		KerbstoneTrainer kt = new KerbstoneTrainer(kerbstoneData);
		kt.train();
		
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
	public ArrayList<Event> classifyEvents (SensorRecord sc) {
		
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
		
//		DodgeTrainer dt = new DodgeTrainer();
//		
//				
//		try {
//			
//			dt.train();
//		
//		} catch (Exception e) {
//		
//			e.printStackTrace();
//			
//			return -1;
//		
//		}
		
		return 0;
		
	}
	
	
}

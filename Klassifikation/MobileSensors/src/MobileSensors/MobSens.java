package MobileSensors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import weka.core.Instances;
import MobileSensors.Events.Event;
import MobileSensors.Events.Labels.*;
import MobileSensors.Events.Classifiers.*;
import MobileSensors.Events.Trainers.*;
import MobileSensors.Helpers.EventRawDataParser;
import MobileSensors.Sensors.SensorRecord;

/**
 * 
 * MobSens Facade
 * 
 * MobSens is the facade class to the mobile sensing weka middleware.
 * <br>
 * Usage:
 * <br>
 * (1) Event classification:
 * <pre>
 * SensorRecord sr = ...
 * MobSens mobs = new MobSens();
 * mobs.setBrakeModelFile(fileToBrakeModel);
 * mobs.setDodgeModelFile(fileToDodgeModel);
 * mobs.setKerbstoneModelFile(fileToKerbstoneModel);
 * ArrayList<Event> events = mobs.classifyEvents(sr);
 * </pre>
 * (2) Event training:
 * <pre>
 * File inp = new File(pathToInputDir);
 * MobSens mobs = new MobSens();
 * mobs.setBrakeModelFile(fileToBrakeModel);
 * mobs.setDodgeModelFile(fileToDodgeModel);
 * mobs.setKerbstoneModelFile(fileToKerbstoneModel);
 * mobs.trainEvents(inp);
 * </pre>
 * 
 * @author henny, thomas, max
 * 
 */
public class MobSens {

	public final static String DEFAULT_INPUT_DIR = "./input";
	public final static String DEFAULT_MODELFILE_DODGE = "./model/Dodge.model";
	public final static String DEFAULT_MODELFILE_BRAKE = "./model/Brake.model";
	public final static String DEFAULT_MODELFILE_KERBSTONE = "./model/Kerbstone.model";
	
	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main (String[] args) throws Exception {
		
		DodgeClassifier dc = new DodgeClassifier(new File(MobSens.DEFAULT_MODELFILE_DODGE));
		
		dc.test();
		
//		MobSens m = new MobSens();
//		m.trainEvents(new File(MobSens.DEFAULT_INPUT_DIR));
		
		
	}
	
	private File brakeModelFile;
	private File dodgeModelFile;
	private File kerbstoneModelFile;
	
	public MobSens () {
		this.brakeModelFile = new File(MobSens.DEFAULT_MODELFILE_BRAKE);
		this.dodgeModelFile = new File(MobSens.DEFAULT_MODELFILE_DODGE);
		this.kerbstoneModelFile = new File(MobSens.DEFAULT_MODELFILE_KERBSTONE);
		
	}
	
	public void setBrakeModelFile(File brakeModelFile) {
		this.brakeModelFile = brakeModelFile;
	}

	public void setDodgeModelFile(File dodgeModelFile) {
		this.dodgeModelFile = dodgeModelFile;
	}

	public void setKerbstoneModelFile(File kerbstoneModelFile) {
		this.kerbstoneModelFile = kerbstoneModelFile;
	}
	
	
	/**
	 * 
	 * @param sc
	 * @return
	 */
	public ArrayList<Event> classifyEvents (SensorRecord sc) {
		
		ArrayList<Event> result = new ArrayList<Event>();
		
		try {
			
			result.addAll((new BrakeClassifier(this.brakeModelFile)).classifyEvents(sc));
			result.addAll((new DodgeClassifier(this.dodgeModelFile)).classifyEvents(sc));
			result.addAll((new KerbstoneClassifier(this.kerbstoneModelFile)).classifyEvents(sc));
		
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		return result;
		
	}
	
	/**
	 * Trains events from data in a given input directory.
	 * The directory outline has to be:
	 * <br>
	 * <br>
	 * <pre>
	 * [event] 
	 * 	> record_[id]
	 * 		> [sensor].csv
	 * no[event]
	 * 	> record_[id] 
	 * 		> [sensor].csv	
	 * </pre> 
	 * 
	 * @param inputDir File to data input directory
	 * @return
	 */
	public int trainEvents (File inputDir) {
		

		System.out.println("MobSens started event training. This may take some time... Go, grab some coffee!");
		System.out.println();
		
		
		EventLabel[] labels = {
				DodgeLabel.DODGE,
				DodgeLabel.NODODGE,
				BrakeLabel.BRAKE,
				BrakeLabel.NOBRAKE,
				KerbstoneLabel.KERBSTONE,
				KerbstoneLabel.NOKERBSTONE
				};
		
		EventRawDataParser etdp = new EventRawDataParser(inputDir, labels);
		
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
		

		try {

			(new BrakeTrainer(this.brakeModelFile)).train(brakeData);
			(new DodgeTrainer(this.dodgeModelFile)).train(dodgeData);
			(new KerbstoneTrainer(this.kerbstoneModelFile)).train(kerbstoneData);
			
			System.out.println();
			System.out.println("Finished Training. Ready to Rumble!");
			
		
		} catch (Exception e) {
			
			System.out.println();
			System.out.println("Something went horribly wrong!");
			
			e.printStackTrace();
			
			
			
			return -1;
		
		}
		
		return 0;
		
	}
	
	
}

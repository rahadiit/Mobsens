package MobileSensors;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

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
		
				
		MobSens m = new MobSens();
		m.trainEvents(new File(MobSens.DEFAULT_INPUT_DIR));
		
		
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
	 * Trains event classifiers
	 */
	public int trainEvents (File indir) {
		

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
		
		EventRawDataParser etdp = new EventRawDataParser(indir, labels);
		
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

//			(new BrakeTrainer(this.brakeModelFile)).train(brakeData);
			(new DodgeTrainer(this.dodgeModelFile)).train(dodgeData);
//			(new KerbstoneTrainer(this.kerbstoneModelFile)).train(kerbstoneData);
			
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

package MobileSensors.Events.Classifiers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import weka.core.Instance;
import weka.core.Instances;
import MobileSensors.Events.Event;
import MobileSensors.Events.EventType;
import MobileSensors.Events.ARFactories.DodgeARFactory;
import MobileSensors.Events.Labels.DodgeLabel;
import MobileSensors.Sensors.SensorRecord;

public class DodgeClassifier extends EventClassifier<DodgeLabel> {

	public DodgeClassifier(File modelFile) throws Exception {
		
		super(new DodgeARFactory(), modelFile);

	}

	@Override
	public ArrayList<Event> classifyEvents (SensorRecord sr) {
		
		ArrayList<Event> events = new ArrayList<Event>();
		
		try {
			
			if (DodgeLabel.DODGE.toString().equals(this.classify(sr))) {

				long startTime = sr.getAcceleration().get(0).getTime();
				long endTime = sr.getAcceleration().get(sr.getAcceleration().size()-1).getTime();
				
				events.add(new Event(startTime,endTime,EventType.DODGE));
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return events;
	}
	
	public void test () throws Exception {
		
		BufferedReader reader = new BufferedReader(new FileReader("./out/Dodge.arff"));
		Instances data = new Instances(reader);
		reader.close();

		data.setClassIndex(data.numAttributes() - 1);
		
		Instance testee = data.instance(1575);
	
		Instance fv = (new DodgeARFactory()).createFeatureVector();
		
		for (int i=0; i < testee.numAttributes()-1; i++) {
			
			fv.setValue(i, testee.value(i));
			
		}
		

		System.out.println(testee);
		System.out.println(fv);
		System.out.println(this.classifier.classifyInstance(fv));
		System.out.println((fv.classAttribute().value((int) this.classifier.classifyInstance(fv))));
		
		
		
	}
	
}

package MobileSensors.Events.Classifiers;

import java.io.File;
import java.util.ArrayList;

import MobileSensors.Events.Event;
import MobileSensors.Events.ARFactories.ARFactory;
import MobileSensors.Events.Labels.EventLabel;
import MobileSensors.Sensors.SensorRecord;
import weka.classifiers.Classifier;
import weka.core.SerializationHelper;

public abstract class EventClassifier<L extends EventLabel> {

	abstract public ArrayList<Event> classifyEvents (SensorRecord sr);
	
	protected ARFactory<L> arFactory;
	protected Classifier classifier;
	
	public EventClassifier (ARFactory<L> arFactory, File modelFile) throws Exception {
		
		this.arFactory = arFactory;
		this.classifier = (Classifier) SerializationHelper.read(modelFile.toString());
		
	}
	
	public double classify (SensorRecord sr) throws Exception {
		
		return this.classifier.classifyInstance(this.arFactory.createFeatureVector(sr));
		
	}
	
	
	
	
	
}

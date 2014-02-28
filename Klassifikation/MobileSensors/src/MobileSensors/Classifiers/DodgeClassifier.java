package MobileSensors.Classifiers;

import java.util.ArrayList;

import MobileSensors.Sensors.Accelerometer;
import MobileSensors.Events.Event;
import MobileSensors.Events.EventType;
import MobileSensors.Helpers.AccelerationFeatureVector;
import MobileSensors.Wekas.DodgeJ48;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

/**
 * 
 * Dodge Classifier
 * 
 * @author henny, thomas, max
 * 
 */
public class DodgeClassifier implements EventClassifier{
	
	
	private FastVector dodgeAttributes;
	private Instances dodgeRelation;
	
	public DodgeClassifier ()  {
		
		this.dodgeAttributes = new FastVector();
		
		this.dodgeAttributes.addElement(new Attribute("xArithMean"));
		this.dodgeAttributes.addElement(new Attribute("yArithMean"));
		this.dodgeAttributes.addElement(new Attribute("zArithMean"));
		
		this.dodgeAttributes.addElement(new Attribute("xHarmMean"));
		this.dodgeAttributes.addElement(new Attribute("yHarmMean"));
		this.dodgeAttributes.addElement(new Attribute("zHarmMean"));
		
		this.dodgeAttributes.addElement(new Attribute("xVariance"));
		this.dodgeAttributes.addElement(new Attribute("yVariance"));
		this.dodgeAttributes.addElement(new Attribute("zVariance"));
		
		this.dodgeAttributes.addElement(new Attribute("xKurtosis"));
		this.dodgeAttributes.addElement(new Attribute("yKurtosis"));
		this.dodgeAttributes.addElement(new Attribute("zKurtosis"));
		
		this.dodgeAttributes.addElement(new Attribute("label"));
		
		
		
		this.dodgeRelation = new Instances("Dodge", this.dodgeAttributes, 1);
		this.dodgeRelation.setClassIndex(this.dodgeAttributes.size()-1);
		
		
		
		
	}
	
	private boolean classify (AccelerationFeatureVector v) throws Exception {
		
		Instance i = new Instance(this.dodgeAttributes.size());
		i.setDataset(this.dodgeRelation);
		
		i.setValue(0, v.getXArithMean());
		i.setValue(1, v.getYArithMean());
		i.setValue(2, v.getZArithMean());
		i.setValue(3, v.getXHarmMean());
		i.setValue(4, v.getYHarmMean());
		i.setValue(5, v.getZHarmMean());
		i.setValue(6, v.getXVariance());
		i.setValue(7, v.getYVariance());
		i.setValue(8, v.getZVariance());
		i.setValue(9, v.getXKurtosis());
		i.setValue(10, v.getYKurtosis());
		i.setValue(11, v.getZKurtosis());
		
		return (new DodgeJ48()).classifyInstance(i) < 0.5;
		
	}


	@Override
	public ArrayList<Event> getEvents(Window win) {

		ArrayList<Event> result = new ArrayList<>();
		
		if (win.getAcceleration().size() > 0) {
			
			ArrayList<Accelerometer> acc = win.getAcceleration();
			
			AccelerationFeatureVector afv = new AccelerationFeatureVector(acc, "");
			
			try {
				
				if (this.classify(afv)) {
					
					result.add(new Event(acc.get(0).getTime(), EventType.DODGE));
					
				}
				
			}
			catch(Exception e) {
				
				
			}
		}
		
		return result;
		
	}
	
}

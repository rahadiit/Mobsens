package MobileSensors.Classifiers;

import java.util.ArrayList;

import MobileSensors.Classifiers.Weka.DodgeJ48;
import MobileSensors.Helper.AccelerationFeatureVector;
import MobileSensors.Storage.Event.Event;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class DodgeClassifier implements EventClassifier{
	
	
	private FastVector dodgeAttributes;
	private Instances dodgeRelation;
	
	public DodgeClassifier () {
		
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
	
	public boolean classify (AccelerationFeatureVector v) throws Exception {
		
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
		
		DodgeJ48 dodge48 = new DodgeJ48();
		
		return dodge48.classifyInstance(i) < 0.5;
		
	}

	@Override
	public ArrayList<Event> getEvents() {
		
		return null;
	}
	
}

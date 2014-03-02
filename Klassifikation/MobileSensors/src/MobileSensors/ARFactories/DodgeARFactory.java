package MobileSensors.Relations;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class DodgeAttributeRelationFactory implements AttributeRelationFactory {

	public final static String LABEL_DODGE = "DODGE";
	public final static String LABEL_NODODGE = "NODODGE";
	public final static String RELATION_NAME = "MobSensDodge";
	
	public FastVector getAttributes () {
		
		FastVector attrs = new FastVector();
		
		attrs.addElement(new Attribute("xArithMean"));
		attrs.addElement(new Attribute("yArithMean"));
		attrs.addElement(new Attribute("zArithMean"));
		
		attrs.addElement(new Attribute("xHarmMean"));
		attrs.addElement(new Attribute("yHarmMean"));
		attrs.addElement(new Attribute("zHarmMean"));
		
		attrs.addElement(new Attribute("xVariance"));
		attrs.addElement(new Attribute("yVariance"));
		attrs.addElement(new Attribute("zVariance"));
		
		attrs.addElement(new Attribute("xKurtosis"));
		attrs.addElement(new Attribute("yKurtosis"));
		attrs.addElement(new Attribute("zKurtosis"));
		
		
		FastVector labels = new FastVector();
		labels.addElement(this.LABEL_DODGE);
		labels.addElement(this.LABEL_NODODGE);
		
		attrs.addElement(new Attribute("label", labels));
		
		return attrs;
		
	}
	
	public Instances getRealtion(int size) {
		
		FastVector attrs = this.getAttributes();
		
		Instances is = new Instances(this.RELATION_NAME, attrs, size);
		is.setClassIndex(attrs.size()-1);
		
		return is;
		
	}
	
	
	public Instance getVector(int size) {
		
		Instance i = new Instance(size);
		i.setDataset(this.getRealtion(0));
		
		return i;
		
	}
	
}

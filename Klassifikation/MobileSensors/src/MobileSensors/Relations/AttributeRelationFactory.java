package MobileSensors.Relations;

import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public interface AttributeRelationFactory {

	
	public FastVector getAttributes();
	public Instances getRealtion(int size);
	public Instance getVector(int size);
	
	
}

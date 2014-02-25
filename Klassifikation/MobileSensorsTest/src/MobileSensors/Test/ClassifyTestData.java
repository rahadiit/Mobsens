package MobileSensors.Test;

import org.apache.commons.lang3.StringUtils;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import MobileSensors.Weka.J48;

public class ClassifyTestData {
	public static void main(String[] args) throws Exception{
		J48 j48 = new J48();
		
		Attribute Attribute1 = new Attribute("xArithMean");
		Attribute Attribute2 = new Attribute("yArithMean");
		Attribute Attribute3 = new Attribute("zArithMean");
		Attribute Attribute4 = new Attribute("xHarmMean");
		Attribute Attribute5 = new Attribute("yHarmMean");
		Attribute Attribute6 = new Attribute("zHarmMean");
		Attribute Attribute7 = new Attribute("xVariance");
		Attribute Attribute8 = new Attribute("yVariance");
		Attribute Attribute9 = new Attribute("zVariance");
		Attribute Attribute10 = new Attribute("xKurtosis");
		Attribute Attribute11 = new Attribute("yKurtosis");
		Attribute Attribute12 = new Attribute("zKurtosis");
		Attribute Attribute13 = new Attribute("label");
		 
		 // Declare the feature vector
		 FastVector fvWekaAttributes = new FastVector(13);
		 fvWekaAttributes.addElement(Attribute1);    
		 fvWekaAttributes.addElement(Attribute2);    
		 fvWekaAttributes.addElement(Attribute3);
		 fvWekaAttributes.addElement(Attribute4);
		 fvWekaAttributes.addElement(Attribute5);
		 fvWekaAttributes.addElement(Attribute6);
		 fvWekaAttributes.addElement(Attribute7);
		 fvWekaAttributes.addElement(Attribute8);
		 fvWekaAttributes.addElement(Attribute9);
		 fvWekaAttributes.addElement(Attribute10);
		 fvWekaAttributes.addElement(Attribute11);
		 fvWekaAttributes.addElement(Attribute12);
		 fvWekaAttributes.addElement(Attribute13);
		 
		 
		 
		 Instances isTrainingSet = new Instances("Rel", fvWekaAttributes, 10); 
		 isTrainingSet.setClassIndex(12);
		 
		 //EXAMPLES
		 //0.7345165601372717,2.5371998667716977,2.5371998667716977,0.689927989021526,0.20677381035082987,0.20677381035082987,6.698235049411811,14.0644992075358,14.0644992075358,0.4283963671158224,0.2302094765209275,0.2302094765209275,DODGE
		//0.268638856001198,2.3240401238948105,2.3240401238948105,-0.5919226445868719,0.4029938321339652,0.4029938321339652,5.7609695143359145,6.804690507107232,6.804690507107232,0.7035384797539397,0.5348275587940678,0.5348275587940678,NODODGE
		 Instance noDodgeExample = new Instance(13);
		 noDodgeExample.setValue((Attribute)fvWekaAttributes.elementAt(0), 0.268638856001198);      
		 noDodgeExample.setValue((Attribute)fvWekaAttributes.elementAt(1), 2.3240401238948105);      
		 noDodgeExample.setValue((Attribute)fvWekaAttributes.elementAt(2), 2.3240401238948105);
		 noDodgeExample.setValue((Attribute)fvWekaAttributes.elementAt(3), -0.5919226445868719);
		 noDodgeExample.setValue((Attribute)fvWekaAttributes.elementAt(4), 0.4029938321339652);
		 noDodgeExample.setValue((Attribute)fvWekaAttributes.elementAt(5), 0.4029938321339652);
		 noDodgeExample.setValue((Attribute)fvWekaAttributes.elementAt(6), 5.7609695143359145);
		 noDodgeExample.setValue((Attribute)fvWekaAttributes.elementAt(7), 6.804690507107232);
		 noDodgeExample.setValue((Attribute)fvWekaAttributes.elementAt(8), 6.804690507107232);
		 noDodgeExample.setValue((Attribute)fvWekaAttributes.elementAt(9), 0.7035384797539397);
		 noDodgeExample.setValue((Attribute)fvWekaAttributes.elementAt(10), 0.5348275587940678);
		 noDodgeExample.setValue((Attribute)fvWekaAttributes.elementAt(11), 0.5348275587940678);
		 noDodgeExample.setDataset(isTrainingSet);
		 
		 
		 Instance dodgeExample = new Instance(13);
		 dodgeExample.setValue((Attribute)fvWekaAttributes.elementAt(0), 0.7345165601372717);      
		 dodgeExample.setValue((Attribute)fvWekaAttributes.elementAt(1), 2.5371998667716977);      
		 dodgeExample.setValue((Attribute)fvWekaAttributes.elementAt(2), 2.5371998667716977);
		 dodgeExample.setValue((Attribute)fvWekaAttributes.elementAt(3), 0.689927989021526);
		 dodgeExample.setValue((Attribute)fvWekaAttributes.elementAt(4), 0.20677381035082987);
		 dodgeExample.setValue((Attribute)fvWekaAttributes.elementAt(5), 0.20677381035082987);
		 dodgeExample.setValue((Attribute)fvWekaAttributes.elementAt(6), 6.698235049411811);
		 dodgeExample.setValue((Attribute)fvWekaAttributes.elementAt(7), 14.0644992075358);
		 dodgeExample.setValue((Attribute)fvWekaAttributes.elementAt(8), 14.0644992075358);
		 dodgeExample.setValue((Attribute)fvWekaAttributes.elementAt(9), 0.4283963671158224);
		 dodgeExample.setValue((Attribute)fvWekaAttributes.elementAt(10), 0.2302094765209275);
		 dodgeExample.setValue((Attribute)fvWekaAttributes.elementAt(11), 0.2302094765209275);
		 dodgeExample.setDataset(isTrainingSet);
		 
		
		double resultDodge = j48.classifyInstance(dodgeExample);
		double resultNoDodge = j48.classifyInstance(noDodgeExample);
		
		System.out.println("DODGE: \t" + (resultDodge > 0.5 ? "No Dodge" : "Dodge"));
		System.out.println("NO DODGE: \t" + (resultNoDodge > 0.5 ? "No Dodge" : "Dodge"));
		
		
	}
}

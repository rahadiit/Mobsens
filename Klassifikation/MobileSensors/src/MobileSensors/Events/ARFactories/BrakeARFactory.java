package MobileSensors.Events.ARFactories;

import java.util.Map;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import MobileSensors.Events.Labels.BrakeLabel;
import MobileSensors.Helpers.FeatureMathHelper;
import MobileSensors.Sensors.SensorRecord;
import MobileSensors.Sensors.ColumnVectors.AccelerometerColumnVector;
import MobileSensors.Sensors.ColumnVectors.LocationColumnVector;

public class BrakeARFactory implements ARFactory<BrakeLabel> {

	private final static String RELATION_NAME = "MobSensBrake";
	
	private final Attribute xArithMean;
	private final Attribute yArithMean;
	private final Attribute zArithMean;
	private final Attribute speedArithMean;
	
	private final Attribute xHarmMean;
	private final Attribute yHarmMean;
	private final Attribute zHarmMean;
	private final Attribute speedHarmMean;
	
	private final Attribute xKurtosis;
	private final Attribute yKurtosis;
	private final Attribute zKurtosis;
	private final Attribute speedKurtosis;
	
	private final Attribute xVariance;
	private final Attribute yVariance;
	private final Attribute zVariance;
	private final Attribute speedVariance;
	
	private final Attribute xS2;
	private final Attribute yS2;
	private final Attribute zS2;
	private final Attribute speedS2;
	
	private final Attribute brakeLabel;
	
	private final FastVector brakeFeatureVector;
	
	
	public BrakeARFactory () {
		
		FastVector labels = new FastVector();
		labels.addElement(BrakeLabel.BRAKE.toString());
		labels.addElement(BrakeLabel.NOBRAKE.toString());
		
		this.brakeLabel = new Attribute("label", labels);
		
		
		this.xArithMean = new Attribute("xArithMean");
		this.yArithMean = new Attribute("yArithMean");
		this.zArithMean = new Attribute("zArithMean");
		this.speedArithMean = new Attribute("speedArithMean");
		
		this.xHarmMean = new Attribute("xHarmMean");
		this.yHarmMean = new Attribute("yHarmMean");
		this.zHarmMean = new Attribute("zHarmMean");
		this.speedHarmMean = new Attribute("speedHarmMean");
		
		this.xKurtosis = new Attribute("xKurtosis");
		this.yKurtosis = new Attribute("yKurtosis");
		this.zKurtosis = new Attribute("zKurtosis");
		this.speedKurtosis = new Attribute("speedKurtosis");
		
		this.xVariance = new Attribute("xVariance");
		this.yVariance = new Attribute("yVariance");
		this.zVariance = new Attribute("zVariance");
		this.speedVariance = new Attribute("speedVariance");
		
		this.xS2 = new Attribute("xS2");
		this.yS2 = new Attribute("yS2");
		this.zS2 = new Attribute("zS2");
		this.speedS2 = new Attribute("speedS2");
		
		
		this.brakeFeatureVector = new FastVector();
		this.brakeFeatureVector.addElement(this.xArithMean);
		this.brakeFeatureVector.addElement(this.yArithMean);
		this.brakeFeatureVector.addElement(this.zArithMean);
//		this.brakeFeatureVector.addElement(this.speedArithMean);
		
		this.brakeFeatureVector.addElement(this.xHarmMean);
		this.brakeFeatureVector.addElement(this.yHarmMean);
		this.brakeFeatureVector.addElement(this.zHarmMean);
//		this.brakeFeatureVector.addElement(this.speedHarmMean);
		
		this.brakeFeatureVector.addElement(this.xKurtosis);
		this.brakeFeatureVector.addElement(this.yKurtosis);
		this.brakeFeatureVector.addElement(this.zKurtosis);
//		this.brakeFeatureVector.addElement(this.speedKurtosis);
		
		this.brakeFeatureVector.addElement(this.xVariance);
		this.brakeFeatureVector.addElement(this.yVariance);
		this.brakeFeatureVector.addElement(this.zVariance);
//		this.brakeFeatureVector.addElement(this.speedVariance);
		
		this.brakeFeatureVector.addElement(this.xS2);
		this.brakeFeatureVector.addElement(this.yS2);
		this.brakeFeatureVector.addElement(this.zS2);
//		this.brakeFeatureVector.addElement(this.speedS2);
		
		this.brakeFeatureVector.addElement(this.brakeLabel);
		
	}
	
	@Override
	public Instances createTrainingSet() {

		/* Note: capacity=0 for the Intances constructor implies
		 * infinity, as it does for every other number, since its
		 * internally used to construct an instance of FastVector.
		 * But FastVector provides an overflow prevention mechanism
		 * which increases its capacity if you try to add an item 
		 * in an already full vector.
		 */
		
		Instances insts = new Instances(
				BrakeARFactory.RELATION_NAME,
				this.brakeFeatureVector,
				0); // <- see note above 
		
		
		insts.setClass(this.brakeLabel);
		
		return insts;
		
	}

	@Override
	public Instances createTrainingSet(Map<SensorRecord, BrakeLabel> sensorCollections) {

		Instances insts = this.createTrainingSet();
		
		for (SensorRecord sc : sensorCollections.keySet()) {
			
			insts.add(this.createFeatureVector(sc, sensorCollections.get(sc)));
			
		}
		
		return insts;

		
	}

	public Instance createFeatureVector () {
		
		Instance inst = new Instance(this.brakeFeatureVector.size());
		inst.setDataset(this.createTrainingSet());
		
		return inst;
		
	}
	
	@Override
	public Instance createFeatureVector(SensorRecord sc) {
		
		AccelerometerColumnVector accWin = new AccelerometerColumnVector(sc.getAcceleration());
		LocationColumnVector locWin = new LocationColumnVector(sc.getLocation());
		
		Instance inst = new Instance(this.brakeFeatureVector.size());
		
		inst.setDataset(this.createTrainingSet());
		
		inst.setValue(this.xArithMean, FeatureMathHelper.arithMean(accWin.getXs()));
		inst.setValue(this.yArithMean, FeatureMathHelper.arithMean(accWin.getYs()));
		inst.setValue(this.zArithMean, FeatureMathHelper.arithMean(accWin.getZs()));
//		inst.setValue(this.speedArithMean, FeatureMathHelper.arithMean(locWin.getSpeeds()));
		
		inst.setValue(this.xHarmMean, FeatureMathHelper.harmMean(accWin.getXs()));
		inst.setValue(this.yHarmMean, FeatureMathHelper.harmMean(accWin.getYs()));
		inst.setValue(this.zHarmMean, FeatureMathHelper.harmMean(accWin.getZs()));
//		inst.setValue(this.speedHarmMean, FeatureMathHelper.harmMean(locWin.getSpeeds()));
		
		inst.setValue(this.xKurtosis, FeatureMathHelper.kurtosis(accWin.getXs()));
		inst.setValue(this.yKurtosis, FeatureMathHelper.kurtosis(accWin.getYs()));
		inst.setValue(this.zKurtosis, FeatureMathHelper.kurtosis(accWin.getZs()));
//		inst.setValue(this.speedKurtosis, FeatureMathHelper.kurtosis(locWin.getSpeeds()));

		inst.setValue(this.xVariance, FeatureMathHelper.variance(accWin.getXs()));
		inst.setValue(this.yVariance, FeatureMathHelper.variance(accWin.getYs()));
		inst.setValue(this.zVariance, FeatureMathHelper.variance(accWin.getZs()));
//		inst.setValue(this.speedVariance, FeatureMathHelper.variance(locWin.getSpeeds()));

		inst.setValue(this.xS2, FeatureMathHelper.s2(accWin.getXs()));
		inst.setValue(this.yS2, FeatureMathHelper.s2(accWin.getYs()));
		inst.setValue(this.zS2, FeatureMathHelper.s2(accWin.getZs()));
//		inst.setValue(this.speedS2, FeatureMathHelper.s2(locWin.getSpeeds()));
		
		return inst;
		
	}

	@Override
	public Instance createFeatureVector(SensorRecord sc, BrakeLabel label) {
		
		Instance inst = this.createFeatureVector(sc);
		
		inst.setValue(this.brakeLabel, label.toString());
		
		return inst;
		
	}

}

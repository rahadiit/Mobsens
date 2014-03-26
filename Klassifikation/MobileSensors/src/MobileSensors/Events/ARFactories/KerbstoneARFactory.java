package MobileSensors.Events.ARFactories;

import java.util.Map;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import MobileSensors.Events.Labels.DodgeLabel;
import MobileSensors.Events.Labels.KerbstoneLabel;
import MobileSensors.Helpers.FeatureMathHelper;
import MobileSensors.Sensors.SensorRecord;
import MobileSensors.Sensors.ColumnVectors.AccelerometerColumnVector;

public class KerbstoneARFactory implements ARFactory<KerbstoneLabel>{

	private final static String RELATION_NAME = "MobSensKerbstone";
	
	private final Attribute xArithMean;
	private final Attribute yArithMean;
	private final Attribute zArithMean;
	
	private final Attribute xHarmMean;
	private final Attribute yHarmMean;
	private final Attribute zHarmMean;
	
	private final Attribute xKurtosis;
	private final Attribute yKurtosis;
	private final Attribute zKurtosis;
	
	private final Attribute xVariance;
	private final Attribute yVariance;
	private final Attribute zVariance;
	
	private final Attribute xS2;
	private final Attribute yS2;
	private final Attribute zS2;
	
	private final Attribute kerbstoneLabel;
	
	private final FastVector kerbstoneFeatureVector;
	
	public KerbstoneARFactory () {
		
		FastVector labels = new FastVector();
		labels.addElement(KerbstoneLabel.KERBSTONE.toString());
		labels.addElement(KerbstoneLabel.NOKERBSTONE.toString());
		
		this.kerbstoneLabel = new Attribute("label", labels);
		
		
		this.xArithMean = new Attribute("xArithMean");
		this.yArithMean = new Attribute("yArithMean");
		this.zArithMean = new Attribute("zArithMean");
		
		this.xHarmMean = new Attribute("xHarmMean");
		this.yHarmMean = new Attribute("yHarmMean");
		this.zHarmMean = new Attribute("zHarmMean");
		
		this.xKurtosis = new Attribute("xKurtosis");
		this.yKurtosis = new Attribute("yKurtosis");
		this.zKurtosis = new Attribute("zKurtosis");
		
		this.xVariance = new Attribute("xVariance");
		this.yVariance = new Attribute("yVariance");
		this.zVariance = new Attribute("zVariance");
		
		this.xS2 = new Attribute("xS2");
		this.yS2 = new Attribute("yS2");
		this.zS2 = new Attribute("zS2");
		
		this.kerbstoneFeatureVector = new FastVector();
		this.kerbstoneFeatureVector.addElement(this.xArithMean);
		this.kerbstoneFeatureVector.addElement(this.yArithMean);
		this.kerbstoneFeatureVector.addElement(this.zArithMean);
		
		this.kerbstoneFeatureVector.addElement(this.xHarmMean);
		this.kerbstoneFeatureVector.addElement(this.yHarmMean);
		this.kerbstoneFeatureVector.addElement(this.zHarmMean);
		
		this.kerbstoneFeatureVector.addElement(this.xKurtosis);
		this.kerbstoneFeatureVector.addElement(this.yKurtosis);
		this.kerbstoneFeatureVector.addElement(this.zKurtosis);
		
		this.kerbstoneFeatureVector.addElement(this.xVariance);
		this.kerbstoneFeatureVector.addElement(this.yVariance);
		this.kerbstoneFeatureVector.addElement(this.zVariance);
		
		this.kerbstoneFeatureVector.addElement(this.xS2);
		this.kerbstoneFeatureVector.addElement(this.yS2);
		this.kerbstoneFeatureVector.addElement(this.zS2);
		
		this.kerbstoneFeatureVector.addElement(this.kerbstoneLabel);
		
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
				KerbstoneARFactory.RELATION_NAME,
				this.kerbstoneFeatureVector,
				0); // <- see note above 
		
		
		insts.setClass(this.kerbstoneLabel);
		
		return insts;
		
	}

	@Override
	public Instances createTrainingSet(Map<SensorRecord, KerbstoneLabel> sensorCollections) {
		
		Instances insts = this.createTrainingSet();
		
		for (SensorRecord sc : sensorCollections.keySet()) {
			
			insts.add(this.createFeatureVector(sc, sensorCollections.get(sc)));
			
		}
		
		return insts;
		
	}

	@Override
	public Instance createFeatureVector(SensorRecord sc) {
		
		AccelerometerColumnVector accWin = new AccelerometerColumnVector(sc.getAcceleration());
		
		Instance inst = new Instance(this.kerbstoneFeatureVector.size());
		
		inst.setDataset(this.createTrainingSet());
		
		inst.setValue(this.xArithMean, FeatureMathHelper.arithMean(accWin.getXs()));
		inst.setValue(this.yArithMean, FeatureMathHelper.arithMean(accWin.getYs()));
		inst.setValue(this.zArithMean, FeatureMathHelper.arithMean(accWin.getZs()));
		
		inst.setValue(this.xHarmMean, FeatureMathHelper.harmMean(accWin.getXs()));
		inst.setValue(this.yHarmMean, FeatureMathHelper.harmMean(accWin.getYs()));
		inst.setValue(this.zHarmMean, FeatureMathHelper.harmMean(accWin.getZs()));
		
		inst.setValue(this.xKurtosis, FeatureMathHelper.kurtosis(accWin.getXs()));
		inst.setValue(this.yKurtosis, FeatureMathHelper.kurtosis(accWin.getYs()));
		inst.setValue(this.zKurtosis, FeatureMathHelper.kurtosis(accWin.getZs()));

		inst.setValue(this.xVariance, FeatureMathHelper.variance(accWin.getXs()));
		inst.setValue(this.yVariance, FeatureMathHelper.variance(accWin.getYs()));
		inst.setValue(this.zVariance, FeatureMathHelper.variance(accWin.getZs()));
		
		inst.setValue(this.xS2, FeatureMathHelper.s2(accWin.getXs()));
		inst.setValue(this.yS2, FeatureMathHelper.s2(accWin.getYs()));
		inst.setValue(this.zS2, FeatureMathHelper.s2(accWin.getZs()));
		
		return inst;
		
	}

	@Override
	public Instance createFeatureVector(SensorRecord sc, KerbstoneLabel label) {
		
		Instance inst = this.createFeatureVector(sc);
		
		inst.setValue(this.kerbstoneLabel, label.toString());
		
		return inst;
		
	}

}

package MobileSensors.Events.ARFactories;

import org.apache.commons.lang3.StringUtils;

import MobileSensors.Events.Labels.DodgeLabel;
import MobileSensors.Helpers.FeatureMathHelper;
import MobileSensors.Sensors.SensorCollection;
import MobileSensors.Sensors.Windows.AccelerometerWindow;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class DodgeARFactory implements ARFactory<DodgeLabel> {

	public final static String LABEL_DODGE = "DODGE";
	public final static String LABEL_NODODGE = "NODODGE";
	public final static String RELATION_NAME = "MobSensDodge";
	
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
	
	private final Attribute dodgeLabel;
	
	private final FastVector dodgeFeatureVector;
		
	public DodgeARFactory () {
		
		FastVector labels = new FastVector();
		labels.addElement(DodgeLabel.DODGE.toString());
		labels.addElement(DodgeLabel.NODODGE.toString());
		
		this.dodgeLabel = new Attribute("label", labels);
		
		
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
		
		
		this.dodgeFeatureVector = new FastVector();
		this.dodgeFeatureVector.addElement(this.xArithMean);
		this.dodgeFeatureVector.addElement(this.yArithMean);
		this.dodgeFeatureVector.addElement(this.zArithMean);
		
		this.dodgeFeatureVector.addElement(this.xHarmMean);
		this.dodgeFeatureVector.addElement(this.yHarmMean);
		this.dodgeFeatureVector.addElement(this.zHarmMean);
		
		this.dodgeFeatureVector.addElement(this.xKurtosis);
		this.dodgeFeatureVector.addElement(this.yKurtosis);
		this.dodgeFeatureVector.addElement(this.zKurtosis);
		
		this.dodgeFeatureVector.addElement(this.xVariance);
		this.dodgeFeatureVector.addElement(this.yVariance);
		this.dodgeFeatureVector.addElement(this.zVariance);
		
		this.dodgeFeatureVector.addElement(this.dodgeLabel);
		
	}
		
	/**
	 * Creates new weka training-set of a given size.
	 * The training-set will be initialized with the dodge attribute relation.
	 * 
	 * @param int size
	 * @return weka.core.Instances
	 */
	@Override
	public Instances createTrainingSet(int size) {
		
		Instances insts = new Instances(
				DodgeARFactory.RELATION_NAME,
				this.dodgeFeatureVector,
				size);
		
		
		insts.setClass(this.dodgeLabel);
		
		return insts;
		
	}

	
	/**
	 * Creates new unlabeled weka feature-vector for the dodge attribute relation
	 * 
	 * @param win
	 * @return
	 */
	@Override
	public Instance createFeatureVector(SensorCollection win) {
		
		AccelerometerWindow accWin = new AccelerometerWindow(win.getAcceleration());
				
		Instance inst = new Instance(this.dodgeFeatureVector.size());
		
		inst.setDataset(this.createTrainingSet(0));
		
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
		
		return inst;
		
	}


	/**
	 * Creates new labeled weka feature-vector for the dodge attribute relation
	 * 
	 * @param win
	 * @param label
	 * @return
	 */
	@Override
	public Instance createFeatureVector(SensorCollection win, DodgeLabel label) {
		
		Instance inst = this.createFeatureVector(win);
		
		inst.setValue(this.dodgeLabel, label.toString());
		
		return inst;
		
	}
	
}

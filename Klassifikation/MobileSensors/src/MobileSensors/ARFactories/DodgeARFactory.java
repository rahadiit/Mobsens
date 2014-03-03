package MobileSensors.ARFactories;

import MobileSensors.Window;
import MobileSensors.Helpers.FeatureMathHelper;
import MobileSensors.SensorWindows.AccelerometerWindow;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class DodgeARFactory implements ARFactory {

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
	
	private final FastVector attributeRelationVector;
		
	public DodgeARFactory () {
		
		FastVector labels = new FastVector();
		labels.addElement(DodgeARFactory.LABEL_DODGE);
		labels.addElement(DodgeARFactory.LABEL_NODODGE);
		
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
		
		
		this.attributeRelationVector = new FastVector();
		this.attributeRelationVector.addElement(this.xArithMean);
		this.attributeRelationVector.addElement(this.yArithMean);
		this.attributeRelationVector.addElement(this.zArithMean);
		
		this.attributeRelationVector.addElement(this.xHarmMean);
		this.attributeRelationVector.addElement(this.yHarmMean);
		this.attributeRelationVector.addElement(this.zHarmMean);
		
		this.attributeRelationVector.addElement(this.xKurtosis);
		this.attributeRelationVector.addElement(this.yKurtosis);
		this.attributeRelationVector.addElement(this.zKurtosis);
		
		this.attributeRelationVector.addElement(this.xVariance);
		this.attributeRelationVector.addElement(this.yVariance);
		this.attributeRelationVector.addElement(this.zVariance);
		
		this.attributeRelationVector.addElement(this.dodgeLabel);
		
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
				this.attributeRelationVector,
				size);
		
		insts.setClass(this.dodgeLabel);
		
		return insts;
		
	}

	
	/**
	 * 
	 */
	@Override
	public Instance createFeatureVector(Window win, String label) {
		
		AccelerometerWindow accWin = new AccelerometerWindow(win.getAcceleration());
		
		Instance inst = new Instance(this.attributeRelationVector.size());
		
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
		
		inst.setClassValue(label);
		
		return inst;
		
	}
	
}

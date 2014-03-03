package MobileSensors.Deprecated;

import java.util.ArrayList;

import MobileSensors.Helpers.FeatureMathHelper;
import MobileSensors.Sensors.Accelerometer;

public class AccelerationFeatureVector {

	private double[] xs;
	private double[] ys;
	private double[] zs;
	private String tag;
	
	public AccelerationFeatureVector (ArrayList<Accelerometer> window, String tag) {
		
		this.xs = new double[window.size()];
		this.ys = new double[window.size()];
		this.zs = new double[window.size()];
		
		for (int i=0; i < window.size(); i++) {
			
			Accelerometer a = window.get(i);
			
			this.xs[i] = a.getX();
			this.ys[i] = a.getY();
			this.zs[i] = a.getZ();
			
		}
		
		this.tag    = tag;
		
	}
		
	public double getXArithMean () {
		
		return FeatureMathHelper.arithMean(this.xs);
		
	}
	
	public double getXGeomMean () {
		
		return FeatureMathHelper.geomMean(this.xs);
		
	}
	
	public double getXHarmMean () {
		
		return FeatureMathHelper.harmMean(this.xs);
		
	}
	
	public double getXVariance () {
		
		return FeatureMathHelper.variance(this.xs);
		
	}
	
	public double getXs2 () {
		
		return FeatureMathHelper.s2(this.xs);
		
	}
	
	public double getXKurtosis () {
		
		return FeatureMathHelper.kurtosis(this.xs);
		
	}
	
	public double getYArithMean () {
		
		return FeatureMathHelper.arithMean(this.ys);
		
	}
	
	public double getYGeomMean () {
		
		return FeatureMathHelper.geomMean(this.ys);
		
	}
	
	public double getYHarmMean () {
		
		return FeatureMathHelper.harmMean(this.ys);
		
	}
	
	public double getYVariance () {
		
		return FeatureMathHelper.variance(this.ys);
		
	}
	
	public double getYs2 () {
		
		return FeatureMathHelper.s2(this.ys);
		
	}
	
	public double getYKurtosis () {
		
		return FeatureMathHelper.kurtosis(this.ys);
		
	}
	
	public double getZArithMean () {
		
		return FeatureMathHelper.arithMean(this.zs);
		
	}
	
	public double getZGeomMean () {
		
		return FeatureMathHelper.geomMean(this.zs);
		
	}
	
	public double getZHarmMean () {
		
		return FeatureMathHelper.harmMean(this.zs);
		
	}
	
	public double getZVariance () {
		
		return FeatureMathHelper.variance(this.zs);
		
	}
	
	public double getZs2 () {
		
		return FeatureMathHelper.s2(this.zs);
		
	}
	
	public double getZKurtosis () {
		
		return FeatureMathHelper.kurtosis(this.zs);
		
	}
	
	
	
	
	
}

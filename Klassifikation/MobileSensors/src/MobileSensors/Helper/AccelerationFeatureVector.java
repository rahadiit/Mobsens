package MobileSensors.Helper;

import java.util.ArrayList;

import MobileSensors.Storage.Sensors.Accelerometer;

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
		
		return FeatureMath.arithMean(this.xs);
		
	}
	
	public double getXGeomMean () {
		
		return FeatureMath.geomMean(this.xs);
		
	}
	
	public double getXHarmMean () {
		
		return FeatureMath.harmMean(this.xs);
		
	}
	
	public double getXVariance () {
		
		return FeatureMath.variance(this.xs);
		
	}
	
	public double getXs2 () {
		
		return FeatureMath.s2(this.xs);
		
	}
	
	public double getXKurtosis () {
		
		return FeatureMath.kurtosis(this.xs);
		
	}
	
	public double getYArithMean () {
		
		return FeatureMath.arithMean(this.ys);
		
	}
	
	public double getYGeomMean () {
		
		return FeatureMath.geomMean(this.ys);
		
	}
	
	public double getYHarmMean () {
		
		return FeatureMath.harmMean(this.ys);
		
	}
	
	public double getYVariance () {
		
		return FeatureMath.variance(this.ys);
		
	}
	
	public double getYs2 () {
		
		return FeatureMath.s2(this.ys);
		
	}
	
	public double getYKurtosis () {
		
		return FeatureMath.kurtosis(this.ys);
		
	}
	
	public double getZArithMean () {
		
		return FeatureMath.arithMean(this.zs);
		
	}
	
	public double getZGeomMean () {
		
		return FeatureMath.geomMean(this.zs);
		
	}
	
	public double getZHarmMean () {
		
		return FeatureMath.harmMean(this.zs);
		
	}
	
	public double getZVariance () {
		
		return FeatureMath.variance(this.zs);
		
	}
	
	public double getZs2 () {
		
		return FeatureMath.s2(this.zs);
		
	}
	
	public double getZKurtosis () {
		
		return FeatureMath.kurtosis(this.zs);
		
	}
	
	
	
	
	
}

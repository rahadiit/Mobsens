package MobileSensors.Helper;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class FeatureMath {

	public static double sum (double[] xs) {
		
		DescriptiveStatistics ds = new DescriptiveStatistics();
		
		for (double x : xs) {
			
			ds.addValue(x);
			
		}
		
		return ds.getSum();
		
	}
	
	public static double arithMean (double[] xs) {
		
		DescriptiveStatistics ds = new DescriptiveStatistics();
		
		for (double x : xs) {
			
			ds.addValue(x);
			
		}
		
		return ds.getMean();
		
	}
	
	public static double geomMean (double[] xs) {
		
		DescriptiveStatistics ds = new DescriptiveStatistics();
		
		for (double x : xs) {
			
			ds.addValue(x);
			
		}
				
		return ds.getGeometricMean();
	}
	
	public static double harmMean (double[] xs) {
		
		double harm = 0;
		
		for (double x : xs) {
			
			harm += 1/x;
			
		}
		
		harm /= xs.length;
		
		return harm;
		
	}
	
	public static double variance (double[] xs) {
		
		DescriptiveStatistics ds = new DescriptiveStatistics();
		
		for (double x : xs) {
			
			ds.addValue(x);
			
		}
				
		return ds.getVariance();
		
	}
	
	public static double s2 (double[] xs) {
		
		return Math.sqrt(FeatureMath.variance(xs));
		
	}
	
	public static double kurtosis (double[] xs) {
		
		DescriptiveStatistics ds = new DescriptiveStatistics();
		
		for (double x : xs) {
			
			ds.addValue(x);
			
		}
				
		return ds.getKurtosis();
		
	}
	
	
}

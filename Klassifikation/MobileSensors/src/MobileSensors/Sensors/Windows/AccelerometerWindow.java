package MobileSensors.Sensors.Windows;

import java.util.ArrayList;
import MobileSensors.Sensors.Accelerometer;

/**
 * Utitlity class to access col vectors of an accelerometer matrix
 * 
 * @author henny, thomas, max
 *
 */
public class AccelerometerWindow extends SensorWindow<Accelerometer> {

	private double[] xs;
	private double[] ys;
	private double[] zs;
	
	public AccelerometerWindow (ArrayList<Accelerometer> accMatrixs) {
		
		this.xs = new double[accMatrixs.size()];
		this.ys = new double[accMatrixs.size()];
		this.zs = new double[accMatrixs.size()];
		
		for (int i=0; i < accMatrixs.size(); i++) {
			
			this.xs[i] = accMatrixs.get(i).getX();
			this.ys[i] = accMatrixs.get(i).getY();
			this.zs[i] = accMatrixs.get(i).getZ();
			
		}
		
	}

	public double[] getXs() {
		return xs;
	}

	public double[] getYs() {
		return ys;
	}

	public double[] getZs() {
		return zs;
	}
	
	
	
}

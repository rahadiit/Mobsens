package MobileSensors.SensorWindows;

import java.util.ArrayList;
import MobileSensors.Sensors.Accelerometer;

public class AccelerometerWindow extends SensorWindow<Accelerometer> {

	private double[] xs;
	private double[] ys;
	private double[] zs;
	
	public AccelerometerWindow (ArrayList<Accelerometer> accs) {
		
		this.xs = new double[accs.size()];
		this.ys = new double[accs.size()];
		this.zs = new double[accs.size()];
		
		for (int i=0; i < accs.size(); i++) {
			
			this.xs[i] = accs.get(i).getX();
			this.ys[i] = accs.get(i).getY();
			this.zs[i] = accs.get(i).getZ();
			
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

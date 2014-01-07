package MobileSensors.Storage.Sensors;

import MobileSensors.Storage.Sensors.Sensor.Sensor;

/**
 * 
 * @author henny, thomas, max
 * 
 * Accelerometer Data Model
 *
 */
public class Accelerometer extends Sensor {
	
	private double x,y,z;
	
	/**
	 * 
	 * @param time
	 * @param x
	 * @param y
	 * @param z
	 */
	public Accelerometer (long time,double x, double y, double z) {
		
		super(time);
		this.x=x;
		this.y=y;
		this.z=z;
		
	}

	/**
	 * 
	 * Gets x-coordinate
	 * 
	 * @return x-coordinate
	 */
	public double getX () {
		
		return x;
		
	}

	/**
	 * 
	 * Sets x-coordinate
	 * 
	 * @param x
	 */
	public void setX (double x) {
		
		this.x = x;
		
	}

	/**
	 * 
	 * Gets y-coordinate
	 * 
	 * @return y-coordinate
	 */
	public double getY () {
		
		return y;
		
	}

	/**
	 * 
	 * Sets y-coordinate
	 * 
	 * @param y
	 */
	public void setY (double y) {
		
		this.y = y;
		
	}

	/**
	 * 
	 * Gets z-coordinate
	 * 
	 * @return z-coordinate
	 */
	public double getZ () {
		
		return z;
		
	}

	/**
	 * 
	 * Gets z-coordinate
	 * 
	 * @param z
	 */
	public void setZ (double z) {
		
		this.z = z;
		
	}
	
}
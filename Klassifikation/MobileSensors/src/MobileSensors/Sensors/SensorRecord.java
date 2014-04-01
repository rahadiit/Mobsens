package MobileSensors.Sensors;

import java.util.ArrayList;

/**
 * 
 * Window Sensor Wrapper
 * 
 * @author henny, thomas, max
 * 
 */
public class SensorRecord {

	private ArrayList<Location> location;
	private ArrayList<Accelerometer> acceleration;
	
	public SensorRecord () {
		
		this.location = new ArrayList<>();
		this.acceleration = new ArrayList<>();
	} 
	
	public ArrayList<Location> getLocation() {
		return location;
	}

	public void setLocation(ArrayList<Location> location) {
		this.location = location;
	}

	public ArrayList<Accelerometer> getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(ArrayList<Accelerometer> acceleration) {
		this.acceleration = acceleration;
	}
	
}

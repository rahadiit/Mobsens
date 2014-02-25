package MobileSensors.Classifiers;

import java.util.ArrayList;

import MobileSensors.Storage.Sensors.Accelerometer;
import MobileSensors.Storage.Sensors.Location;

public class Window {

	private ArrayList<Location> location;
	private ArrayList<Accelerometer> acceleration;
	
	public Window () {
		
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

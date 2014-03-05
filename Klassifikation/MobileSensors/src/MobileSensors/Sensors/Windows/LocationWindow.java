package MobileSensors.Sensors.Windows;

import java.util.ArrayList;

import MobileSensors.Sensors.Location;

public class LocationWindow extends SensorWindow<Location> {

	private double[] latitudes;
	private double[] longitutdes;
	private double[] altitudes;
	private double[] speeds;
	private double[] bearings;
	private double[] accuracies;
	
	public LocationWindow (ArrayList<Location> locs) {
		
		this.latitudes = new double[locs.size()];
		this.longitutdes = new double[locs.size()];
		this.altitudes = new double[locs.size()];
		this.speeds = new double[locs.size()];
		this.bearings = new double[locs.size()];
		this.accuracies = new double[locs.size()];
		
		for (int i=0; i < locs.size(); i++) {
			
			Location loc = locs.get(i);
			
			this.latitudes[i] = loc.getLatitude();
			this.longitutdes[i] = loc.getLongitude();
			this.latitudes[i] = loc.getAltitude();
			this.speeds[i] = loc.getSpeed();
			this.bearings[i] = loc.getBearing();
			this.accuracies[i] = loc.getAccuracy();
			
		}
		
	}

	public double[] getLatitudes() {
		return latitudes;
	}

	public double[] getLongitutdes() {
		return longitutdes;
	}

	public double[] getAltitudes() {
		return altitudes;
	}

	public double[] getSpeeds() {
		return speeds;
	}

	public double[] getBearings() {
		return bearings;
	}

	public double[] getAccuracies() {
		return accuracies;
	}
	
	
	
}

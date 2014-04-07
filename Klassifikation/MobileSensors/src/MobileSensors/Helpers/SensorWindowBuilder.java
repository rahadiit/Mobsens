package MobileSensors.Helpers;

import java.util.ArrayList;
import java.util.HashMap;

import MobileSensors.Sensors.Accelerometer;
import MobileSensors.Sensors.Sensor;

public class SensorWindowBuilder<S extends Sensor> {

	
	private ArrayList<S> buildWindow (ArrayList<S> values, long sTime, long eTime) {
		
		ArrayList<S> window = new ArrayList<S>();
		
		for (S value : values) {
			
			if (value.getTime() < sTime) continue;
			
			if (sTime <= value.getTime() && value.getTime() <= eTime) {
				
				window.add(value);
				
			}
			
			if (eTime < value.getTime()) break;
			
		}
		
		return window;
		
	}
	
	public ArrayList<ArrayList<S>> buildWindows (ArrayList<S> values, long windowWidth) {
		
		ArrayList<ArrayList<S>> windows = new ArrayList<ArrayList<S>>();
		
		if (values.size() > 0) {
			
			long sTime = values.get(0).getTime();
			long eTime = values.get(values.size()-1).getTime();
			
			
			while (sTime < eTime) {
				
				windows.add(this.buildWindow(values, sTime, sTime + windowWidth));
				
				sTime += windowWidth/2;
				
			}
			
			
		}
		
		return windows;
		
	}
	
	public static void main (String[] args) {
		
		ArrayList<Accelerometer> values = new ArrayList<Accelerometer>();
		
		for (int i=1; i < 13; i++) {
			
			values.add(new Accelerometer(i,0,0,0));
			
		}
		
		System.out.println(values);
		
		ArrayList<ArrayList<Accelerometer>> wins = (new SensorWindowBuilder<Accelerometer>()).buildWindows(values, 2);
		
		for (ArrayList<Accelerometer> win : wins) {
			
			System.out.println(win);
			
		}
		
	}
	
}

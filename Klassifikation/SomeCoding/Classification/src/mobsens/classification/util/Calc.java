package mobsens.classification.util;

import java.util.ArrayList;

import mobsens.classification.data.Location;

public class Calc {
	
	public static double getAverageSpeed(long time, double distance){
		double hours = ((double)time/1000)/60/60;
		return distance/hours;
	}
	
	public static double getAverageSpeed(ArrayList<Location> locations){
		return getAverageSpeed(locations,0,locations.size()-1);
	}
	
	public static double getAverageSpeed(ArrayList<Location> locations, int from, int to){
		long time1=(long)locations.get(from).getTime();
		long time2=(long)locations.get(to).getTime();
		return getAverageSpeed(time2-time1, GPS.distanceKMSumLoc(locations,from,to));
	}
	
	public static ArrayList<Integer> indicesBySteps(ArrayList<Location> locations, int stepsInMeters){
		ArrayList<Integer> result = new ArrayList<>();
		
		int i=0;
		while(i<locations.size()-1){
			int sum=0;
			
			while(sum<stepsInMeters && i<locations.size()-1){
				sum+=GPS.distanceKM(locations.get(i),locations.get(i+1))*1000;
				i++;
				
			}
			result.add(i);		
		}
		
		return result;
	}
	
}

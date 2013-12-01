package mobsens.classification;

import java.io.File;
import java.util.ArrayList;

import mobsens.classification.data.Location;
import mobsens.classification.util.CSV;
import mobsens.classification.util.GPS;
import mobsens.classification.util.IO;
import mobsens.classification.util.Time;
import mobsens.classification.util.Calc;

public class Main {
	
	public static void main (String[] args){
		
		ArrayList<Location> locations = CSV.csvToLocation(new File("lo1.csv"));
		
		long time1=(long)locations.get(0).getTime();
		long time2=(long)locations.get(locations.size()-1).getTime();
		System.out.println("locations: "+locations.size());
		System.out.println("Distance: "+GPS.distanceKMSumLoc(locations));
		System.out.println("duration "+Time.duration(time1,time2, "HH:mm:ss"));
		System.out.println("avg speed: "+Calc.getAverageSpeed((time1-time2), GPS.distanceKMSumLoc(locations)));
	}
}

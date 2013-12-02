package mobsens.classification.output;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import mobsens.classification.data.Location;
import mobsens.classification.input.CSV;
import mobsens.classification.util.Calc;
import mobsens.classification.util.GPS;
import mobsens.classification.util.Time;

public class PrettyPrint {
	
	public static void print(ArrayList<Location> locations){
		
		System.out.println("overall stats");
		
		long time1=(long)locations.get(0).getTime();
		long time2=(long)locations.get(locations.size()-1).getTime();
		System.out.println("locations: "+locations.size());
		System.out.println("Distance: "+GPS.distanceKMSumLoc(locations));
		System.out.println("duration "+Time.duration(locations.get(0),locations.get(locations.size()-1), "HH:mm:ss"));
		System.out.println("avg speed: "+Calc.getAverageSpeed(locations));
		System.out.println("start: "+ DateFormatUtils.format(new Date(time1), "HH:mm:ss"));
		System.out.println("stop: "+ DateFormatUtils.format(new Date(time2), "HH:mm:ss"));
		
		
		int steps=2000;
		System.out.println("");
		System.out.println("stats every "+steps+". meters");
		
		int start=0;
		for(int step:Calc.indicesBySteps(locations, steps)){
			System.out.println("");
			System.out.println("Distance: "+GPS.distanceKMSumLoc(locations,start,step));
			System.out.println("duration "+Time.duration(locations.get(start),locations.get(step), "HH:mm:ss"));
			System.out.println("avg speed: "+Calc.getAverageSpeed(locations,start,step));
			
			start=step+1;
		}
		
	}
	
	

}

package mobsens.classification;

import java.io.File;
import java.util.ArrayList;

import mobsens.classification.data.Location;
import mobsens.classification.util.CSV;
import mobsens.classification.util.GPS;
import mobsens.classification.util.IO;

public class Main {
	
	public static void main (String[] args){
		
		ArrayList<double[]> coordinates = new ArrayList<>();
		ArrayList<Location> locations = CSV.csvToLocation(new File("lo1.csv"));
		for(Location location:locations){
			coordinates.add(location.getCoordinates());
		}
		
		System.out.println("Distance: "+GPS.distanceKMSum(coordinates));
		
	}
}

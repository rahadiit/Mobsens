package mobsens.classification.util;

public class Calc {
	
	public static double getAverageSpeed(long time, double distance){
		System.out.println(time);
		double hours = ((double)time/1000)/60/60;
		System.out.println(hours);
		return distance/hours;
	}
	
	
}

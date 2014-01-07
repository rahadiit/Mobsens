package MobileSensors.Calculation;

import MobileSensors.Storage.Sensors.Location;

/**
 * 
 * @author henny, thomas, max
 *
 */
public class GPS {
	
	/**
	 * Computes distance between two geo-locations due to Haversine.
	 * 
	 * 
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return Distance between tow geo-locations
	 */
	public static double distance(double lat1, double lng1, double lat2,
			double lng2) {
		
		double rlat1=Math.toRadians(lat1);
		double rlat2=Math.toRadians(lat2);
		double rlng1=Math.toRadians(lng1);
		double rlng2=Math.toRadians(lng2);
		
		
		// average radius at a given latitude in m
		double r = 6372*1000 * Math.cos(rlat1); //WARNING!: this may be inaccurate, use simple radius (6372*1000) instead
		double dLat = rlat2-rlat1;
		double dLng = rlng2-rlng1;
		double a = Math.sin(dLat/2)*Math.sin(dLat/2);
		double b = Math.sin(dLng/2)* Math.sin(dLng/2);
		//double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		
		double d = 2*r*Math.sin(Math.sqrt(a+Math.cos(rlat1)*Math.cos(rlat2)*b));
		return d;
	}
	
	/**
	 * Computes distance between two geo-locations due to Haversine.
	 * 
	 * @param co1
	 * @param co2
	 * @return Distance between tow geo-locations
	 */
	public static double distance(double[] co1, double[] co2) {
		return (distance(co1[0], co1[1], co2[0], co2[1]));
	}
	
	/**
	 * Computes distance between two geo-locations due to Haversine.
	 * 
	 * @param loc1
	 * @param loc2
	 * @return Distance between tow geo-locations
	 */
	public static double distance(Location loc1, Location loc2) {
		return (distance(loc1.getCoordinates(), loc2.getCoordinates()));
	}
	
	/**
	 * Computes first order derivative of distance
	 * 
	 * @param distance
	 * @param time
	 * @return
	 */
	public static double speed(double distance, double time){
		if(time!=0)
			return distance/time;
		return 0;
	}
	
	/**
	 * Computes second order derivative of distance
	 * 
	 * @param prevSpeed
	 * @param speed
	 * @param time
	 * @return
	 */
	public static double acceleration(double prevSpeed, double speed, double time){
		if(time!=0)
			return (speed-prevSpeed)/time;
		return 0;
	}
	
	/**
	 * Computes third order drivative of distance
	 * 
	 * @param prevSpeed
	 * @param speed
	 * @param time
	 * @return
	 */
	public static double jerk(double prevSpeed, double speed, double time){
		if(time!=0)
			return (speed-prevSpeed)/time;
		return 0;
	}

	
	/*
	public static double dist2(double lat1, double lng1, double lat2, double lng2){
		
		double R=6371;
		double dLat = Math.toRadians(lat2-lat1);
		double dLng = Math.toRadians(lng2-lng1);
		lat1=Math.toRadians(lat1);
		lat2=Math.toRadians(lat2);
		double a = Math.sin(dLat/2) * Math.sin(dLat/2)+
					Math.sin(dLng/2) * Math.sin(dLng/2)
					*Math.cos(lat1)*Math.cos(lat2);
		double c = 2* Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
		
		double d = R*c;
		return d;
	}
	*/
}

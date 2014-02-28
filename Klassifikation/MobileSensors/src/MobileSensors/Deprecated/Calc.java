package MobileSensors.Deprecated;

public class Calc {


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
	 * @param prevAccel
	 * @param accel
	 * @param time
	 * @return
	 */
	public static double jerk(double prevAccel, double accel, double time){
		if(time!=0)
			return (accel-prevAccel)/time;
		return 0;
	}
	
}

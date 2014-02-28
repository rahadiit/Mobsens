package MobileSensors.Deprecated;

import java.util.Collection;

/**
 * 
 * @author henny, thomas, max
 *
 */
public class LocationCalc {

	/**
	 * 
	 * @param locations
	 */
	public static void locationCalc(Collection<Location> locations) {
		Location prevLocation = null;

		for (Location location : locations) {
			if (prevLocation != null) {				
				location.setTimeDifference(prevLocation);

				setDistance(prevLocation, location);
				setDistanceSum(prevLocation, location);

				setSpeed(location);
				setAcceleration(prevLocation, location);
				setJerk(prevLocation, location);
			}
			prevLocation = location;
		}
	}



	/**
	 * 
	 * @param prevLoc
	 * @param loc
	 */
	private static void setDistance(Location prevLoc, Location loc) {
		double distCo = GPS.distance(prevLoc, loc);
		double distGs = loc.getSpeed() * loc.getTimeDifference();
		
		//falls Stillstand gemessen durch doppler-effekt
		if (loc.getSpeed() <= 0.3) {
			distCo = 0;
		} else {
			//10% abziehen aufgrund von messfehlern
			distCo*= 0.9;
		}
		

		loc.setDistanceCalcCo(distCo);
		loc.setDistanceCalcGs(distGs);

		loc.setDistanceFusion((distCo + distGs) / 2);
	}

	/**
	 * 
	 * @param prevLoc
	 * @param loc
	 */
	private static void setDistanceSum(Location prevLoc, Location loc) {
		loc.setDistanceSumCalcCo(prevLoc.getDistanceSumCalcCo()
				+ loc.getDistanceCalcCo());

		loc.setDistanceSumCalcGs(prevLoc.getDistanceSumCalcGs()
				+ loc.getDistanceCalcGs());

		loc.setDistanceFusionSum(prevLoc.getDistanceFusionSum()
				+ loc.getDistanceFusion());
	}

	/**
	 * 
	 * @param loc
	 */
	private static void setSpeed(Location loc) {
		double speedCo = Calc.speed(loc.getDistanceCalcCo(), loc.getTimeDifference());

		loc.setSpeedCalcCo(speedCo);

		loc.setSpeedFusion((speedCo + loc.getSpeed()) / 2);
	}

	/**
	 * 
	 * @param prevLoc
	 * @param loc
	 */
	private static void setAcceleration(Location prevLoc, Location loc) {
		double accel = Calc.acceleration(prevLoc.getSpeed(), loc.getSpeed(), loc.getTimeDifference());
		loc.setAcceleration(accel);
		
		double accelCalc = Calc.acceleration(prevLoc.getSpeedCalcCo(),
				loc.getSpeedCalcCo(), loc.getTimeDifference());
		loc.setAccelerationCalc(accelCalc);

		double accelFusion = Calc.acceleration(prevLoc.getSpeedFusion(),
				loc.getSpeedFusion(), loc.getTimeDifference());
		loc.setAccelerationFusion(accelFusion);
	}

	/**
	 * 
	 * @param prevLoc
	 * @param loc
	 */
	private static void setJerk(Location prevLoc, Location loc) {
		double jerk = Calc.jerk(prevLoc.getAcceleration(), loc.getAcceleration(), loc.getTimeDifference());
		loc.setJerk(jerk);
		
		double jerkCalc=Calc.jerk(prevLoc.getAccelerationCalc(),
				loc.getAccelerationCalc(), loc.getTimeDifference());
		loc.setJerkCalc(jerkCalc);
		
		double jerkFusion=Calc.jerk(prevLoc.getAccelerationFusion(),
				loc.getAccelerationFusion(), loc.getTimeDifference());
		loc.setJerkFusion(jerkFusion);
		
	}

}

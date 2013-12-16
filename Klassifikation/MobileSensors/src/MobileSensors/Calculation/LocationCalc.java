package MobileSensors.Calculation;

import java.util.Collection;

import MobileSensors.Storage.Sensors.Location;

public class LocationCalc {

	public static void locationCalc(Collection<Location> locations) {
		Location prevLocation = null;

		for (Location location : locations) {
			if (prevLocation != null) {
				setTime(prevLocation, location);

				setDistance(prevLocation, location);
				setDistanceSum(prevLocation, location);

				setSpeed(location);
				setAcceleration(prevLocation, location);
				setJerk(prevLocation, location);
			}
			prevLocation = location;
		}
	}

	private static void setTime(Location prevLoc, Location loc) {

		long time = loc.getTime() - prevLoc.getTime();
		time = Math.abs(time);
		time /= 1000; // ms in s
		loc.setTimeCalc(time);
	}

	private static void setDistance(Location prevLoc, Location loc) {
		double distCo = GPS.distance(prevLoc, loc);
		double distGs = loc.getSpeed() * loc.getTimeCalc();

		loc.setDistanceCalcCo(distCo);
		loc.setDistanceCalcGs(distGs);

		loc.setDistanceFusion((distCo + distGs) / 2);
	}

	private static void setDistanceSum(Location prevLoc, Location loc) {
		loc.setDistanceSumCalcCo(prevLoc.getDistanceSumCalcCo()
				+ loc.getDistanceCalcCo());

		loc.setDistanceSumCalcGs(prevLoc.getDistanceSumCalcGs()
				+ loc.getDistanceCalcGs());

		loc.setDistanceFusionSum(prevLoc.getDistanceFusionSum()
				+ loc.getDistanceFusion());
	}

	private static void setSpeed(Location loc) {
		double speedCo = GPS.speed(loc.getDistanceCalcCo(), loc.getTimeCalc());

		loc.setSpeedCalcCo(speedCo);

		loc.setSpeedFusion((speedCo + loc.getSpeed()) / 2);
	}

	private static void setAcceleration(Location prevLoc, Location loc) {
		double accelCalc = GPS.acceleration(prevLoc.getSpeedCalcCo(),
				loc.getSpeedCalcCo(), loc.getTimeCalc());
		loc.setAccelerationCalc(accelCalc);

		double accelFusion = GPS.acceleration(prevLoc.getSpeedFusion(),
				loc.getSpeedFusion(), loc.getTimeCalc());
		loc.setAccelerationFusion(accelFusion);
	}

	private static void setJerk(Location prevLoc, Location loc) {
		double jerkCalc=GPS.jerk(prevLoc.getAccelerationCalc(),
				loc.getAccelerationCalc(), loc.getTimeCalc());
		loc.setJerkCalc(jerkCalc);
		
		double jerkFusion=GPS.jerk(prevLoc.getAccelerationFusion(),
				loc.getAccelerationFusion(), loc.getTimeCalc());
		loc.setJerkFusion(jerkFusion);
		
	}

}

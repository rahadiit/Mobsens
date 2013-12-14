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

				setDistanceCalc2(location);
				setDistanceSumCalc2(prevLocation, location);

				setSpeed(location);
				setAcceleration(prevLocation, location);
				setJerk(prevLocation, location);
			}
			prevLocation = location;
		}
	}

	private static void setTime(Location prevLoc, Location loc) {
		long time = loc.getTime() - prevLoc.getTime();
		time /= 1000; // ms in s
		loc.setTimeCalc(time);
	}

	private static void setDistance(Location prevLoc, Location loc) {
		loc.setDistanceCalc(GPS.distance(prevLoc, loc));
	}

	private static void setDistanceSum(Location prevLoc, Location loc) {
		loc.setDistanceSumCalc(prevLoc.getDistanceSumCalc()
				+ loc.getDistanceCalc());
	}

	private static void setSpeed(Location loc) {
		loc.setSpeedCalc(GPS.speed(loc.getDistanceCalc(), loc.getTimeCalc()));
	}

	private static void setAcceleration(Location prevLoc, Location loc) {
		loc.setAccelerationCalc(GPS.acceleration(prevLoc.getSpeedCalc(),
				loc.getSpeed(), loc.getTimeCalc()));
	}

	private static void setJerk(Location prevLoc, Location loc) {
		loc.setJerkCalc(GPS.jerk(prevLoc.getSpeedCalc(), loc.getSpeedCalc(),
				loc.getTimeCalc()));
	}

	private static void setDistanceCalc2(Location loc) {
		loc.setDistanceCalc2(loc.getSpeed() * loc.getTimeCalc());
	}

	private static void setDistanceSumCalc2(Location prevLoc, Location loc) {
		loc.setDistanceSumCalc2(prevLoc.getDistanceSumCalc2()
				+ loc.getDistanceCalc2());
	}

}

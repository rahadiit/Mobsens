package mobsens.classification.util;

import java.util.ArrayList;

import mobsens.classification.data.Location;

public class GPS {

	/**
	 * Calculates the distance in km between two lat/long points using the
	 * haversine formula source:
	 * http://stackoverflow.com/questions/18861728/calculating
	 * -distance-between-two-points-represented-by-lat-long-upto-15-feet-acc
	 */
	public static double distanceKM(double lat1, double lng1, double lat2,
			double lng2) {
		double r = 6367; // average radius of the earth in km
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lng2 - lng1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
				* Math.sin(dLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double d = r * c;
		return d;
	}

	public static double distanceKM(double[] co1, double[] co2) {
		return (distanceKM(co1[0], co1[1], co2[0], co2[1]));
	}

	public static double distanceKM(Location loc1, Location loc2) {
		return (distanceKM(loc1.getCoordinates(), loc2.getCoordinates()));
	}

	public static double distanceKMSum(ArrayList<Double> lat1,
			ArrayList<Double> lng1, ArrayList<Double> lat2,
			ArrayList<Double> lng2) {
		double sum = 0;
		// all same size
		if (lat1.size() == lng1.size() && lat1.size() == lat2.size()
				&& lat1.size() == lng2.size()) {
			for (int i = 0; i < lat1.size(); i++) {
				sum += distanceKM(lat1.get(i), lng1.get(i), lat2.get(i),
						lng2.get(i));
			}
		}

		return sum;
	}

	public static double distanceKMSum(ArrayList<double[]> coordinates) {
		double sum = 0;

		for (int i = 0; i < coordinates.size()-30; i+=30) {
			sum += distanceKM(coordinates.get(i), coordinates.get(i + 30));
		}

		return sum;
	}

	public static double distanceKMSumLoc(ArrayList<Location> locations,
			int from, int to) {
		ArrayList<double[]> coordinates = new ArrayList<>();
		from = from < 0 ? 0 : from;

		for (int i = from; i < locations.size() && i < to; i++) {
			coordinates.add(locations.get(i).getCoordinates());
		}
		return GPS.distanceKMSum(coordinates);
	}

	public static double distanceKMSumLoc(ArrayList<Location> locations) {
		return distanceKMSumLoc(locations, 0, locations.size());
	}

}

package mobsens.collector.util;

public class Calculations
{
	public static final int msFromFrequency(double frequency)
	{
		if (frequency == 0.0) return Integer.MAX_VALUE;

		return (int) Math.round(1000.0 / frequency);
	}

	public static final double EARTH_RADIUS = 6371000.785;

	public static final double haversineDistance(double lon1, double lat1, double lon2, double lat2)
	{
		final double dLat = Math.toRadians(lat2 - lat1);
		final double dLon = Math.toRadians(lon2 - lon1);
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		final double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
		final double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return EARTH_RADIUS * c;
	}

}

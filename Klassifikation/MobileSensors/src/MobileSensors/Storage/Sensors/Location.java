package MobileSensors.Storage.Sensors;

import MobileSensors.Storage.Sensors.Sensor.Sensor;

public class Location extends Sensor {

	private double latitude;
	private double longitude;
	private double altitude;
	private double speed;
	private double bearing;
	private double accuracy;
	
	private double distanceCalc;
	private double distanceSumCalc;
	
	private double distanceCalc2;
	private double distanceSumCalc2;
	
	private double speedCalc;
	private double accelerationCalc;
	private double jerkCalc;
	private long timeCalc;

	public Location(long time, double latitude, double longitude,
			double altitude, double speed, double bearing, double accuracy) {
		super(time);
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
		this.speed = speed;
		this.bearing = bearing;
		this.accuracy = accuracy;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getBearing() {
		return bearing;
	}

	public void setBearing(double bearing) {
		this.bearing = bearing;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	/*
	 * @return latitute, longitude
	 */
	public double[] getCoordinates() {
		double[] result = { latitude, longitude };
		return result;
	}

	public double getSpeedCalc() {
		return speedCalc;
	}

	public void setSpeedCalc(double speedCalc) {
		this.speedCalc = speedCalc;
	}

	public double getAccelerationCalc() {
		return accelerationCalc;
	}

	public void setAccelerationCalc(double accelerationCalc) {
		this.accelerationCalc = accelerationCalc;
	}

	public long getTimeCalc() {
		return timeCalc;
	}

	public void setTimeCalc(long timeCalc) {
		this.timeCalc = timeCalc;
	}

	public double getDistanceCalc() {
		return distanceCalc;
	}

	public void setDistanceCalc(double distanceCalc) {
		this.distanceCalc = distanceCalc;
	}

	public double getJerkCalc() {
		return jerkCalc;
	}

	public void setJerkCalc(double jerkCalc) {
		this.jerkCalc = jerkCalc;
	}

	public double getDistanceSumCalc() {
		return distanceSumCalc;
	}

	public void setDistanceSumCalc(double distanceSumCalc) {
		this.distanceSumCalc = distanceSumCalc;
	}

	public double getDistanceCalc2() {
		return distanceCalc2;
	}

	public void setDistanceCalc2(double distanceCalc2) {
		this.distanceCalc2 = distanceCalc2;
	}

	public double getDistanceSumCalc2() {
		return distanceSumCalc2;
	}

	public void setDistanceSumCalc2(double distanceSumCalc2) {
		this.distanceSumCalc2 = distanceSumCalc2;
	}
}
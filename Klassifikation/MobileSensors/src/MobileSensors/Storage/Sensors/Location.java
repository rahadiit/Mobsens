package MobileSensors.Storage.Sensors;

import MobileSensors.Storage.Sensors.Sensor.Sensor;

public class Location extends Sensor {

	private double latitude;
	private double longitude;
	private double altitude;
	private double speed;
	private double bearing;
	private double accuracy;
	
	private double acceleration;
	private double jerk;
	
	private double distanceCalcCo;
	private double distanceSumCalcCo;
	
	private double distanceCalcGs;
	private double distanceSumCalcGs;
	
	private double speedCalcCo;
	
	private double accelerationCalc;
	private double jerkCalc;
	
	private double distanceFusion;
	private double distanceFusionSum;
	private double speedFusion;
	private double accelerationFusion;
	private double jerkFusion;
	
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
	
	public double getAccelerationCalc() {
		return accelerationCalc;
	}

	public void setAccelerationCalc(double accelerationCalc) {
		this.accelerationCalc = accelerationCalc;
	}

	public double getDistanceCalcCo() {
		return distanceCalcCo;
	}

	public void setDistanceCalcCo(double distanceCalcCo) {
		this.distanceCalcCo = distanceCalcCo;
	}
	

	public double getDistanceSumCalcCo() {
		return distanceSumCalcCo;
	}

	public void setDistanceSumCalcCo(double distanceSumCalcCo) {
		this.distanceSumCalcCo = distanceSumCalcCo;
	}

	public double getDistanceCalcGs() {
		return distanceCalcGs;
	}

	public void setDistanceCalcGs(double distanceCalcGs) {
		this.distanceCalcGs = distanceCalcGs;
	}

	public double getDistanceSumCalcGs() {
		return distanceSumCalcGs;
	}

	public void setDistanceSumCalcGs(double distanceSumCalcGs) {
		this.distanceSumCalcGs = distanceSumCalcGs;
	}

	public double getSpeedCalcCo() {
		return speedCalcCo;
	}

	public void setSpeedCalcCo(double speedCalcCo) {
		this.speedCalcCo = speedCalcCo;
	}

	public double getDistanceFusion() {
		return distanceFusion;
	}

	public void setDistanceFusion(double distanceFusion) {
		this.distanceFusion = distanceFusion;
	}

	public double getDistanceFusionSum() {
		return distanceFusionSum;
	}

	public void setDistanceFusionSum(double distanceFusionSum) {
		this.distanceFusionSum = distanceFusionSum;
	}

	public double getSpeedFusion() {
		return speedFusion;
	}

	public void setSpeedFusion(double speedFusion) {
		this.speedFusion = speedFusion;
	}

	public double getJerkCalc() {
		return jerkCalc;
	}

	public void setJerkCalc(double jerkCalc) {
		this.jerkCalc = jerkCalc;
	}

	public long getTimeCalc() {
		return timeCalc;
	}

	public void setTimeCalc(long timeCalc) {
		this.timeCalc = timeCalc;
	}

	public double getAccelerationFusion() {
		return accelerationFusion;
	}

	public void setAccelerationFusion(double accelerationFusion) {
		this.accelerationFusion = accelerationFusion;
	}

	public double getJerkFusion() {
		return jerkFusion;
	}

	public void setJerkFusion(double jerkFusion) {
		this.jerkFusion = jerkFusion;
	}

	public double getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}

	public double getJerk() {
		return jerk;
	}

	public void setJerk(double jerk) {
		this.jerk = jerk;
	}

}
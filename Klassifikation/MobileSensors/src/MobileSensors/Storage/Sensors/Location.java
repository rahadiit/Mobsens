package MobileSensors.Storage.Sensors;

import MobileSensors.Storage.Sensors.Sensor.Sensor;

/**
 * 
 * @author henny, thomas, max
 * 
 *         Location Data Model
 * 
 */
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

	/**
	 * 
	 * @param time
	 * @param latitude
	 * @param longitude
	 * @param altitude
	 * @param speed
	 * @param bearing
	 * @param accuracy
	 */
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

	/**
	 * 
	 * @return
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * 
	 * @param latitude
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * 
	 * @return
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * 
	 * @param longitude
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * 
	 * @return
	 */
	public double getAltitude() {
		return altitude;
	}

	/**
	 * 
	 * @param altitude
	 */
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	/**
	 * 
	 * @return
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * 
	 * @param speed
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	/**
	 * 
	 * @return
	 */
	public double getBearing() {
		return bearing;
	}

	/**
	 * 
	 * @param bearing
	 */
	public void setBearing(double bearing) {
		this.bearing = bearing;
	}

	/**
	 * 
	 * @return
	 */
	public double getAccuracy() {
		return accuracy;
	}

	/**
	 * 
	 * @param accuracy
	 */
	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	/**
	 * 
	 * @return
	 */
	public double[] getCoordinates() {
		double[] result = { latitude, longitude };
		return result;
	}

	/**
	 * 
	 * @return
	 */
	public double getAccelerationCalc() {
		return accelerationCalc;
	}

	/**
	 * 
	 * @param accelerationCalc
	 */
	public void setAccelerationCalc(double accelerationCalc) {
		this.accelerationCalc = accelerationCalc;
	}

	/**
	 * 
	 * @return
	 */
	public double getDistanceCalcCo() {
		return distanceCalcCo;
	}

	/**
	 * 
	 * @param distanceCalcCo
	 */
	public void setDistanceCalcCo(double distanceCalcCo) {
		this.distanceCalcCo=distanceCalcCo;
	}

	/**
	 * 
	 * @return
	 */
	public double getDistanceSumCalcCo() {
		return distanceSumCalcCo;
	}

	/**
	 * 
	 * @param distanceSumCalcCo
	 */
	public void setDistanceSumCalcCo(double distanceSumCalcCo) {
		this.distanceSumCalcCo = distanceSumCalcCo;
	}

	/**
	 * 
	 * @return
	 */
	public double getDistanceCalcGs() {
		return distanceCalcGs;
	}

	/**
	 * 
	 * @param distanceCalcGs
	 */
	public void setDistanceCalcGs(double distanceCalcGs) {
		this.distanceCalcGs = distanceCalcGs;
	}

	/**
	 * 
	 * @return
	 */
	public double getDistanceSumCalcGs() {
		return distanceSumCalcGs;
	}

	/**
	 * 
	 * @param distanceSumCalcGs
	 */
	public void setDistanceSumCalcGs(double distanceSumCalcGs) {
		this.distanceSumCalcGs = distanceSumCalcGs;
	}

	/**
	 * 
	 * @return
	 */
	public double getSpeedCalcCo() {
		return speedCalcCo;
	}

	/**
	 * 
	 * @param speedCalcCo
	 */
	public void setSpeedCalcCo(double speedCalcCo) {
		this.speedCalcCo = speedCalcCo;
	}

	/**
	 * 
	 * @return
	 */
	public double getDistanceFusion() {
		return distanceFusion;
	}

	/**
	 * 
	 * @param distanceFusion
	 */
	public void setDistanceFusion(double distanceFusion) {
		this.distanceFusion = distanceFusion;
	}

	/**
	 * 
	 * @return
	 */
	public double getDistanceFusionSum() {
		return distanceFusionSum;
	}

	/**
	 * 
	 * @param distanceFusionSum
	 */
	public void setDistanceFusionSum(double distanceFusionSum) {
		this.distanceFusionSum = distanceFusionSum;
	}

	/**
	 * 
	 * @return
	 */
	public double getSpeedFusion() {
		return speedFusion;
	}

	/**
	 * 
	 * @param speedFusion
	 */
	public void setSpeedFusion(double speedFusion) {
		this.speedFusion = speedFusion;
	}

	/**
	 * 
	 * @return
	 */
	public double getJerkCalc() {
		return jerkCalc;
	}

	/**
	 * 
	 * @param jerkCalc
	 */
	public void setJerkCalc(double jerkCalc) {
		this.jerkCalc = jerkCalc;
	}

	/**
	 * 
	 * @return
	 */
	public long getTimeCalc() {
		return timeCalc;
	}

	/**
	 * 
	 * @param timeCalc
	 */
	public void setTimeCalc(long timeCalc) {
		this.timeCalc = timeCalc;
	}

	/**
	 * 
	 * @return
	 */
	public double getAccelerationFusion() {
		return accelerationFusion;
	}

	/**
	 * 
	 * @param accelerationFusion
	 */
	public void setAccelerationFusion(double accelerationFusion) {
		this.accelerationFusion = accelerationFusion;
	}

	/**
	 * 
	 * @return
	 */
	public double getJerkFusion() {
		return jerkFusion;
	}

	/**
	 * 
	 * @param jerkFusion
	 */
	public void setJerkFusion(double jerkFusion) {
		this.jerkFusion = jerkFusion;
	}

	/**
	 * 
	 * @return
	 */
	public double getAcceleration() {
		return acceleration;
	}

	/**
	 * 
	 * @param acceleration
	 */
	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}

	/**
	 * 
	 * @return
	 */
	public double getJerk() {
		return jerk;
	}

	/**
	 * 
	 * @param jerk
	 */
	public void setJerk(double jerk) {
		this.jerk = jerk;
	}

}
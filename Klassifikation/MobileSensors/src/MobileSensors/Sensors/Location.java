package MobileSensors.Sensors;

public class Location extends Sensor {

	public final static int COLINDEX_LATITUDE = 0;
	public final static int COLINDEX_LONGITUDE = 1;
	public final static int COLINDEX_ALTIDUE = 2;
	public final static int COLINDEX_SPEED = 3;
	public final static int COLINDEX_BEARING = 4;
	public final static int COLINDEX_ACCURANCY = 5;
	
	private double latitude;
	private double longitude;
	private double altitude;
	private double speed;
	private double bearing;
	private double accuracy;
	
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
	

}

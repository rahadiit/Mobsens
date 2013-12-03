package mobsens.classification.data;

public class Location {
	
	private double time;
	private double lat;
	private double lng;
	private double alt;
	private double speed;
	private double bearing;
	private double accuracy;
	
	public Location(double time, double lat, double lng, double alt, double speed,
			double bearing, double accuracy) {
		this.time = time;
		this.lat = lat;
		this.lng = lng;
		this.alt = alt;
		this.speed = speed;
		this.bearing = bearing;
		this.accuracy = accuracy;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}
	
	public double getAlt() {
		return alt;
	}

	public void setAlt(double alt) {
		this.alt = alt;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
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
	
	public double[] getCoordinates(){
		double[] result = {lat,lng};
		return result;
	}
}

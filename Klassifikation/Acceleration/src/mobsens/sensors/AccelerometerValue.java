package mobsens.sensors;

import mobsens.physics.Acceleration;

public class AccelerometerValue extends SensorValue {

	private double x;
	private double y;
	private double z;
	
	public AccelerometerValue(long time, double x, double y, double z) {
		super(time);
		
		this.x = x;
		this.y = y;
		this.z = z;
		
	}
	
	public Acceleration getXAcceleration () {
		
		return new Acceleration(this.getTime(), this.x);
		
	}

	public Acceleration getYAcceleration () {
		
		return new Acceleration(this.getTime(), this.y);
		
	}

	public Acceleration getZAcceleration () {
		
		return new Acceleration(this.getTime(), this.z);
		
	}
	
}

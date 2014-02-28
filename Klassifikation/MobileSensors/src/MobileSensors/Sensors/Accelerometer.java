package MobileSensors.Sensors;


public class Accelerometer
	extends Sensor implements Comparable {

	private double x,y,z;
	
	public Accelerometer(long time, double x, double y, double z) {
		super(time);
		
		this.x = x;
		this.y = y;
		this.z = z;
		
		
	}


	public double getX() {
		return x;
	}



	public void setX(double x) {
		this.x = x;
	}



	public double getY() {
		return y;
	}



	public void setY(double y) {
		this.y = y;
	}



	public double getZ() {
		return z;
	}



	public void setZ(double z) {
		this.z = z;
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}

package mobsens.physics;

public class PhysicsValue {
	
	private long time;
	private double value;
	
	public PhysicsValue (long time, double value) {
		
		this.time  = time;
		this.value = value;
		
	}
	
	public long getTime () {
		
		return this.time;
		
	}
	
	public double getValue () {
		
		return this.value;
		
	}
	
}

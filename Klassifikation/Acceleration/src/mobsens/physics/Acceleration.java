package mobsens.physics;


public class Acceleration extends PhysicsValue {

	public Acceleration(long time, double value) {
		
		super(time, value);
		
	}
	
	public Jerk jerk (Acceleration a) {
		
		long   time  = a.getTime();
		double value = (this.getValue() - a.getValue()) / (this.getTime() - a.getTime());
		
		return new Jerk(time, value);
		
	}

	
	
}

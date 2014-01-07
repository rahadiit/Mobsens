package MobileSensors.Storage.Sensors.Sensor;

/**
 * 
 * Smartphone Seonsor Abstraction
 * 
 * @author henny, thomas, max
 *
 */
public abstract class Sensor {

	private long time;
	
	/**
	 * 
	 * @param time
	 */
	public Sensor(long time){
		this.time=time;
	}

	/**
	 * 
	 * @param time
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * 
	 * @return
	 */
	public long getTime() {
		return this.time;
	}

}

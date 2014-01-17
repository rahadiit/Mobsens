package MobileSensors.Storage.Sensors.Sensor;

/**
 * 
 * Smartphone Seonsor Abstraction
 * 
 * @author henny, thomas, max
 *
 */
public abstract class Sensor extends Timeable{

	/**
	 * 
	 * @param time
	 */
	public Sensor(long time){
		super.setTime(time);
	}

}

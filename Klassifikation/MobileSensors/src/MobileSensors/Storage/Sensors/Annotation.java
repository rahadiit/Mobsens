package MobileSensors.Storage.Sensors;

import MobileSensors.Storage.Sensor;

/**
 * Annotation Data Model 
 * 
 * @author henny, thomas, max
 *
 */
public class Annotation extends Sensor {
	
	private String tag;
	
	/**
	 * 
	 * @param time
	 * @param tag
	 */
	public Annotation(long time, String tag){
		super(time);
		this.tag=tag;
	}

	/**
	 * 
	 * @return
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * 
	 * @param tag
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

}

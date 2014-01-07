package MobileSensors.Storage.Event;

/**
 * 
 * Mobile Sensors EventType Enumeration
 * 
 * @author henny, thomas, max
 *
 */
public enum EventType {
	
	STANDING, 
	
	BRAKING,
	
	JERK;

	/**
	 * 
	 */
	public String toString() {
		return this.name().toLowerCase();
	}

}
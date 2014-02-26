package MobileSensors.Storage.Events;

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
	
	DODGE,
	
	JERK;

	
	public String toString() {
		return this.name().toLowerCase();
	}

}
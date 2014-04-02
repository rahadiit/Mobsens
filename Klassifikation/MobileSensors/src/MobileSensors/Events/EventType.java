package MobileSensors.Events;

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
	
	JERK,
	
	KERBSTONE
	;

	
	public String toString() {
		return this.name().toLowerCase();
	}

}
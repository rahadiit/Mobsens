package MobileSensors.Storage.Event;

import MobileSensors.Storage.Sensors.Sensor.Timeable;

/**
 * Mobile Sensors Event Data Model
 * 
 * @author henny, thomas, max
 *
 */
public class Event extends Timeable{

	private long time;
	private EventType eventType;

	/**
	 * 
	 * @param time
	 * @param eventType
	 */
	public Event(long time, EventType eventType) {
		this.time = time;
		this.eventType = eventType;
	}
	
	/**
	 * 
	 * @return
	 */
	public long getTime() {
		return time;
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
	public EventType getEventType() {
		return eventType;
	}

	/**
	 * 
	 * @param eventType
	 */
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

}
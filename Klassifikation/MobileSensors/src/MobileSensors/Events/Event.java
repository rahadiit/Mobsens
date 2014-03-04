package MobileSensors.Events;

/**
 * Mobile Sensors Event Data Model
 * 
 * @author henny, thomas, max
 *
 */
public class Event {

	private long startTime;
	private long endTime;
	private EventType eventType;

	/**
	 * 
	 * @param time
	 * @param eventType
	 */
	public Event(long startTime, long endTime, EventType eventType) {
		
		this.startTime = startTime;
		this.endTime   = endTime;
		this.eventType = eventType;
		
	}
	
	public Event (long time, EventType eventType) {
		
		this.startTime = time;
		this.endTime   = time;
		this.eventType = eventType;
		
	}
	

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
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
	
	@Override
	public String toString(){
		
		return this.startTime + " " + this.endTime + " " + this.eventType.toString();
		
	}

}
package MobileSensors.Storage.Event;

public class Event {

	long time;
	EventType eventType;

	public Event(long time, EventType eventType) {
		this.time = time;
		this.eventType = eventType;
	}

}
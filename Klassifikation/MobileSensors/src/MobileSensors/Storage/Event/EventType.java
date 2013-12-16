package MobileSensors.Storage.Event;

public enum EventType {
	STANDING, BRAKING, JERK;

	public String toString() {
		return this.name().toLowerCase();
	}
}
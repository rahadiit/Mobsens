package MobileSensors.Storage.Event;

public enum EventType {
	STANDING, BRAKING;

	public String toString() {
		return this.name().toLowerCase();
	}
}
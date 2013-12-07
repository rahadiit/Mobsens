package MobileSensors.Storage.Sensors.Sensor;

public abstract class Sensor {

	private long time;
	
	public Sensor(long time){
		this.time=time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public long getTime() {
		return this.time;
	}

}

package MobileSensors.Storage.Sensors;

import MobileSensors.Storage.Sensors.Sensor.Sensor;

public class Annotation extends Sensor{
	private String tag;
	
	public Annotation(long time, String tag){
		super(time);
		this.tag=tag;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}

package MobileSensors.Sensors;

import java.util.Date;

public class Annotation extends Sensor {

	private String text;
	
	public Annotation (long time, String text) {
		
		super(time);
		
		this.text = text;
		
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public String toString() {
		
		return "ann(" + new Date(this.getTime()) + ", " + this.getText() + ")";
		
	}
	
}

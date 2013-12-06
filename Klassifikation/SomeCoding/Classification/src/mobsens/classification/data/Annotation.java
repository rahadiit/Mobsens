package mobsens.classification.data;

public class Annotation {
	
	private double time;
	private String tag;
	
	public Annotation(double time, String tag){	
		this.time=time;
		this.tag=tag;
	}
	
	public double getTime() {
		return time;
	}
	public void setTime(double time) {
		this.time = time;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	

}

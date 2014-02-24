package MobileSensors.Test.Output;

public class ArffAttribute {

	private String name;
	private String type;
	
	public ArffAttribute (String name, String type) {
		
		this.name = name;
		this.type = type;
		
	}
	
	public String getName () {
		
		return this.name;
		
	}
	
	public String getType () {
		
		return this.type;
		
	}
	
	public String toString () {
		
		return "@ATTRIBUTE " + this.name + " " + this.type;
		
	}
	
	
}

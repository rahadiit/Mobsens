package MobileSensors.Sensors;

public abstract class SensorVector extends SensorMatrix {

	public SensorVector(int colCount) {
		
		super(colCount, 1);
		
	}

	protected double getValue (int colIndex) {
		
		return super.getValue(colIndex, 0);
		
	}
	
	protected void setValue (int colIndex, double value) {
		
		super.setValue(colIndex, 0, value);
		
	}
	
}

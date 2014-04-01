package MobileSensors.Sensors;

/**
 * Abstract class for double matrices with optimized access to column vectors
 * 
 * @author darjeeling
 *
 */
public abstract class SensorMatrix {

	private int colCount;
	private double[][] values;
	
	public SensorMatrix (int colCount, int rowCount) {
		
		this.colCount = colCount;
		this.values = new double[colCount][rowCount];
		
	}
	
	protected double[][] getValues () {
		
		return this.values;
		
	}
	
	protected double getValue (int colIndex, int rowIndex) {
		
		return this.values[colIndex][rowIndex];
		
	}
	
	protected void setValue (int colIndex, int rowIndex, double value) {
		
		this.values[colIndex][rowIndex] = value;
		
	}
	
	protected double[] getColumn (int colIndex) {
		
		return this.values[colIndex];
		
	}
	
	protected void setColumn (int colIndex, double[] column) {
		
		this.values[colIndex] = column;
		
	}
	
	protected double[] getRow (int rowIndex) {
		
		double[] row = new double[this.colCount];
		
		for (int i=0; i < this.colCount; i++) {
			
			row[i] = this.values[i][rowIndex];
			
		}
		
		return row;
		
	}
	
	protected void setRow (int rowIndex, double[] row) {
		
		for (int i=0; i < this.colCount; i++) {
			
			this.values[i][rowIndex] = row[i];
			
		}
		
	}
	
	
}

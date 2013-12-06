package mobsens.classification.data;

public enum Sensor {
	ACCELEROMETERS, ANNOTATIONS, 
	BATTERIES, GRAVITIES, GYROSCOPES, 
	LIGHTS, LINEAR_ACCELERATIONS, LOCATIONS, 
	MAGNETIC_FIELDS, PRESSURES, PROXIMITIES, 
	ROTATION_VECTORS;
	
	public String toString(){
		return this.name().toLowerCase();
	}
}

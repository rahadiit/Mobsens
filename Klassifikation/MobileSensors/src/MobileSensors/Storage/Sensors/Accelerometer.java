package MobileSensors.Storage.Sensors;

import MobileSensors.Enums.AcceleroOption;
import MobileSensors.Enums.Axis;
import MobileSensors.Storage.Sensors.Sensor.Sensor;

/**
 * 
 * @author henny, thomas, max
 * 
 *         Accelerometer Data Model
 * 
 */

public class Accelerometer extends Sensor implements Comparable {

	private double x, y, z, jerkX, jerkY, jerkZ;

	private double smoothedX, smoothedY, smoothedZ;

	private double meanShortX, meanShortY, meanShortZ;
	private double meanLongX, meanLongY, meanLongZ;

	private Axis interestedAxis = Axis.X;

	/**
	 * 
	 * @param time
	 * @param x
	 * @param y
	 * @param z
	 */
	public Accelerometer(long time, double x, double y, double z) {

		super(time);
		this.x = x;
		this.y = y;
		this.z = z;

	}

	public double getJerkX() {
		return jerkX;
	}

	public void setJerkX(double jerkX) {
		this.jerkX = jerkX;
	}

	public double getJerkY() {
		return jerkY;
	}

	public void setJerkY(double jerkY) {
		this.jerkY = jerkY;
	}

	public double getJerkZ() {
		return jerkZ;
	}

	public void setJerkZ(double jerkZ) {
		this.jerkZ = jerkZ;
	}

	/**
	 * 
	 * Gets x-coordinate
	 * 
	 * @return x-coordinate
	 */
	public double getX() {

		return x;

	}

	/**
	 * 
	 * Sets x-coordinate
	 * 
	 * @param x
	 */
	public void setX(double x) {

		this.x = x;

	}

	/**
	 * 
	 * Gets y-coordinate
	 * 
	 * @return y-coordinate
	 */
	public double getY() {

		return y;

	}

	/**
	 * 
	 * Sets y-coordinate
	 * 
	 * @param y
	 */
	public void setY(double y) {

		this.y = y;

	}

	/**
	 * 
	 * Gets z-coordinate
	 * 
	 * @return z-coordinate
	 */
	public double getZ() {

		return z;

	}

	/**
	 * 
	 * Gets z-coordinate
	 * 
	 * @param z
	 */
	public void setZ(double z) {

		this.z = z;

	}

	public double getAxisValue(Axis a) {

		switch (a) {
		case X:
			return this.getX();
		case Y:
			return this.getY();
		case Z:
			return this.getZ();

		default:
			return this.getX();
		}

	}

	public double getMeanShort(Axis a) {
		switch (a) {
		case X:
			return this.getMeanShortX();
		case Y:
			return this.getMeanShortY();
		case Z:
			return this.getMeanShortZ();

		default:
			return this.getMeanShortX();
		}

	}

	public double getMeanLong(Axis a) {
		switch (a) {
		case X:
			return this.getMeanLongX();
		case Y:
			return this.getMeanLongY();
		case Z:
			return this.getMeanLongZ();

		default:
			return this.getMeanLongX();
		}

	}

	public void setOption(double value, AcceleroOption option, Axis axis) {

		switch (option) {
		case JERK:
			this.setJerk(value, axis);
		case MEAN_SHORT:
			this.setMeanShort(value, axis);
		case MEAN_LONG:
			this.setMeanLong(value, axis);
		case PLAIN:
			this.setAxis(value, axis);
		default:
			this.setAxis(value, axis);
		}

	}

	public void setOptionIntr(double value, AcceleroOption option) {
		this.setOption(value, option, this.getInterestedAxis());
	}

	public void setAxisIntr(double value) {
		setAxis(value, this.getInterestedAxis());

	}

	public void setAxis(double value, Axis axis) {
		switch (axis) {
		case X: {
			this.setX(value);
			break;
		}
		case Y: {
			this.setY(value);
			break;
		}
		case Z: {
			this.setZ(value);
			break;
		}
		}
	}

	public void setJerk(double value, Axis axis) {
		switch (axis) {
		case X: {
			this.setJerkX(value);
			break;
		}
		case Y: {
			this.setJerkY(value);
			break;
		}
		case Z: {
			this.setJerkZ(value);
			break;
		}
		}
	}

	public double getJerk(Axis axis) {
		switch (axis) {
		case X: {
			return this.getJerkX();
		}
		case Y: {
			return this.getJerkY();
		}
		case Z: {
			return this.getJerkZ();
		}
		default:
			return this.getJerkX();
		}
	}

	public void setMeanShort(double value, Axis axis) {
		switch (axis) {
		case X: {
			this.setMeanShortX(value);
			break;
		}
		case Y: {
			this.setMeanShortY(value);
			break;
		}
		case Z: {
			this.setMeanShortZ(value);
			break;
		}
		}
	}

	public void setMeanLong(double value, Axis axis) {
		switch (axis) {
		case X: {
			this.setMeanLongX(value);
			break;
		}
		case Y: {
			this.setMeanLongY(value);
			break;
		}
		case Z: {
			this.setMeanLongZ(value);
			break;
		}
		}
	}

	public double getOptionValue(AcceleroOption option, Axis axis) {

		switch (option) {
		case JERK:
			return this.getJerk(axis);
		case MEAN_SHORT:
			return this.getMeanShort(axis);
		case MEAN_LONG:
			return this.getMeanLong(axis);
		case PLAIN:
			return this.getAxisValue(axis);
		default:
			return this.getAxisValue(axis);
		}

	}

	public Axis getInterestedAxis() {
		return interestedAxis;
	}

	public void setInterestedAxis(Axis interestedAxis) {
		this.interestedAxis = interestedAxis;
	}

	public double getSmoothedX() {
		return smoothedX;
	}

	public void setSmoothedX(double smoothedX) {
		this.smoothedX = smoothedX;
	}

	public double getSmoothedY() {
		return smoothedY;
	}

	public void setSmoothedY(double smoothedY) {
		this.smoothedY = smoothedY;
	}

	public double getSmoothedZ() {
		return smoothedZ;
	}

	public void setSmoothedZ(double smoothedZ) {
		this.smoothedZ = smoothedZ;
	}

	public double getMeanShortX() {
		return meanShortX;
	}

	public void setMeanShortX(double meanShortX) {
		this.meanShortX = meanShortX;
	}

	public double getMeanShortY() {
		return meanShortY;
	}

	public void setMeanShortY(double meanShortY) {
		this.meanShortY = meanShortY;
	}

	public double getMeanShortZ() {
		return meanShortZ;
	}

	public void setMeanShortZ(double meanShortZ) {
		this.meanShortZ = meanShortZ;
	}

	public double getMeanLongX() {
		return meanLongX;
	}

	public void setMeanLongX(double meanLongX) {
		this.meanLongX = meanLongX;
	}

	public double getMeanLongY() {
		return meanLongY;
	}

	public void setMeanLongY(double meanLongY) {
		this.meanLongY = meanLongY;
	}

	public double getMeanLongZ() {
		return meanLongZ;
	}

	public void setMeanLongZ(double meanLongZ) {
		this.meanLongZ = meanLongZ;
	}

	@Override
	public int compareTo(Object arg0) {

		if (arg0 instanceof Accelerometer) {
			Accelerometer accelerometer = (Accelerometer) arg0;

			if (this.getAxisValue(this.interestedAxis) < accelerometer
					.getAxisValue(this.interestedAxis))
				return -1;
			else if (this.getAxisValue(this.interestedAxis) > accelerometer
					.getAxisValue(this.interestedAxis))
				return 1;
		}

		return 0;
	}

}
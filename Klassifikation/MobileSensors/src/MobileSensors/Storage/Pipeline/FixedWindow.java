package MobileSensors.Storage.Pipeline;
import java.util.Arrays;

public class FixedWindow {
	private long innerStart;

	private long innerLength;

	private long valueDistance;

	private double[][] values;

	public long getInnerStart() {
		return innerStart;
	}

	public void setInnerStart(long innerStart) {
		this.innerStart = innerStart;
	}

	public long getInnerLength() {
		return innerLength;
	}

	public void setInnerLength(long innerLength) {
		this.innerLength = innerLength;
	}

	public long getValueDistance() {
		return valueDistance;
	}

	public void setValueDistance(long valueDistance) {
		this.valueDistance = valueDistance;
	}

	public double[][] getValues() {
		return values;
	}

	public void setValues(double[][] values) {
		this.values = values;
	}

	@Override
	public String toString() {
		return "FixedWindow [innerStart=" + innerStart + ", innerLength="
				+ innerLength + ", valueDistance=" + valueDistance
				+ ", values=" + Arrays.deepToString(values) + "]";
	}
}

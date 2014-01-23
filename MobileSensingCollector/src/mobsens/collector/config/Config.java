package mobsens.collector.config;

public interface Config
{
	public static final double OFF = -1.0;

	public static final double FREQUENCY_ACCELEROMETER = 100.0;

	public static final double FREQUENCY_GYROSCOPE = 100.0;

	public static final double FREQUENCY_LINEAR_ACCELEROMETER = OFF;

	public static final double FREQUENCY_GRAVITY = OFF;

	public static final double FREQUENCY_MAGNETIC_FIELD = 100.0;

	public static final double FREQUENCY_LOCATION = 4.0;

	/**
	 * Auf Erdbeschleunigung innerhalb einer Millisekunde
	 */
	public static final double ACCELEROMETER_MAX_DDT = 10.0f / 0.01;

	/**
	 * Ungefähr 250km/h
	 */
	public static final double LOCATION_MAX_DDT = 70.0f / 1.0;
}

package mobsens.collector.config;

public interface Config
{
	public static final double FREQUENCY_ACCELEROMETER = 50.0;

	public static final double FREQUENCY_GYROSCOPE = 50.0;

	public static final double FREQUENCY_LINEAR_ACCELEROMETER = 50.0;

	public static final double FREQUENCY_GRAVITY = 50.0;

	public static final double FREQUENCY_MAGNETIC_FIELD = 50.0;

	public static final double FREQUENCY_LOCATION = 5.0;

	/**
	 * Auf Erdbeschleunigung innerhalb einer Millisekunde
	 */
	public static final double ACCELEROMETER_MAX_DDT = 10.0f / 0.001;

	/**
	 * Ungefähr 250km/h
	 */
	public static final double LOCATION_MAX_DDT = 70.0f / 1.0;
}

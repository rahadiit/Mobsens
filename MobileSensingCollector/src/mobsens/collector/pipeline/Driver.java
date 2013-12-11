package mobsens.collector.pipeline;

public interface Driver
{
	/**
	 * Startet den Treiber
	 */
	public void start();

	/**
	 * Beendet den Treiber
	 */
	public void stop();
}

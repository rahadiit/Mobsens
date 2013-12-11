package mobsens.collector.pipeline;

/**
 * Start- und Stoppbare Generator
 * 
 * @author Pizza
 * 
 * @param <Item>
 *            Typ der generierten Item
 */
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

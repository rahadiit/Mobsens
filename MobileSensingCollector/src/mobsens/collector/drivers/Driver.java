package mobsens.collector.drivers;

import mobsens.collector.pipeline.Generator;

/**
 * Start- und Stoppbare Generator
 * 
 * @author Pizza
 * 
 * @param <Item>
 *            Typ der generierten Item
 */
public interface Driver<Item> extends Generator<Item>
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

package mobsens.collector.pipeline;

public interface Receiver<Item> extends Consumer<Item>
{
	/**
	 * Startet den Empfänger
	 */
	public void start();

	/**
	 * Beendet den Empfänger
	 */
	public void stop();
}

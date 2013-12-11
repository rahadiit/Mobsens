package mobsens.collector.pipeline;

/**
 * Quelle von Items
 * 
 * @author Pizza
 * 
 * @param <Item>
 *            Der Typ der Items
 */
public interface Generator<Item>
{
	public boolean hasConsumer();
	
	/**
	 * 
	 * @return Gibt den aktuellen Konsumenten aus
	 */
	public Consumer<? super Item> getConsumer();

	/**
	 * Setzt den aktuellen Konsumenten
	 * 
	 * @param consumer
	 *            Der neue Konsument
	 */
	public void setConsumer(Consumer<? super Item> consumer);
}

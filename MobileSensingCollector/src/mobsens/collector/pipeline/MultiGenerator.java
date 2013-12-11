package mobsens.collector.pipeline;

/**
 * Quelle von Items
 * 
 * @author Pizza
 * 
 * @param <Item>
 *            Der Typ der Items
 */
public interface MultiGenerator<Item>
{
	public boolean hasConsumers();
	
	public Iterable<Consumer<? super Item>> getConsumers();

	public void addConsumer(Consumer<? super Item> consumer);

	public void removeConsumer(Consumer<? super Item> consumer);
}

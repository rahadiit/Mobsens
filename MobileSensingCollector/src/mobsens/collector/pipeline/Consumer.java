package mobsens.collector.pipeline;

/**
 * Konsument eines von einer Quelle generierten Items
 * 
 * @author Pizza
 * 
 * @param <Item>
 *            Der Typ der Items
 */
public interface Consumer<Item>
{
	/**
	 * Konsumiert das gegebene Item
	 * 
	 * @param item
	 *            Das von der Quelle generierte Item, nie null
	 */
	public void consume(Item item);
}

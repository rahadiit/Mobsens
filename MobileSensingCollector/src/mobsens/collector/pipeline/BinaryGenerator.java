package mobsens.collector.pipeline;

public interface BinaryGenerator<LeftItem, RightItem>
{
	public boolean hasLeftConsumer();

	/**
	 * 
	 * @return Gibt den aktuellen linken Konsumenten aus
	 */
	public Consumer<? super LeftItem> getLeftConsumer();

	/**
	 * Setzt den aktuellen linken Konsumenten
	 * 
	 * @param consumer
	 *            Der neue Konsument
	 */
	public void setLeftConsumer(Consumer<? super LeftItem> consumer);

	public boolean hasRightConsumer();

	/**
	 * 
	 * @return Gibt den aktuellen rechten Konsumenten aus
	 */
	public Consumer<? super RightItem> getRightConsumer();

	/**
	 * Setzt den aktuellen rechten Konsumenten
	 * 
	 * @param consumer
	 *            Der neue Konsument
	 */
	public void setRightConsumer(Consumer<? super RightItem> consumer);
}

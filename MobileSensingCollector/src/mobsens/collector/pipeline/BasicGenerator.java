package mobsens.collector.pipeline;

public class BasicGenerator<Item> implements Generator<Item>
{
	private Consumer<? super Item> consumer;

	protected void offer(Item item)
	{
		if (hasConsumer())
		{
			consumer.consume(item);
		}
	}

	@Override
	public boolean hasConsumer()
	{
		return consumer != null;
	}

	@Override
	public Consumer<? super Item> getConsumer()
	{
		return consumer;
	}

	@Override
	public void setConsumer(Consumer<? super Item> consumer)
	{
		this.consumer = consumer;
	}

}

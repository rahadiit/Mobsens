package mobsens.collector.pipeline;

public class BasicGenerator<Item> implements Generator<Item>
{
	protected Consumer<? super Item> consumer;

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

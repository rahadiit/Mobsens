package mobsens.collector.pipeline.basics;

import mobsens.collector.pipeline.BasicBinaryGenerator;
import mobsens.collector.pipeline.Consumer;
import mobsens.collector.pipeline.Generator;

public abstract class Predicate<Item> extends BasicBinaryGenerator<Item, Item> implements Generator<Item>, Consumer<Item>
{
	protected abstract boolean predicate(Item item);

	@Override
	public void consume(Item item)
	{
		if (predicate(item))
		{
			offerLeft(item);
		}
		else
		{
			offerRight(item);
		}
	}

	@Override
	public boolean hasConsumer()
	{
		return hasLeftConsumer();
	}

	@Override
	public Consumer<? super Item> getConsumer()
	{
		return getLeftConsumer();
	}

	@Override
	public void setConsumer(Consumer<? super Item> consumer)
	{
		setLeftConsumer(consumer);
	}
}

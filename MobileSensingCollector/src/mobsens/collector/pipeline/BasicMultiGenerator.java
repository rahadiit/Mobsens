package mobsens.collector.pipeline;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BasicMultiGenerator<Item> implements MultiGenerator<Item>
{
	private final Set<Consumer<? super Item>> consumers;

	protected void offer(Item item)
	{
		if (hasConsumers())
		{
			for (Consumer<? super Item> consumer : consumers)
			{
				consumer.consume(item);
			}
		}
	}

	public BasicMultiGenerator()
	{
		consumers = new HashSet<Consumer<? super Item>>();
	}

	@Override
	public boolean hasConsumers()
	{
		return !consumers.isEmpty();
	}

	@Override
	public Iterable<? extends Consumer<? super Item>> getConsumers()
	{
		return Collections.unmodifiableSet(consumers);
	}

	@Override
	public void addConsumer(Consumer<? super Item> consumer)
	{
		consumers.add(consumer);
	}

	@Override
	public void removeConsumer(Consumer<? super Item> consumer)
	{
		consumers.remove(consumer);
	}

}

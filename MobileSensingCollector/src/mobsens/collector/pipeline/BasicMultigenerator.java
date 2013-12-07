package mobsens.collector.pipeline;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BasicMultigenerator<Item> implements Multigenerator<Item>
{
	private final Set<Consumer<? super Item>> consumers;
	
	public BasicMultigenerator()
	{
		consumers = new HashSet<Consumer<? super Item>>();
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

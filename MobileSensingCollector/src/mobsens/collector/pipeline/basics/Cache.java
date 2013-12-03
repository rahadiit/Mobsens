package mobsens.collector.pipeline.basics;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import mobsens.collector.pipeline.BasicGenerator;
import mobsens.collector.pipeline.Consumer;

public class Cache<Item> extends BasicGenerator<Item> implements Consumer<Item>
{
	private Queue<Item> queuedItems;

	public Cache(boolean allowConcurrentWrite)
	{
		if (allowConcurrentWrite)
		{
			queuedItems = new ConcurrentLinkedQueue<Item>();
		}
		else
		{
			queuedItems = new LinkedList<Item>();
		}
	}

	@Override
	public void consume(Item item)
	{
		queuedItems.offer(item);
	}

	public final boolean releaseOneItem()
	{
		if (queuedItems.isEmpty())
		{
			return false;
		}
		else
		{
			final Item item = queuedItems.poll();

			if (consumer != null)
			{
				consumer.consume(item);
			}

			return true;
		}
	}

	public final boolean releaseAllItems()
	{
		if (releaseOneItem())
		{
			while (releaseOneItem())
				;
			return true;
		}
		else
		{
			return false;
		}
	}
}

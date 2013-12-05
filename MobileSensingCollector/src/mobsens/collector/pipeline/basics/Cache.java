package mobsens.collector.pipeline.basics;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import mobsens.collector.metrication.BasicMeter;
import mobsens.collector.pipeline.BasicGenerator;
import mobsens.collector.pipeline.Consumer;

public class Cache<Item> extends BasicGenerator<Item> implements Consumer<Item>
{
	public class CacheMeter extends BasicMeter
	{
		public static final int FIELD_ITEMS = 1;

		@Override
		public int getNextField(int field)
		{
			switch (field)
			{
			case FIELD_INIT:
				return FIELD_ITEMS;
			case FIELD_ITEMS:
				return FIELD_NONE;
			}

			throw new IllegalArgumentException("field");
		}

		@Override
		public int getType(int field)
		{
			switch (field)
			{
			case FIELD_ITEMS:
				return TYPE_AMOUNT;
			}

			throw new IllegalArgumentException("field");
		}

		@Override
		public String getName(int field)
		{
			switch (field)
			{
			case FIELD_ITEMS:
				return "Items";
			}

			throw new IllegalArgumentException("field");
		}

		@Override
		public String getValue(int field)
		{
			switch (field)
			{
			case FIELD_ITEMS:
				return Integer.toString(queuedItems.size());
			}

			throw new IllegalArgumentException("field");
		}
	}

	public final CacheMeter meter = new CacheMeter();

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

		meter.updated(CacheMeter.FIELD_ITEMS);
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
			{
				// Wiederholen
			}

			return true;
		}
		else
		{
			return false;
		}
	}
}

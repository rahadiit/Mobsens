package mobsens.collector.pipeline.basics;

import mobsens.collector.pipeline.BasicGenerator;
import mobsens.collector.pipeline.Consumer;

public class Synchronizer<Item> extends BasicGenerator<Item> implements Consumer<Item>
{
	public final Object gateObject;

	public Synchronizer(Object gateObject)
	{
		this.gateObject = gateObject;
	}

	public Synchronizer()
	{
		this(new Object());
	}

	@Override
	public void consume(Item item)
	{
		if (hasConsumer())
		{
			synchronized (gateObject)
			{
				offer(item);
			}
		}
	}
}

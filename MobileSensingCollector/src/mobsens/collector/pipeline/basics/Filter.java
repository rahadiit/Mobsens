package mobsens.collector.pipeline.basics;

import mobsens.collector.pipeline.BasicGenerator;
import mobsens.collector.pipeline.Consumer;

public class Filter<Item> extends BasicGenerator<Item> implements Consumer<Object>
{
	public final Class<Item> filterClass;

	public Filter(Class<Item> filterClass)
	{
		this.filterClass = filterClass;
	}

	@Override
	public void consume(Object item)
	{
		if (consumer != null)
		{
			if (filterClass.isInstance(item))
			{
				consumer.consume(filterClass.cast(item));
			}
		}

	}

}

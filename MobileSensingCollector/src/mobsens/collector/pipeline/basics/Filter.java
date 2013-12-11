package mobsens.collector.pipeline.basics;

import mobsens.collector.pipeline.BasicBinaryGenerator;
import mobsens.collector.pipeline.Consumer;
import mobsens.collector.pipeline.Generator;

public class Filter<Specific, General> extends BasicBinaryGenerator<Specific, General> implements Generator<Specific>, Consumer<General>
{
	public final Class<Specific> filterClass;

	public Filter(Class<Specific> filterClass)
	{
		this.filterClass = filterClass;
	}

	@Override
	public void consume(General item)
	{
		if (filterClass.isInstance(item))
		{
			if (hasLeftConsumer())
			{
				getLeftConsumer().consume(filterClass.cast(item));
			}
		}
		else
		{
			if (hasRightConsumer())
			{
				getRightConsumer().consume(item);
			}
		}
	}

	@Override
	public boolean hasConsumer()
	{
		return hasLeftConsumer();
	}

	@Override
	public Consumer<? super Specific> getConsumer()
	{
		return getLeftConsumer();
	}

	@Override
	public void setConsumer(Consumer<? super Specific> consumer)
	{
		setLeftConsumer(consumer);
	}
}

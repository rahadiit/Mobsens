package mobsens.collector.pipeline.basics;

import mobsens.collector.pipeline.BasicBinaryGenerator;
import mobsens.collector.pipeline.Consumer;
import mobsens.collector.pipeline.Generator;

public class ClassFilter<Specific, General> extends BasicBinaryGenerator<Specific, General> implements Generator<Specific>, Consumer<General>
{
	public final Class<Specific> classFilter;

	public ClassFilter(Class<Specific> filterClass)
	{
		this.classFilter = filterClass;
	}

	@Override
	public void consume(General item)
	{
		if (classFilter.isInstance(item))
		{
			offerLeft(classFilter.cast(item));
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

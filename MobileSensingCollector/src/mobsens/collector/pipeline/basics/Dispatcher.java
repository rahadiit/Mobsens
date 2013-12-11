package mobsens.collector.pipeline.basics;

import mobsens.collector.pipeline.BasicMultiGenerator;
import mobsens.collector.pipeline.Consumer;

public class Dispatcher<Item> extends BasicMultiGenerator<Item> implements Consumer<Item>
{
	@Override
	public void consume(Item item)
	{
		for (Consumer<? super Item> consumer : getConsumers())
		{
			consumer.consume(item);
		}
	}

}

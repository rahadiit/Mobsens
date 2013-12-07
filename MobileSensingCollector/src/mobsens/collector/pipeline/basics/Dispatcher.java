package mobsens.collector.pipeline.basics;

import mobsens.collector.pipeline.BasicMultigenerator;
import mobsens.collector.pipeline.Consumer;

public class Dispatcher<Item> extends BasicMultigenerator<Item> implements Consumer<Item>
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

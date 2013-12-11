package mobsens.collector.pipeline.basics;

import mobsens.collector.pipeline.BasicMultiGenerator;
import mobsens.collector.pipeline.Consumer;

public class Dispatcher<Item> extends BasicMultiGenerator<Item> implements Consumer<Item>
{
	@Override
	public void consume(Item item)
	{
		offer(item);
	}

}

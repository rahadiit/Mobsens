package mobsens.collector.consumers;

import mobsens.collector.pipeline.Consumer;
import android.util.Log;

public class LogcatConsumer<Item> implements Consumer<Item>
{
	@Override
	public void consume(Item item)
	{
		Log.i(LogcatConsumer.class.getSimpleName(), item.toString());
	}
}

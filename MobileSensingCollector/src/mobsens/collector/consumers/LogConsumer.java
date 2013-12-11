package mobsens.collector.consumers;

import mobsens.collector.pipeline.Consumer;
import android.util.Log;

public class LogConsumer implements Consumer<Object>
{
	public final int channel;

	public final String tag;

	public LogConsumer(int channel, String tag)
	{
		this.channel = channel;
		this.tag = tag;
	}

	@Override
	public void consume(Object item)
	{
		Log.println(channel, tag, item == null ? "<null>" : item.toString());
	}
}

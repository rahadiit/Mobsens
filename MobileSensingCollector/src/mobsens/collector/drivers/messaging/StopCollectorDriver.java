package mobsens.collector.drivers.messaging;

import mobsens.collector.drivers.Driver;
import mobsens.collector.intents.IntentStopCollector;
import mobsens.collector.pipeline.BasicGenerator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;

public class StopCollectorDriver extends BasicGenerator<StopCollectorOutput> implements Driver<StopCollectorOutput>
{
	public final BroadcastReceiver INTENT_ENDPOINT = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			if (IntentStopCollector.ACTION.equals(intent.getAction()))
			{
				if (consumer != null)
				{
					// Item erstellen
					final StopCollectorOutput item = new StopCollectorOutput();

					// Item senden
					consumer.consume(item);
				}
			}
		}
	};

	public final ContextWrapper contextWrapper;

	public StopCollectorDriver(ContextWrapper contextWrapper)
	{
		this.contextWrapper = contextWrapper;
	}

	private boolean started = false;

	@Override
	public void start()
	{
		if (started) return;
		started = true;

		contextWrapper.registerReceiver(INTENT_ENDPOINT, new IntentFilter(IntentStopCollector.ACTION));
	}

	@Override
	public void stop()
	{
		if (!started) return;
		started = false;

		contextWrapper.unregisterReceiver(INTENT_ENDPOINT);
	}
}

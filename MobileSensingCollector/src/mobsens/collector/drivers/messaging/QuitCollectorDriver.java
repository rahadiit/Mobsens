package mobsens.collector.drivers.messaging;

import mobsens.collector.drivers.Driver;
import mobsens.collector.intents.IntentQuitCollector;
import mobsens.collector.pipeline.BasicGenerator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;

public class QuitCollectorDriver extends BasicGenerator<QuitCollectorOutput> implements Driver<QuitCollectorOutput>
{
	public final BroadcastReceiver INTENT_ENDPOINT = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			if (IntentQuitCollector.ACTION.equals(intent.getAction()))
			{
				if (consumer != null)
				{
					// Item erstellen
					final QuitCollectorOutput item = new QuitCollectorOutput();

					// Item senden
					consumer.consume(item);
				}
			}
		}
	};

	public final ContextWrapper contextWrapper;

	public QuitCollectorDriver(ContextWrapper contextWrapper)
	{
		this.contextWrapper = contextWrapper;
	}

	private boolean started = false;

	@Override
	public void start()
	{
		if (started) return;
		started = true;

		contextWrapper.registerReceiver(INTENT_ENDPOINT, new IntentFilter(IntentQuitCollector.ACTION));
	}

	@Override
	public void stop()
	{
		if (!started) return;
		started = false;

		contextWrapper.unregisterReceiver(INTENT_ENDPOINT);
	}
}

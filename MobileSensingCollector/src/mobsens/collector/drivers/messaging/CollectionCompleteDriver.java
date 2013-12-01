package mobsens.collector.drivers.messaging;

import mobsens.collector.intents.IntentCollectionComplete;
import mobsens.collector.pipeline.BasicGenerator;
import mobsens.collector.pipeline.Driver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;

public class CollectionCompleteDriver extends BasicGenerator<CollectionCompleteOutput> implements Driver<CollectionCompleteOutput>
{
	public final BroadcastReceiver INTENT_ENDPOINT = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			if (IntentCollectionComplete.ACTION.equals(intent.getAction()))
			{
				if (consumer != null)
				{
					// Intent-Extras holen
					final String extra_location = intent.getStringExtra(IntentCollectionComplete.EXTRA_LOCATION);

					// Felder erstellen
					final String location = extra_location;

					// Item erstellen
					final CollectionCompleteOutput item = new CollectionCompleteOutput(location);

					// Item senden
					consumer.consume(item);
				}
			}
		}
	};

	public final ContextWrapper contextWrapper;

	public CollectionCompleteDriver(ContextWrapper contextWrapper)
	{
		this.contextWrapper = contextWrapper;
	}

	private boolean started = false;

	@Override
	public void start()
	{
		if (started) return;
		started = true;

		contextWrapper.registerReceiver(INTENT_ENDPOINT, new IntentFilter(IntentCollectionComplete.ACTION));
	}

	@Override
	public void stop()
	{
		if (!started) return;
		started = false;

		contextWrapper.unregisterReceiver(INTENT_ENDPOINT);
	}
}

package mobsens.collector.drivers.messaging;

import mobsens.collector.intents.IntentStartCollector;
import mobsens.collector.pipeline.Driver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;

public abstract class StartCollectorDriver implements Driver
{
	public final BroadcastReceiver INTENT_ENDPOINT = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			if (IntentStartCollector.ACTION.equals(intent.getAction()))
			{
				// Intent-Extras holen
				final String extra_title = intent.getStringExtra(IntentStartCollector.EXTRA_TITLE);

				// Felder erstellen
				final String title = extra_title;

				// Methode aufrufen
				onStartCollector(title);
			}
		}
	};

	protected abstract void onStartCollector(String title);

	public final ContextWrapper contextWrapper;

	public StartCollectorDriver(ContextWrapper contextWrapper)
	{
		this.contextWrapper = contextWrapper;
	}

	private boolean started = false;

	@Override
	public void start()
	{
		if (started) return;
		started = true;

		contextWrapper.registerReceiver(INTENT_ENDPOINT, new IntentFilter(IntentStartCollector.ACTION));
	}

	@Override
	public void stop()
	{
		if (!started) return;
		started = false;

		contextWrapper.unregisterReceiver(INTENT_ENDPOINT);
	}
}

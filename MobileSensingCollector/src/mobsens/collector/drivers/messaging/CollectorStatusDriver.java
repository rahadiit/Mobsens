package mobsens.collector.drivers.messaging;

import mobsens.collector.intents.IntentCollectorStatus;
import mobsens.collector.pipeline.Driver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;

public abstract class CollectorStatusDriver implements Driver
{
	public final BroadcastReceiver INTENT_ENDPOINT = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			if (IntentCollectorStatus.ACTION.equals(intent.getAction()))
			{
				// Intent-Extras holen
				final boolean extra_status = intent.getBooleanExtra(IntentCollectorStatus.EXTRA_STATUS, false);

				// Felder erstellen
				final boolean status = extra_status;

				// Methode aufrufen
				onCollectorStatus(status);
			}
		}
	};

	protected abstract void onCollectorStatus(boolean status);

	public final ContextWrapper contextWrapper;

	public CollectorStatusDriver(ContextWrapper contextWrapper)
	{
		this.contextWrapper = contextWrapper;
	}

	private boolean started = false;

	@Override
	public void start()
	{
		if (started) return;
		started = true;

		contextWrapper.registerReceiver(INTENT_ENDPOINT, new IntentFilter(IntentCollectorStatus.ACTION));
	}

	@Override
	public void stop()
	{
		if (!started) return;
		started = false;

		contextWrapper.unregisterReceiver(INTENT_ENDPOINT);
	}
}

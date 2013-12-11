package mobsens.collector.drivers.messaging;

import java.util.Date;

import mobsens.collector.intents.IntentLog;
import mobsens.collector.pipeline.Driver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;

public abstract class LogDriver implements Driver
{
	public final BroadcastReceiver INTENT_ENDPOINT = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			if (IntentLog.ACTION.equals(intent.getAction()))
			{
				// Intent-Extras holen
				final long extra_time = intent.getLongExtra(IntentLog.EXTRA_TIME, Long.MIN_VALUE);
				final String extra_title = intent.getStringExtra(IntentLog.EXTRA_TITLE);
				final String extra_subtitle = intent.getStringExtra(IntentLog.EXTRA_SUBTITLE);
				final String extra_description = intent.getStringExtra(IntentLog.EXTRA_DESCRIPTION);

				// Felder erstellen
				final Date time = new Date(extra_time);
				final String title = extra_title;
				final String subtitle = extra_subtitle;
				final String description = extra_description;

				// Methode aufrufen
				onLog(time, title, subtitle, description);
				
			}
		}
	};

	protected abstract void onLog(Date time, String title, String subtitle, String description);

	public final ContextWrapper contextWrapper;

	public LogDriver(ContextWrapper contextWrapper)
	{
		this.contextWrapper = contextWrapper;
	}

	private boolean started = false;

	@Override
	public void start()
	{
		if (started) return;
		started = true;

		contextWrapper.registerReceiver(INTENT_ENDPOINT, new IntentFilter(IntentLog.ACTION));
	}

	@Override
	public void stop()
	{
		if (!started) return;
		started = false;

		contextWrapper.unregisterReceiver(INTENT_ENDPOINT);
	}
}

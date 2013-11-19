package mobsens.collector.drivers.messaging;

import java.util.Date;

import mobsens.collector.drivers.Driver;
import mobsens.collector.intents.IntentLog;
import mobsens.collector.pipeline.BasicGenerator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;

public class LogDriver extends BasicGenerator<LogOutput> implements Driver<LogOutput>
{
	public final BroadcastReceiver INTENT_ENDPOINT = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			if (IntentLog.ACTION.equals(intent.getAction()))
			{
				if (consumer != null)
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

					// Item erstellen
					final LogOutput item = new LogOutput(time, title, subtitle, description);

					// Item senden
					consumer.consume(item);
				}
			}
		}
	};

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

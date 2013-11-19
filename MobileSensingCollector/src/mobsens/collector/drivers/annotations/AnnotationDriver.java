package mobsens.collector.drivers.annotations;

import java.util.Date;

import mobsens.collector.drivers.Driver;
import mobsens.collector.intents.IntentAnnotation;
import mobsens.collector.pipeline.BasicGenerator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;

public class AnnotationDriver extends BasicGenerator<AnnotationOutput> implements Driver<AnnotationOutput>
{
	public final BroadcastReceiver INTENT_ENDPOINT = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			if (IntentAnnotation.ACTION.equals(intent.getAction()))
			{
				if (consumer != null)
				{
					// Intent-Extras holen
					final long extra_time = intent.getLongExtra(IntentAnnotation.EXTRA_TIME, Long.MIN_VALUE);
					final String extra_value = intent.getStringExtra(IntentAnnotation.EXTRA_VALUE);

					// Felder erstellen
					final Date time = new Date(extra_time);
					final String value = extra_value;

					// Item erstellen
					final AnnotationOutput item = new AnnotationOutput(time, value);

					// Item senden
					consumer.consume(item);
				}
			}
		}
	};

	public final ContextWrapper contextWrapper;

	public AnnotationDriver(ContextWrapper contextWrapper)
	{
		this.contextWrapper = contextWrapper;
	}

	private boolean started = false;

	@Override
	public void start()
	{
		if (started) return;
		started = true;

		contextWrapper.registerReceiver(INTENT_ENDPOINT, new IntentFilter(IntentAnnotation.ACTION));
	}

	@Override
	public void stop()
	{
		if (!started) return;
		started = false;
		contextWrapper.unregisterReceiver(INTENT_ENDPOINT);
	}
}

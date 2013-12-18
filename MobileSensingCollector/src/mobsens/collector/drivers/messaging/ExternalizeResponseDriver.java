package mobsens.collector.drivers.messaging;

import java.io.Serializable;
import java.util.Date;

import mobsens.collector.intents.IntentExternalizeResponse;
import mobsens.collector.pipeline.Driver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;

public abstract class ExternalizeResponseDriver implements Driver
{
	public final BroadcastReceiver INTENT_ENDPOINT = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			if (IntentExternalizeResponse.ACTION.equals(intent.getAction()))
			{
				// Intent-Extras holen
				final String extra_handle = intent.getStringExtra(IntentExternalizeResponse.EXTRA_HANDLE);
				final long extra_startTime = intent.getLongExtra(IntentExternalizeResponse.EXTRA_START_TIME, Long.MIN_VALUE);
				final long extra_endTime = intent.getLongExtra(IntentExternalizeResponse.EXTRA_END_TIME, Long.MIN_VALUE);
				final long extra_transmitted = intent.getLongExtra(IntentExternalizeResponse.EXTRA_TRANSMITTED, Long.MIN_VALUE);
				final Serializable extra_exception = intent.getSerializableExtra(IntentExternalizeResponse.EXTRA_EXCEPTION);

				// Felder erstellen
				final String handle = extra_handle;
				final Date startTime = new Date(extra_startTime);
				final Date endTime = new Date(extra_endTime);
				final long transmitted = extra_transmitted;
				final Throwable exception = (Throwable) extra_exception;

				onExternalizeResponse(handle, startTime, endTime, transmitted, exception);
			}
		}
	};

	protected abstract void onExternalizeResponse(String handle, Date startTime, Date endTime, long transmitted, Throwable exception);

	public final ContextWrapper contextWrapper;

	public ExternalizeResponseDriver(ContextWrapper contextWrapper)
	{
		this.contextWrapper = contextWrapper;
	}

	private boolean started = false;

	@Override
	public void start()
	{
		if (started) return;
		started = true;

		contextWrapper.registerReceiver(INTENT_ENDPOINT, new IntentFilter(IntentExternalizeResponse.ACTION));
	}

	@Override
	public void stop()
	{
		if (!started) return;
		started = false;

		contextWrapper.unregisterReceiver(INTENT_ENDPOINT);
	}
}

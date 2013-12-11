package mobsens.collector.drivers.messaging;

import java.io.Serializable;
import java.util.Date;

import mobsens.collector.intents.IntentUploadResponse;
import mobsens.collector.pipeline.Driver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;

public abstract class UploadResponseDriver implements Driver
{
	public final BroadcastReceiver INTENT_ENDPOINT = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			if (IntentUploadResponse.ACTION.equals(intent.getAction()))
			{
				// Intent-Extras holen
				final String extra_handle = intent.getStringExtra(IntentUploadResponse.EXTRA_HANDLE);
				final long extra_startTime = intent.getLongExtra(IntentUploadResponse.EXTRA_START_TIME, Long.MIN_VALUE);
				final long extra_endTime = intent.getLongExtra(IntentUploadResponse.EXTRA_END_TIME, Long.MIN_VALUE);
				final long extra_transmitted = intent.getLongExtra(IntentUploadResponse.EXTRA_TRANSMITTED, Long.MIN_VALUE);
				final String extra_response = intent.getStringExtra(IntentUploadResponse.EXTRA_RESPONSE);
				final Serializable extra_exception = intent.getSerializableExtra(IntentUploadResponse.EXTRA_EXCEPTION);

				// Felder erstellen
				final String handle = extra_handle;
				final Date startTime = new Date(extra_startTime);
				final Date endTime = new Date(extra_endTime);
				final long transmitted = extra_transmitted;
				final String response = extra_response;
				final Throwable exception = (Throwable) extra_exception;

				onUploadResponse(handle, startTime, endTime, transmitted, response, exception);
			}
		}
	};

	protected abstract void onUploadResponse(String handle, Date startTime, Date endTime, long transmitted, String response, Throwable exception);

	public final ContextWrapper contextWrapper;

	public UploadResponseDriver(ContextWrapper contextWrapper)
	{
		this.contextWrapper = contextWrapper;
	}

	private boolean started = false;

	@Override
	public void start()
	{
		if (started) return;
		started = true;

		contextWrapper.registerReceiver(INTENT_ENDPOINT, new IntentFilter(IntentUploadResponse.ACTION));
	}

	@Override
	public void stop()
	{
		if (!started) return;
		started = false;

		contextWrapper.unregisterReceiver(INTENT_ENDPOINT);
	}
}

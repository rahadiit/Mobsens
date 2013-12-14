package mobsens.collector.drivers.messaging;

import java.sql.Date;

import mobsens.collector.intents.IntentLocationUpdate;
import mobsens.collector.pipeline.Driver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;

public abstract class LocationUpdateDriver implements Driver
{
	public final BroadcastReceiver INTENT_ENDPOINT = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			if (IntentLocationUpdate.ACTION.equals(intent.getAction()))
			{
				// Intent-Extras holen
				final long extra_time = intent.getLongExtra(IntentLocationUpdate.EXTRA_TIME, 0L);
				final double extra_latitude = intent.getDoubleExtra(IntentLocationUpdate.EXTRA_LATITUDE, 0.0);
				final double extra_longitude = intent.getDoubleExtra(IntentLocationUpdate.EXTRA_LONGITUDE, 0.0);
				final Float extra_accuracy = intent.hasExtra(IntentLocationUpdate.EXTRA_ACCURACY) ? intent.getFloatExtra(IntentLocationUpdate.EXTRA_ACCURACY, 0.0f) : null;
				final Double extra_altitude = intent.hasExtra(IntentLocationUpdate.EXTRA_ALTITUDE) ? intent.getDoubleExtra(IntentLocationUpdate.EXTRA_ALTITUDE, 0.0) : null;
				final Float extra_bearing = intent.hasExtra(IntentLocationUpdate.EXTRA_BEARING) ? intent.getFloatExtra(IntentLocationUpdate.EXTRA_BEARING, 0.0f) : null;
				final Float extra_speed = intent.hasExtra(IntentLocationUpdate.EXTRA_SPEED) ? intent.getFloatExtra(IntentLocationUpdate.EXTRA_SPEED, 0.0f) : null;

				// Felder erstellen
				final Date time = new Date(extra_time);
				final double latitude = extra_latitude;
				final double longitude = extra_longitude;
				final Float accuracy = extra_accuracy;
				final Double altitude = extra_altitude;
				final Float bearing = extra_bearing;
				final Float speed = extra_speed;

				// Methode aufrufen
				onLocationUpdate(time, latitude, longitude, accuracy, altitude, bearing, speed);
			}
		}
	};

	protected abstract void onLocationUpdate(Date time, double latitude, double longitude, Float accuracy, Double altitude, Float bearing, Float speed);

	public final ContextWrapper contextWrapper;

	public LocationUpdateDriver(ContextWrapper contextWrapper)
	{
		this.contextWrapper = contextWrapper;
	}

	private boolean started = false;

	@Override
	public void start()
	{
		if (started) return;
		started = true;

		contextWrapper.registerReceiver(INTENT_ENDPOINT, new IntentFilter(IntentLocationUpdate.ACTION));
	}

	@Override
	public void stop()
	{
		if (!started) return;
		started = false;

		contextWrapper.unregisterReceiver(INTENT_ENDPOINT);
	}
}

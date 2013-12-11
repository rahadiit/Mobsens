package mobsens.collector.drivers.locations;

import java.util.Date;

import android.content.Context;
import android.content.ContextWrapper;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationNoPSDriver extends LocationDriver
{
	public final LocationListener LOCATION_ENDPOINT = new LocationListener()
	{
		@Override
		public void onLocationChanged(Location location)
		{
			if (hasConsumers())
			{
				// Felder erstellen
				final Date time = new Date();
				final double latitude = location.getLatitude();
				final double longitude = location.getLongitude();
				final Float accuracy = location.hasAccuracy() ? location.getAccuracy() : null;
				final Double altitude = location.hasAltitude() ? location.getAltitude() : null;
				final Float bearing = location.hasBearing() ? location.getBearing() : null;
				final Float speed = location.hasSpeed() ? location.getSpeed() : null;

				// Item erstellen
				final LocationOutput item = new LocationOutput(time, latitude, longitude, accuracy, altitude, bearing, speed);

				// Item senden
				offer(item);
			}
		}

		@Override
		public void onProviderDisabled(String provider)
		{
		}

		@Override
		public void onProviderEnabled(String provider)
		{

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras)
		{
		}
	};

	/**
	 * Context, der die Services enthält
	 */
	public final ContextWrapper contextWrapper;

	/**
	 * Minimale Zeit zwischen den Updates
	 */
	public final long minTime;

	/**
	 * Minimale Distanz zwischen den Updates
	 */
	public final float minDistance;

	private boolean started;

	public LocationNoPSDriver(ContextWrapper contextWrapper, long minTime, float minDistance)
	{
		this.contextWrapper = contextWrapper;
		this.minTime = minTime;
		this.minDistance = minDistance;

		started = false;
	}

	@Override
	public void start()
	{
		if (started) return;
		started = true;

		if (!LocationPSDriver.isAvailable(contextWrapper))
		{
			LocationManager lm = (LocationManager) contextWrapper.getSystemService(Context.LOCATION_SERVICE);

			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, LOCATION_ENDPOINT);
		}
	}

	@Override
	public void stop()
	{
		if (!started) return;
		started = false;

		if (!LocationPSDriver.isAvailable(contextWrapper))
		{
			LocationManager lm = (LocationManager) contextWrapper.getSystemService(Context.LOCATION_SERVICE);

			lm.removeUpdates(LOCATION_ENDPOINT);
		}
	}

}

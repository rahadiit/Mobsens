package mobsens.collector.drivers.locations;

import java.util.Date;

import mobsens.collector.pipeline.BasicGenerator;
import mobsens.collector.pipeline.Driver;
import android.content.Context;
import android.content.ContextWrapper;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationDriver extends BasicGenerator<LocationOutput> implements Driver<LocationOutput>
{
	public final LocationListener LOCATION_ENDPOINT = new LocationListener()
	{
		@Override
		public void onLocationChanged(Location location)
		{
			if (consumer != null)
			{
				// Felder erstellen
				final Date time = new Date(location.getTime());
				final double latitude = location.getLatitude();
				final double longitude = location.getLongitude();
				final Float accuracy = location.hasAccuracy() ? location.getAccuracy() : null;
				final Double altitude = location.hasAltitude() ? location.getAltitude() : null;
				final Float bearing = location.hasBearing() ? location.getBearing() : null;
				final Float speed = location.hasSpeed() ? location.getSpeed() : null;

				// Item erstellen
				final LocationOutput item = new LocationOutput(time, latitude, longitude, accuracy, altitude, bearing, speed);

				// Item senden
				consumer.consume(item);
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

	/**
	 * True, wenn GPS verwendet wird
	 */
	public final boolean gps;

	/**
	 * True, wenn Netzwerk verwendet wird
	 */
	public final boolean network;

	/**
	 * True, wenn Passive verwendet wird
	 */
	public final boolean passive;

	public LocationDriver(ContextWrapper contextWrapper, long minTime, float minDistance, boolean gps, boolean network, boolean passive)
	{
		this.contextWrapper = contextWrapper;
		this.minTime = minTime;
		this.minDistance = minDistance;
		this.gps = gps;
		this.network = network;
		this.passive = passive;
	}

	private boolean started = false;

	@Override
	public void start()
	{
		if (started) return;
		started = true;

		LocationManager lm = (LocationManager) contextWrapper.getSystemService(Context.LOCATION_SERVICE);

		if (gps) lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, LOCATION_ENDPOINT);
		if (network) lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, LOCATION_ENDPOINT);
		if (passive) lm.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, minTime, minDistance, LOCATION_ENDPOINT);
	}

	@Override
	public void stop()
	{
		if (!started) return;
		started = false;

		LocationManager lm = (LocationManager) contextWrapper.getSystemService(Context.LOCATION_SERVICE);

		lm.removeUpdates(LOCATION_ENDPOINT);
	}

}

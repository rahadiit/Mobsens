package mobsens.collector.drivers.locations;

import java.util.Date;

import android.content.ContextWrapper;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

public class LocationPSDriver extends LocationDriver
{
	public static boolean isAvailable(ContextWrapper contextWrapper)
	{
		return GooglePlayServicesUtil.isGooglePlayServicesAvailable(contextWrapper) == ConnectionResult.SUCCESS;
	}

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
	};

	private final ConnectionCallbacks CONNECTION_ENDPOINT = new ConnectionCallbacks()
	{
		@Override
		public void onDisconnected()
		{
		}

		@Override
		public void onConnected(Bundle arg0)
		{
			if (requesting)
			{
				requesting = false;

				locationClient.requestLocationUpdates(locationRequest, LOCATION_ENDPOINT);
			}
		}
	};

	private final OnConnectionFailedListener CONNECTION_FAILED_ENDPOINT = new OnConnectionFailedListener()
	{
		@Override
		public void onConnectionFailed(ConnectionResult arg0)
		{
		}
	};

	/**
	 * Context, der die Services enthält
	 */
	public final ContextWrapper contextWrapper;

	public final long minTime;

	public final float minDistance;

	private LocationClient locationClient;

	private LocationRequest locationRequest;

	private boolean started;

	private boolean requesting;

	public LocationPSDriver(ContextWrapper contextWrapper, long minTime, float minDistance)
	{
		this.contextWrapper = contextWrapper;
		this.minTime = minTime;
		this.minDistance = minDistance;

		locationClient = null;
		locationRequest = null;

		started = false;
		requesting = false;
	}

	@Override
	public void start()
	{
		if (started) return;
		started = true;

		if (isAvailable(contextWrapper))
		{
			if (locationClient == null)
			{
				locationClient = new LocationClient(contextWrapper, CONNECTION_ENDPOINT, CONNECTION_FAILED_ENDPOINT);
				locationClient.connect();

				locationRequest = new LocationRequest();
				locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
				locationRequest.setInterval(minTime);
				locationRequest.setFastestInterval(minTime);
				locationRequest.setSmallestDisplacement(minDistance);
			}

			if (locationClient.isConnected())
			{
				locationClient.requestLocationUpdates(locationRequest, LOCATION_ENDPOINT);
			}
			else
			{
				requesting = true;
			}
		}
	}

	@Override
	public void stop()
	{
		if (!started) return;
		started = false;

		if (isAvailable(contextWrapper))
		{
			if (locationClient.isConnected())
			{
				locationClient.removeLocationUpdates(LOCATION_ENDPOINT);
			}
			else
			{
				requesting = false;
			}
		}
	}
}

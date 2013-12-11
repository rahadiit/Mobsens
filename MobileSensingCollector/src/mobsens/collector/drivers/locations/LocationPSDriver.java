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

	private final LocationClient locationClient;

	private final LocationRequest locationRequest;

	public LocationPSDriver(ContextWrapper contextWrapper, long minTime, float minDistance)
	{
		this.contextWrapper = contextWrapper;

		locationClient = new LocationClient(contextWrapper, CONNECTION_ENDPOINT, CONNECTION_FAILED_ENDPOINT);
		locationClient.connect();

		locationRequest = new LocationRequest();
		locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		locationRequest.setInterval(minTime);
		locationRequest.setFastestInterval(minTime);
		locationRequest.setSmallestDisplacement(minDistance);
	}

	@Override
	public void start()
	{
		if (isAvailable(contextWrapper))
		{
			locationClient.requestLocationUpdates(locationRequest, LOCATION_ENDPOINT);
		}
	}

	@Override
	public void stop()
	{
		if (isAvailable(contextWrapper))
		{
			locationClient.removeLocationUpdates(LOCATION_ENDPOINT);
		}
	}

}

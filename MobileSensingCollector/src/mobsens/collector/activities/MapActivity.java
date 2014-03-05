package mobsens.collector.activities;

import java.sql.Date;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import mobsens.collector.Collector;
import mobsens.collector.CollectorIPC;
import mobsens.collector.R;
import mobsens.collector.communications.ConnectingActivity;
import mobsens.collector.drivers.messaging.LocationUpdateDriver;
import mobsens.collector.intents.IntentStopCollector;
import mobsens.collector.util.Logging;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.TextView;

public class MapActivity extends ConnectingActivity
{
	private final LocationUpdateDriver locationUpdateDriver;

	private CollectorIPC collectorIPC;

	private MapView mapPosition;

	private TextView mapSpeed;

	private TextView mapDistance;

	public MapActivity()
	{
		super(Collector.class);

		locationUpdateDriver = new LocationUpdateDriver(this)
		{
			@Override
			protected void onLocationUpdate(Date time, double latitude, double longitude, Float accuracy, Double altitude, Float bearing, Float speed)
			{
				if (mapPosition != null)
				{
					mapPosition.getController().animateTo(new GeoPoint(latitude, longitude));

					if (bearing != null)
					{
						mapPosition.setMapOrientation(bearing);
					}
				}

				if (mapSpeed != null)
				{
					if (speed == null)
					{
						mapSpeed.setText("--- km/h");

					}
					else
					{
						mapSpeed.setText((speed * 3600.0f / 1000.0f) + "km/h");
					}
				}

				if (mapDistance != null)
				{
					mapDistance.setText("--- km");
				}
			}
		};
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		// get a handle on the XML element
		mapPosition = (MapView) findViewById(R.id.map_position);
		mapSpeed = (TextView) findViewById(R.id.map_speed);
		mapDistance = (TextView) findViewById(R.id.map_distance);

		mapPosition.setClickable(true);
		mapPosition.setBuiltInZoomControls(true);
		mapPosition.getController().setZoom(16);

		locationUpdateDriver.start();
	}

	@Override
	protected void onConnected(IBinder service)
	{
		collectorIPC = CollectorIPC.Stub.asInterface(service);
	}

	@Override
	protected void onDisconnected()
	{
		collectorIPC = null;
	}

	public void stop(View v)
	{
		try
		{
			if (collectorIPC.isCollecting())
			{
				IntentStopCollector.sendBroadcast(this);
			}
		}
		catch (RemoteException e)
		{
			Logging.log(this, e);
		}

		locationUpdateDriver.stop();

		Intent intent = new Intent(this, RatingActivity.class);
		startActivity(intent);
		finish();
	}

}

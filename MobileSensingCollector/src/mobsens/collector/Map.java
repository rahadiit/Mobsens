package mobsens.collector;

import java.sql.Date;
import mobsens.collector.communications.ConnectingActivity;
import mobsens.collector.drivers.messaging.CollectorStatusDriver;
import mobsens.collector.drivers.messaging.LocationUpdateDriver;
import mobsens.collector.intents.IntentStopCollector;
import mobsens.collector.maps.ResourceOverlay;
import mobsens.collector.util.Logging;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Map extends ConnectingActivity
{
	private final LocationUpdateDriver locationUpdateDriver;

	private final CollectorStatusDriver collectorStatusDriver;

	private CollectorIPC collectorIPC;

	private MapView mapViewMapPosition;

	private TextView textViewMapSpeed;

	private Button buttonMapStop;

	private ResourceProxy mResourceProxy;

	public Map()
	{
		super(Collector.class);

		locationUpdateDriver = new LocationUpdateDriver(this)
		{
			@Override
			protected void onLocationUpdate(Date time, double latitude, double longitude, Float accuracy, Double altitude, Float bearing, Float speed)
			{
				Log.d("Map", "Updated location: " + latitude + ", " + longitude);

				if (mapViewMapPosition != null)
				{
					mapViewMapPosition.getController().animateTo(new GeoPoint(latitude, longitude));
					locationOverlay.setGeoPoint(new GeoPoint(latitude, longitude));

					if (bearing != null)
					{
						mapViewMapPosition.setMapOrientation(bearing);
					}
				}

				if (textViewMapSpeed != null)
				{
					if (speed == null)
					{
						textViewMapSpeed.setText("Unknown speed");

					}
					else
					{
						textViewMapSpeed.setText(speed + "m/s, " + (speed * 3600.0f / 1000.0f) + "km/h");
					}
				}
			}
		};

		collectorStatusDriver = new CollectorStatusDriver(this)
		{

			@Override
			protected void onCollectorStatus(boolean status)
			{
				if (status)
				{
					buttonMapStop.setEnabled(true);
				}
				else
				{
					buttonMapStop.setEnabled(false);
				}
			}
		};

		collectorIPC = null;
	}

	private ResourceOverlay locationOverlay;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// old
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		// get a handle on the XML element
		mapViewMapPosition = (MapView) findViewById(R.id.map_position);

		// Map parameters
		mapViewMapPosition.setClickable(true);
		mapViewMapPosition.setBuiltInZoomControls(true);
		mapViewMapPosition.getController().setZoom(16);

		mResourceProxy = new DefaultResourceProxyImpl(getApplicationContext());

		textViewMapSpeed = (TextView) findViewById(R.id.map_speed);
		buttonMapStop = (Button) findViewById(R.id.map_stop);

		locationOverlay = new ResourceOverlay(this);
		locationOverlay.setResource(R.drawable.ic_launcher);

		mapViewMapPosition.getOverlayManager().add(locationOverlay);

		buttonMapStop.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (collectorIPC != null)
				{
					try
					{
						if (collectorIPC.isCollecting())
						{
							IntentStopCollector.sendBroadcast(Map.this);
						}
					}
					catch (RemoteException e)
					{
						Logging.log(Map.this, e);
					}
				}
			}
		});

		locationUpdateDriver.start();
		collectorStatusDriver.start();
	}

	@Override
	protected void onDestroy()
	{
		locationUpdateDriver.stop();
		collectorStatusDriver.stop();

		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	@Override
	protected void onConnected(IBinder service)
	{
		collectorIPC = CollectorIPC.Stub.asInterface(service);

		try
		{
			if (collectorIPC.isCollecting())
			{
				buttonMapStop.setEnabled(true);
			}
			else
			{
				buttonMapStop.setEnabled(false);
			}
		}
		catch (RemoteException e)
		{
			Logging.log(this, e);
		}

		Logging.log(this, "Controller", "Connected", null);
	}

	@Override
	protected void onDisconnected()
	{
		collectorIPC = null;

		Logging.log(this, "Controller", "Disconnected", null);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);

		outState.putDouble(R.id.map_position + "latitude", mapViewMapPosition.getMapCenter().getLatitude());
		outState.putDouble(R.id.map_position + "longitude", mapViewMapPosition.getMapCenter().getLongitude());
		outState.putFloat(R.id.map_position + "orientation", mapViewMapPosition.getMapOrientation());
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);

		mapViewMapPosition.getController().setCenter(
				new GeoPoint(savedInstanceState.getDouble(R.id.map_position + "latitude"), savedInstanceState.getDouble(R.id.map_position + "longitude")));
		mapViewMapPosition.setMapOrientation(savedInstanceState.getFloat(R.id.map_position + "orientation"));
	}

}

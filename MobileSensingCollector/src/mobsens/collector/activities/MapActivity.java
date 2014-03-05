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

	private MapView mapViewMapPosition;

	private TextView textViewMapSpeed;

	private TextView textViewMapDistance;

	public MapActivity()
	{
		super(Collector.class);

		locationUpdateDriver = new LocationUpdateDriver(this)
		{
			@Override
			protected void onLocationUpdate(Date time, double latitude, double longitude, Float accuracy, Double altitude, Float bearing, Float speed)
			{
				if (mapViewMapPosition != null)
				{
					mapViewMapPosition.getController().animateTo(new GeoPoint(latitude, longitude));

					if (bearing != null)
					{
						mapViewMapPosition.setMapOrientation(bearing);
					}
				}

				if (textViewMapSpeed != null)
				{
					if (speed == null)
					{
						textViewMapSpeed.setText("--- km/h");

					}
					else
					{
						textViewMapSpeed.setText((speed * 3600.0f / 1000.0f) + "km/h");
					}
				}

				if (textViewMapDistance != null)
				{
					textViewMapDistance.setText("--- km");
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
		mapViewMapPosition = (MapView) findViewById(R.id.map_position);
		textViewMapSpeed = (TextView) findViewById(R.id.textView1);
		textViewMapDistance = (TextView) findViewById(R.id.textView2);

		mapViewMapPosition.setClickable(true);
		mapViewMapPosition.setBuiltInZoomControls(true);
		mapViewMapPosition.getController().setZoom(16);

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

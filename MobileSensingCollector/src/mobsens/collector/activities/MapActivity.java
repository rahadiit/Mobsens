package mobsens.collector.activities;

import java.util.Date;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.PathOverlay;

import mobsens.collector.Collector;
import mobsens.collector.CollectorIPC;
import mobsens.collector.R;
import mobsens.collector.communications.ConnectingActivity;
import mobsens.collector.drivers.messaging.LocationUpdateDriver;
import mobsens.collector.intents.IntentStopCollector;
import mobsens.collector.util.Logging;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.TextView;
import static mobsens.collector.config.Config.*;
import static mobsens.collector.config.Constants.*;

public class MapActivity extends ConnectingActivity
{
	private final LocationUpdateDriver locationUpdateDriver;

	private CollectorIPC collectorIPC;

	private MapView mapPosition;

	private TextView mapSpeed;

	private TextView mapDistance;

	private PathOverlay pathOverlay;

	private Long lastTime = null;

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
				}

				if (pathOverlay != null)
				{
					if (lastTime == null || (time.getTime() - lastTime) > MAP_POINT_DISTANCE)
					{
						lastTime = time.getTime();

						pathOverlay.addPoint(new GeoPoint(latitude, longitude));
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
						mapSpeed.setText((speed * SECONDS_PER_HOUR / METERS_PER_KILOMETER) + "km/h");
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

		// path
		pathOverlay = new PathOverlay(Color.BLUE, this);
		pathOverlay.getPaint().setStrokeWidth(PATH_STROKE_WIDTH);
		pathOverlay.getPaint().setStrokeCap(Cap.ROUND);
		pathOverlay.getPaint().setStrokeJoin(Join.ROUND);
		pathOverlay.getPaint().setStrokeMiter(10.0f);
		mapPosition.getOverlayManager().add(pathOverlay);

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

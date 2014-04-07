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
import mobsens.collector.maps.ResourceOverlay;
import mobsens.collector.util.Logging;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import static mobsens.collector.config.Config.*;
import static mobsens.collector.config.Constants.*;

public class MapActivity extends ConnectingActivity
{
	public static final String EXTRA_DISTANCE = "distance";

	public static final String EXTRA_TOTAL_TIME = "totalTime";

	public static final String EXTRA_MAX_SPEED = "maxSpeed";

	public static final String EXTRA_AVG_SPEED = "avgSpeed";

	private final LocationUpdateDriver locationUpdateDriver;

	private CollectorIPC collectorIPC;

	private MapView mapPosition;

	private TextView mapSpeed;

	private TextView mapDistance;

	private PathOverlay pathOverlay;

	private ResourceOverlay locationOverlay;

	private Long lastTime = null;

	private Double lastLatitude = null;

	private Double lastLongitude = null;

	private double totalDistance = 0.0;

	private Float maxSpeed = null;

	private Date startTime;

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);

		outState.putLong("lastTime", lastTime != null ? lastTime.longValue() : Long.MIN_VALUE);
		outState.putDouble("lastLatitude", lastLatitude != null ? lastLatitude.doubleValue() : Double.NaN);
		outState.putDouble("lastLongitude", lastLongitude != null ? lastLongitude.doubleValue() : Double.NaN);
		outState.putDouble("totalDistance", totalDistance);
		outState.putFloat("maxSpeed", maxSpeed != null ? maxSpeed.floatValue() : Float.NaN);
		outState.putLong("startTime", startTime != null ? startTime.getTime() : Long.MIN_VALUE);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		long rawLastTime = savedInstanceState.getLong("lastTime", Long.MIN_VALUE);
		double rawLastLatitude = savedInstanceState.getDouble("lastLatitude", Double.NaN);
		double rawLastLongitude = savedInstanceState.getDouble("lastLongitude", Double.NaN);
		double rawTotalDistance = savedInstanceState.getDouble("totalDistance", 0.0);
		float rawMaxSpeed = savedInstanceState.getFloat("maxSpeed", Float.NaN);
		long rawStartTime = savedInstanceState.getLong("startTime", Long.MIN_VALUE);

		lastTime = rawLastTime != Long.MIN_VALUE ? rawLastTime : null;
		lastLatitude = rawLastLatitude != Double.NaN ? rawLastLatitude : null;
		lastLongitude = rawLastLongitude != Double.NaN ? rawLastLongitude : null;
		totalDistance = rawTotalDistance;
		maxSpeed = rawMaxSpeed != Float.NaN ? rawMaxSpeed : null;
		startTime = rawStartTime != Long.MIN_VALUE ? new Date(rawStartTime) : null;

		super.onRestoreInstanceState(savedInstanceState);
	}

	public MapActivity()
	{
		super(Collector.class);

		locationUpdateDriver = new LocationUpdateDriver(this)
		{
			@Override
			protected void onLocationUpdate(Date time, double latitude, double longitude, Float accuracy, Double altitude, Float bearing, Float speed)
			{
				if (lastLatitude == null || lastLongitude == null)
				{
					lastLatitude = latitude;
					lastLongitude = longitude;
				}
				else
				{
					float[] rb = new float[3];
					Location.distanceBetween(lastLatitude, lastLongitude, latitude, longitude, rb);
					totalDistance += rb[0];
				}

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

				if (locationOverlay != null)
				{
					locationOverlay.setGeoPoint(new GeoPoint(latitude, longitude));
				}

				if (mapSpeed != null)
				{
					if (speed == null)
					{
						mapSpeed.setText("--- km/h");

					}
					else
					{
						if (maxSpeed == null || maxSpeed < speed) maxSpeed = speed;

						mapSpeed.setText((speed * SECONDS_PER_HOUR / METERS_PER_KILOMETER) + "km/h");
					}
				}

				if (mapDistance != null)
				{
					mapDistance.setText(Math.round(totalDistance) + "m");
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

		locationOverlay = new ResourceOverlay(this);
		locationOverlay.setResource(R.drawable.marker);
		mapPosition.getOverlayManager().add(locationOverlay);

		mapPosition.setClickable(true);
		mapPosition.setBuiltInZoomControls(true);
		mapPosition.getController().setZoom(16);

		locationUpdateDriver.start();
		startTime = new Date();
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
		double seconds = (new Date().getTime() - startTime.getTime()) / 1000.0;

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
		intent.putExtra(EXTRA_MAX_SPEED, maxSpeed == null ? Float.NaN : (float) maxSpeed);
		intent.putExtra(EXTRA_TOTAL_TIME, seconds);
		intent.putExtra(EXTRA_DISTANCE, totalDistance);
		intent.putExtra(EXTRA_AVG_SPEED, (float) ((totalDistance / seconds) * SECONDS_PER_HOUR / METERS_PER_KILOMETER));
		startActivity(intent);
		finish();
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		switch (item.getItemId())
		{

		case R.id.action_tag:
			Intent intent = new Intent(this, FasttagActivity.class);
			startActivity(intent);
			return true;

		default:
			return super.onMenuItemSelected(featureId, item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	@Override
	public void onBackPressed()
	{
	}
}

package mobsens.collector.intents;

import java.util.Date;

import android.content.ContextWrapper;
import android.content.Intent;

public class IntentLocationUpdate
{
	public static final String ACTION = "mobsens.ACTION_LOCATION_UPDATE";

	public static final String EXTRA_TIME = "time";

	public static final String EXTRA_LATITUDE = "latitude";

	public static final String EXTRA_LONGITUDE = "longitude";

	public static final String EXTRA_ACCURACY = "accuracy";

	public static final String EXTRA_ALTITUDE = "altitude";

	public static final String EXTRA_BEARING = "bearing";

	public static final String EXTRA_SPEED = "speed";

	public static void sendBroadcast(ContextWrapper contextWrapper, Date time, double latitude, double longitude, Float accuracy, Double altitude, Float bearing, Float speed)
	{
		final Intent intent = new Intent(ACTION);

		intent.putExtra(EXTRA_TIME, time.getTime());
		intent.putExtra(EXTRA_LATITUDE, latitude);
		intent.putExtra(EXTRA_LONGITUDE, longitude);

		if (accuracy != null)
		{
			intent.putExtra(EXTRA_ACCURACY, accuracy);
		}

		if (altitude != null)
		{
			intent.putExtra(EXTRA_ALTITUDE, altitude);
		}

		if (bearing != null)
		{
			intent.putExtra(EXTRA_BEARING, bearing);
		}

		if (speed != null)
		{
			intent.putExtra(EXTRA_SPEED, speed);
		}

		contextWrapper.sendBroadcast(intent);
	}

}

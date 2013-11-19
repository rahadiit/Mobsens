package mobsens.collector.intents;

import java.util.Date;

import android.content.ContextWrapper;
import android.content.Intent;

public class IntentAnnotation
{
	public static final String ACTION = "mobsens.ACTION_ANNOTATION";

	public static final String EXTRA_TIME = "time";

	public static final String EXTRA_VALUE = "value";

	public static void sendBroadcast(ContextWrapper contextWrapper, Date time, String value)
	{
		final Intent intent = new Intent(ACTION);

		intent.putExtra(EXTRA_TIME, time.getTime());
		intent.putExtra(EXTRA_VALUE, value);

		contextWrapper.sendBroadcast(intent);
	}

}

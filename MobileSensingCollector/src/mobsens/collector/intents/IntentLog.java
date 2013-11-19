package mobsens.collector.intents;

import java.util.Date;

import android.content.ContextWrapper;
import android.content.Intent;

public class IntentLog
{
	public static final String ACTION = "mobsens.ACTION_LOG";

	public static final String EXTRA_TIME = "time";

	public static final String EXTRA_TITLE = "title";

	public static final String EXTRA_SUBTITLE = "subtitle";

	public static final String EXTRA_DESCRIPTION = "description";

	public static void sendBroadcast(ContextWrapper contextWrapper, Date time, String title, String subtitle, String description)
	{
		final Intent intent = new Intent(ACTION);

		intent.putExtra(EXTRA_TIME, time.getTime());
		intent.putExtra(EXTRA_TITLE, title);
		intent.putExtra(EXTRA_SUBTITLE, subtitle);
		intent.putExtra(EXTRA_DESCRIPTION, description);

		contextWrapper.sendBroadcast(intent);
	}

}

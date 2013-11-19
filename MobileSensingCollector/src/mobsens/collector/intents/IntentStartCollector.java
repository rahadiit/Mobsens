package mobsens.collector.intents;

import android.content.ContextWrapper;
import android.content.Intent;

public class IntentStartCollector
{
	public static final String ACTION = "mobsens.ACTION_START_COLLECTOR";

	public static final String EXTRA_TITLE = "title";

	public static void sendBroadcast(ContextWrapper contextWrapper, String title)
	{
		final Intent intent = new Intent(ACTION);

		intent.putExtra(EXTRA_TITLE, title);

		contextWrapper.sendBroadcast(intent);
	}

}

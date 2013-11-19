package mobsens.collector.intents;

import android.content.ContextWrapper;
import android.content.Intent;

public class IntentQuitCollector
{
	public static final String ACTION = "mobsens.ACTION_QUIT_COLLECTOR";

	public static void sendBroadcast(ContextWrapper contextWrapper)
	{
		final Intent intent = new Intent(ACTION);

		contextWrapper.sendBroadcast(intent);
	}
}

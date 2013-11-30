package mobsens.collector.intents;

import android.content.ContextWrapper;
import android.content.Intent;

public class IntentCollectorStatus
{
	public static final String ACTION = "mobsens.ACTION_COLLECTOR_STATUS";
	
	public static final String EXTRA_STATUS = "status";

	public static void sendBroadcast(ContextWrapper contextWrapper, boolean status)
	{
		final Intent intent = new Intent(ACTION);
		
		intent.putExtra(EXTRA_STATUS, status);

		contextWrapper.sendBroadcast(intent);
	}

}

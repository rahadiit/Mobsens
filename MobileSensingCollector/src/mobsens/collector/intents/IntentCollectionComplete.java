package mobsens.collector.intents;

import android.content.ContextWrapper;
import android.content.Intent;

public class IntentCollectionComplete
{
	public static final String ACTION = "mobsens.ACTION_COLLECTION_COMPLETE";

	public static final String EXTRA_LOCATION = "location";

	public static void sendBroadcast(ContextWrapper contextWrapper, String location)
	{
		final Intent intent = new Intent(ACTION);

		intent.putExtra(EXTRA_LOCATION, location);

		contextWrapper.sendBroadcast(intent);
	}

}

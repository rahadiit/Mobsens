package mobsens.collector.intents;

import java.util.Date;

import android.content.ContextWrapper;
import android.content.Intent;

public class IntentUploadResponse
{
	public static final String ACTION = "mobsens.ACTION_UPLOAD_RESPONSE";

	public static final String EXTRA_HANDLE = "handle";

	public static final String EXTRA_START_TIME = "startTime";

	public static final String EXTRA_END_TIME = "endTime";

	public static final String EXTRA_TRANSMITTED = "transmitted";

	public static final String EXTRA_RESPONSE = "response";

	public static final String EXTRA_EXCEPTION = "exception";

	public static void sendBroadcast(ContextWrapper contextWrapper, String handle, Date startTime, Date endTime, long transmitted, String response, Throwable exception)
	{
		final Intent intent = new Intent(ACTION);

		intent.putExtra(EXTRA_HANDLE, handle);
		intent.putExtra(EXTRA_START_TIME, startTime.getTime());
		intent.putExtra(EXTRA_END_TIME, endTime.getTime());
		intent.putExtra(EXTRA_TRANSMITTED, transmitted);
		intent.putExtra(EXTRA_RESPONSE, response);
		intent.putExtra(EXTRA_EXCEPTION, exception);

		contextWrapper.sendBroadcast(intent);
	}
}

package mobsens.collector.intents;

import java.io.File;

import mobsens.collector.Uploader;
import android.content.ContextWrapper;
import android.content.Intent;

public class IntentUpload
{
	public static final String EXTRA_HANDLE = "handle";

	public static final String EXTRA_DESTINATION = "destination";

	public static final String EXTRA_FILE = "file";

	public static final String EXTRA_CONTENT_TYPE = "contentType";

	public static final String EXTRA_ACCEPT_TYPE = "acceptType";

	public static final String EXTRA_CONSUME = "consume";

	public static void startService(ContextWrapper contextWrapper, String handle, String destination, File file, String contentType, String acceptType,
			boolean consume)
	{
		final Intent intent = new Intent(contextWrapper, Uploader.class);

		intent.putExtra(EXTRA_HANDLE, handle);
		intent.putExtra(EXTRA_DESTINATION, destination);
		intent.putExtra(EXTRA_FILE, file);
		intent.putExtra(EXTRA_CONTENT_TYPE, contentType);
		intent.putExtra(EXTRA_ACCEPT_TYPE, acceptType);
		intent.putExtra(EXTRA_CONSUME, consume);

		contextWrapper.startService(intent);
	}
}

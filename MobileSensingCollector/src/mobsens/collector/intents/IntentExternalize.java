package mobsens.collector.intents;

import java.io.File;

import mobsens.collector.Externalizer;
import android.content.ContextWrapper;
import android.content.Intent;

public class IntentExternalize
{
	public static final String EXTRA_HANDLE = "handle";

	public static final String EXTRA_SOURCE = "source";

	public static final String EXTRA_TARGET = "target";

	public static void startService(ContextWrapper contextWrapper, String handle, File source, File target)
	{
		final Intent intent = new Intent(contextWrapper, Externalizer.class);

		intent.putExtra(EXTRA_HANDLE, handle);
		intent.putExtra(EXTRA_SOURCE, source);
		intent.putExtra(EXTRA_TARGET, target);

		contextWrapper.startService(intent);
	}
}

package mobsens.collector.util;

import java.util.Arrays;
import java.util.Date;

import mobsens.collector.intents.IntentLog;
import android.content.ContextWrapper;
import android.util.Log;

public class Logging
{
	public static void log(ContextWrapper contextWrapper, String title, String subtitle, String description)
	{
		log(contextWrapper, Log.INFO, new Date(), title, subtitle, description);
	}

	public static void log(ContextWrapper contextWrapper, int priority, String title, String subtitle, String description)
	{
		log(contextWrapper, priority, new Date(), title, subtitle, description);
	}

	public static void log(ContextWrapper contextWrapper, Date time, String title, String subtitle, String description)
	{
		log(contextWrapper, Log.INFO, time, title, subtitle, description);
	}

	public static void log(ContextWrapper contextWrapper, int priority, Date time, String title, String subtitle, String description)
	{
		if (description == null)
		{
			Log.println(priority, contextWrapper.getClass().getSimpleName(), String.format("[%tT] %s/%s", time, title, subtitle));

		}
		else
		{
			Log.println(priority, contextWrapper.getClass().getSimpleName(), String.format("[%tT] %s/%s - %s", time, title, subtitle, description));
		}

		IntentLog.sendBroadcast(contextWrapper, time, title, subtitle, description);
	}

	public static void log(ContextWrapper contextWrapper, Throwable throwable)
	{
		log(contextWrapper, new Date(), throwable);
	}

	public static void log(ContextWrapper contextWrapper, Date time, Throwable throwable)
	{
		log(contextWrapper, Log.ERROR, time, throwable.getClass().getSimpleName(), throwable.getLocalizedMessage(), Arrays.toString(throwable.getStackTrace()));
	}
}

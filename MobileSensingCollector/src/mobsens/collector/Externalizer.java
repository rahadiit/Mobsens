package mobsens.collector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import mobsens.collector.intents.IntentExternalize;
import mobsens.collector.intents.IntentExternalizeResponse;
import mobsens.collector.util.Logging;
import android.app.IntentService;
import android.content.Intent;

public class Externalizer extends IntentService
{
	private static final int CACHE_LENGTH = 8192;

	private static final byte[] CACHE = new byte[CACHE_LENGTH];

	public Externalizer()
	{
		super("Externalizer");
	}

	@Override
	protected void onHandleIntent(Intent request)
	{
		// Extras auslesen
		final String extra_handle = request.getStringExtra(IntentExternalize.EXTRA_HANDLE);
		final Serializable extra_source = request.getSerializableExtra(IntentExternalize.EXTRA_SOURCE);
		final Serializable extra_target = request.getSerializableExtra(IntentExternalize.EXTRA_TARGET);

		// Parameter erstellen
		final String handle = extra_handle;
		final File source = (File) extra_source;
		final File target = (File) extra_target;

		Logging.log(this, "Externalzier", handle, source + " -> " + target);

		// Startzeit markieren
		final Date startTimeMarker = new Date();

		try
		{
			if (!source.exists())
			{
				// Endzeit und gesendete Größe markieren
				final Date endTimeMarker = new Date();
				final long transmissionMarker = 0;

				Logging.log(this, "Externalzier", handle, "Source not found");

				// Wenn Datei nicht existiert, als Exception melden
				IntentExternalizeResponse.sendBroadcast(this, handle, startTimeMarker, endTimeMarker, transmissionMarker, new FileNotFoundException(source + " not found"));
			}
			else
			{
				// Übergeordnete Ordner erstellen
				target.getParentFile().mkdirs();

				FileOutputStream fileOutputStream = new FileOutputStream(target);
				FileInputStream fileInputStream = new FileInputStream(source);

				long transmissionMarker = 0;
				int read;
				while ((read = fileInputStream.read(CACHE)) > 0)
				{
					fileOutputStream.write(CACHE, 0, read);

					transmissionMarker += read;
				}

				fileInputStream.close();
				fileOutputStream.close();

				// Endzeit markieren
				final Date endTimeMarker = new Date();

				Logging.log(this, "Externalzier", handle, "Done");

				// Rückgabe weiterleiten
				IntentExternalizeResponse.sendBroadcast(this, handle, startTimeMarker, endTimeMarker, transmissionMarker, null);
			}
		}
		catch (IOException e)
		{
			// Endzeit und gesendete Größe markieren
			final Date endTimeMarker = new Date();
			final long transmissionMarker = 0;

			Logging.log(this, e);

			IntentExternalizeResponse.sendBroadcast(this, handle, startTimeMarker, endTimeMarker, transmissionMarker, e);
		}
	}
}

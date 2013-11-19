package mobsens.collector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import mobsens.collector.intents.IntentUpload;
import mobsens.collector.intents.IntentUploadResponse;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class Uploader extends IntentService
{
	public Uploader()
	{
		super("Uploader");
	}

	@Override
	protected void onHandleIntent(Intent request)
	{
		// Extras auslesen
		final String extra_handle = request.getStringExtra(IntentUpload.EXTRA_HANDLE);
		final String extra_destination = request.getStringExtra(IntentUpload.EXTRA_DESTINATION);
		final Serializable extra_file = request.getSerializableExtra(IntentUpload.EXTRA_FILE);
		final String extra_contentType = request.getStringExtra(IntentUpload.EXTRA_CONTENT_TYPE);
		final String extra_acceptType = request.getStringExtra(IntentUpload.EXTRA_ACCEPT_TYPE);
		final boolean extra_consume = request.getBooleanExtra(IntentUpload.EXTRA_CONSUME, true);

		// Parameter erstellen
		final String handle = extra_handle;
		final String destination = extra_destination;
		final File file = (File) extra_file;
		final String contentType = extra_contentType;
		final String acceptType = extra_acceptType;
		final boolean consume = extra_consume;

		Log.d("Uploader", "<" + handle + ">" + destination + " from " + file);

		// Startzeit markieren
		final Date startTimeMarker = new Date();

		try
		{
			if (!file.exists())
			{
				// Endzeit und gesendete Größe markieren
				final Date endTimeMarker = new Date();
				final long transmissionMarker = 0;

				Log.d("Uploader", "<" + handle + ">" + file + " not found");

				// Wenn Datei nicht existiert, als Exception melden
				IntentUploadResponse.sendBroadcast(this, handle, startTimeMarker, endTimeMarker, transmissionMarker, null, new FileNotFoundException(file + " not found"));
			}
			else
			{
				// Client erstellen und konfigurieren
				final HttpClient httpClient = new DefaultHttpClient();
				httpClient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

				// Post erstellen und konfigurieren
				final HttpPost httpPost = new HttpPost(destination);
				httpPost.setHeader("Accept", acceptType);
				httpPost.setHeader("Expect", "100-continue");

				// Zu sendendes Entity erstellen und konfigurieren
				final FileEntity requestEntity = new FileEntity(file, contentType);

				// POST mit Entity befüllen
				httpPost.setEntity(requestEntity);

				// POST ausführen
				final HttpResponse response = httpClient.execute(httpPost);

				// Rückgabe lesen
				final String responseString = EntityUtils.toString(response.getEntity());

				// Endzeit und gesendete Größe markieren
				final Date endTimeMarker = new Date();
				final long transmissionMarker = 0;

				Log.d("Uploader", "<" + handle + "> done");

				// Rückgabe weiterleiten
				IntentUploadResponse.sendBroadcast(this, handle, startTimeMarker, endTimeMarker, transmissionMarker, responseString, null);

				// Klienten beenden
				httpClient.getConnectionManager().shutdown();

				// Optional hochgeladene Datei löschen
				if (consume)
				{
					file.delete();
				}
			}
		}
		catch (IOException e)
		{
			// Endzeit und gesendete Größe markieren
			final Date endTimeMarker = new Date();
			final long transmissionMarker = 0;

			Log.d("Uploader", "<" + handle + "> " + e.getMessage());
			IntentUploadResponse.sendBroadcast(this, handle, startTimeMarker, endTimeMarker, transmissionMarker, null, e);
		}
	}
}

package mobsens.collector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import mobsens.collector.intents.IntentUpload;
import mobsens.collector.intents.IntentUploadResponse;
import mobsens.collector.util.Logging;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONStringer;

import android.app.IntentService;
import android.content.Intent;

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
		final String extra_login = request.getStringExtra(IntentUpload.EXTRA_LOGIN);
		final String extra_login_user = request.getStringExtra(IntentUpload.EXTRA_LOGIN_USER);
		final String extra_login_pass = request.getStringExtra(IntentUpload.EXTRA_LOGIN_PASS);
		final String extra_destination = request.getStringExtra(IntentUpload.EXTRA_DESTINATION);
		final Serializable extra_file = request.getSerializableExtra(IntentUpload.EXTRA_FILE);
		final String extra_contentType = request.getStringExtra(IntentUpload.EXTRA_CONTENT_TYPE);
		final String extra_acceptType = request.getStringExtra(IntentUpload.EXTRA_ACCEPT_TYPE);

		// Parameter erstellen
		final String handle = extra_handle;
		final String login = extra_login;
		final String login_user = extra_login_user;
		final String login_pass = extra_login_pass;
		final String destination = extra_destination;
		final File file = (File) extra_file;
		final String contentType = extra_contentType;
		final String acceptType = extra_acceptType;

		Logging.log(this, "Uploader", handle, destination + " <- " + file);

		// Startzeit markieren
		final Date startTimeMarker = new Date();

		try
		{
			if (!file.exists())
			{
				// Endzeit und gesendete Größe markieren
				final Date endTimeMarker = new Date();
				final long transmissionMarker = 0;

				Logging.log(this, "Uploader", handle, "Source not found");

				// Wenn Datei nicht existiert, als Exception melden
				IntentUploadResponse.sendBroadcast(this, handle, startTimeMarker, endTimeMarker, transmissionMarker, null, new FileNotFoundException(file + " not found"));
			}
			else
			{
				// Client erstellen und konfigurieren
				final DefaultHttpClient httpClient = new DefaultHttpClient();
				httpClient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

				final HttpPost loginPost = new HttpPost(login);

				try
				{
					final StringEntity loginEntity = new StringEntity(new JSONStringer().object().key("user").object().key("email").value(login_user).key("password").value(login_pass)
							.endObject().endObject().toString());
					loginEntity.setContentType("application/json");
					loginPost.setEntity(loginEntity);

					final HttpResponse loginResponse = httpClient.execute(loginPost);

					final String loginResponseString = EntityUtils.toString(loginResponse.getEntity());

					Logging.log(this, "Uploader", handle, "Login successful with: " + loginResponseString);
				}
				catch (JSONException e)
				{
					throw new RuntimeException(e);
				}

				// Post erstellen und konfigurieren
				final HttpPost httpPost = new HttpPost(destination);
				httpPost.setHeader("Accept", acceptType);
				httpPost.setHeader("Expect", "100-continue");

				// Zu sendendes Entity erstellen und konfigurieren
				final FileEntity requestEntity = new FileEntity(file, contentType);

				// MIME-Type für Upload
				requestEntity.setContentType("application/text");

				// POST mit Entity befüllen
				httpPost.setEntity(requestEntity);

				// POST ausführen
				final HttpResponse response = httpClient.execute(httpPost);

				// Rückgabe lesen
				final String responseString = EntityUtils.toString(response.getEntity());

				// Endzeit und gesendete Größe markieren
				final Date endTimeMarker = new Date();
				final long transmissionMarker = file.length();

				Logging.log(this, "Uploader", handle, "Done");

				// Rückgabe weiterleiten
				IntentUploadResponse.sendBroadcast(this, handle, startTimeMarker, endTimeMarker, transmissionMarker, responseString, null);

				// Klienten beenden
				httpClient.getConnectionManager().shutdown();
			}
		}
		catch (IOException e)
		{
			// Endzeit und gesendete Größe markieren
			final Date endTimeMarker = new Date();
			final long transmissionMarker = 0;

			Logging.log(this, e);

			IntentUploadResponse.sendBroadcast(this, handle, startTimeMarker, endTimeMarker, transmissionMarker, null, e);
		}
	}
}

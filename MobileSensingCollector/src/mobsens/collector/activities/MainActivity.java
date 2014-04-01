package mobsens.collector.activities;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import mobsens.collector.Collector;
import mobsens.collector.CollectorIPC;
import mobsens.collector.R;
import mobsens.collector.communications.ConnectingActivity;
import mobsens.collector.intents.IntentStartCollector;
import mobsens.collector.util.Logging;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends ConnectingActivity
{
	private static final String ADDRESS_LOGIN = "http://mobilesensing.west.uni-koblenz.de/users/sign_in.json";

	private static final String PREFERENCES_NAME = "mscprefs";

	private static final String PREFERENCE_EMAIL = "mscemail";

	private static final String PREFERENCE_PASSWORD = "mscpwd";

	private static final String PREFERENCE_STARTUP = "mscsup";

	private CollectorIPC collectorIPC;

	private int state;

	public MainActivity()
	{
		super(Collector.class);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final SharedPreferences sp = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

		state = sp.getInt(PREFERENCE_STARTUP, R.string.intent_unspecified);
		handleStateChanged();

		// Beim Starten testen, ob Collector läuft um Aufruf schneller
		// auszuführen
		handleStartupWhileRunning();
	}

	@Override
	protected void onResume()
	{
		super.onResume();

		// Beim Anzeigen der Activity testen, ob Collector läuft
		handleStartupWhileRunning();
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.action_settings:
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			return true;

		default:
			return super.onMenuItemSelected(featureId, item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onConnected(IBinder service)
	{
		collectorIPC = CollectorIPC.Stub.asInterface(service);

		handleStartupWhileRunning();
	}

	private void handleStartupWhileRunning()
	{
		if (collectorIPC == null) return;

		try
		{
			if (collectorIPC.isCollecting())
			{
				Intent intent = new Intent(this, MapActivity.class);
				startActivity(intent);
			}
		}
		catch (RemoteException e)
		{
			Logging.log(this, e);
		}
	}

	@Override
	protected void onDisconnected()
	{
		collectorIPC = null;
	}

	public void visibilityTrue(View v)
	{

		switch (v.getId())
		{
		case R.id.main_leisure:
			state = R.string.intent_leisure;
			break;

		case R.id.main_sport:
			state = R.string.intent_sport;
			break;

		case R.id.main_everyday:
			state = R.string.intent_everyday;
			break;
		}

		handleStateChanged();
	}

	private void handleStateChanged()
	{
		TextView description = (TextView) findViewById(R.id.main_description);

		switch (state)
		{
		case R.string.intent_unspecified:
			state = R.string.intent_everyday;
		case R.string.intent_everyday:
			description.setText(R.string.description_everyday);
			break;

		case R.string.intent_sport:
			description.setText(R.string.description_sport);
			break;

		case R.string.intent_leisure:
			description.setText(R.string.description_leisure);
			break;
		}

	}

	public void start(View v)
	{
		if (isValidLogin())
		{
			try
			{
				if (!collectorIPC.isCollecting())
				{
					IntentStartCollector.sendBroadcast(this, getString(state));
				}
			}
			catch (RemoteException e)
			{
				Logging.log(this, e);
			}

			Intent intent = new Intent(this, MapActivity.class);
			startActivity(intent);
		}
		else
		{
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		}
	}

	private boolean isValidLogin()
	{
		final SharedPreferences sp = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

		final String mail = sp.getString(PREFERENCE_EMAIL, "");
		final String pass = sp.getString(PREFERENCE_PASSWORD, "");

		// Client erstellen und konfigurieren
		final DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

		final HttpPost loginPost = new HttpPost(ADDRESS_LOGIN);

		try
		{
			final StringEntity loginEntity = new StringEntity(new JSONStringer().object().key("user").object().key("email").value(mail).key("password").value(pass).endObject().endObject()
					.toString());
			loginEntity.setContentType("application/json");
			loginPost.setEntity(loginEntity);

			final HttpResponse loginResponse = httpClient.execute(loginPost);

			final String loginResponseString = EntityUtils.toString(loginResponse.getEntity());

			final JSONObject responseObject = new JSONObject(loginResponseString);

			if ("Success".equals(responseObject.getString("error")))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (JSONException e)
		{
			// Annehmen, das das Login gültig ist, wird dann am Schluss nicht
			// hochgeladen
			Logging.log(this, e);
			return true;
		}
		catch (IOException e)
		{
			// Annehmen, das das Login gültig ist, wird dann am Schluss nicht
			// hochgeladen
			Logging.log(this, e);
			return true;
		}
	}
}

package mobsens.collector.communications;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

public abstract class ConnectingActivity extends Activity
{
	public final ServiceConnection SERVICE_ENDPOINT = new ServiceConnection()
	{
		@Override
		public void onServiceConnected(ComponentName name, IBinder service)
		{
			onConnected(service);
		}

		@Override
		public void onServiceDisconnected(ComponentName name)
		{
			onDisconnected();
		}
	};

	public final Class<? extends ConnectedService> serviceClass;

	public ConnectingActivity(Class<? extends ConnectedService> serviceClass)
	{
		this.serviceClass = serviceClass;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// Super aufrufen
		super.onCreate(savedInstanceState);

		// Intent auf den Service erstellen
		final Intent intent = new Intent(this, serviceClass);

		// Service starten
		startService(intent);

		// Service binden
		bindService(intent, SERVICE_ENDPOINT, 0);
	}

	@Override
	protected void onDestroy()
	{
		// Service trennen
		unbindService(SERVICE_ENDPOINT);

		// Super aufrufen
		super.onDestroy();
	}

	protected void onConnected(IBinder service)
	{
	}

	protected void onDisconnected()
	{
	}
}

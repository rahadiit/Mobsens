package mobsens.collector.drivers.connectivity;

import java.util.Date;

import mobsens.collector.drivers.Driver;
import mobsens.collector.pipeline.BasicGenerator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

public class ConnectivityDriver extends BasicGenerator<ConnectivityOutput> implements Driver<ConnectivityOutput>
{
	public final BroadcastReceiver INTENT_ENDPOINT = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction()))
			{
				if (consumer != null)
				{
					// Intent-Extras holen
					final NetworkInfo extra_networkInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);

					// Felder erstellen
					final Date time = new Date();
					final int type = extra_networkInfo.getType();
					final int subtype = extra_networkInfo.getSubtype();
					final State state = extra_networkInfo.getState();

					// Item erstellen
					final ConnectivityOutput item = new ConnectivityOutput(time, type, subtype, state);

					// Item senden
					consumer.consume(item);
				}
			}
		}
	};

	/**
	 * Context, der die Services enthält
	 */
	public final ContextWrapper contextWrapper;

	public ConnectivityDriver(ContextWrapper contextWrapper)
	{
		this.contextWrapper = contextWrapper;
	}

	private boolean started = false;

	@Override
	public void start()
	{
		if (started) return;
		started = true;

		contextWrapper.registerReceiver(INTENT_ENDPOINT, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
	}

	@Override
	public void stop()
	{
		if (!started) return;
		started = false;

		contextWrapper.unregisterReceiver(INTENT_ENDPOINT);
	}
}

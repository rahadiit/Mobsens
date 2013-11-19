package mobsens.collector.communications;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public abstract class ConnectedService extends Service
{
	@Override
	public IBinder onBind(Intent intent)
	{
		onConnected();

		return getBinder();
	}

	@Override
	public void onRebind(Intent intent)
	{
		onConnected();

		super.onRebind(intent);
	}

	@Override
	public boolean onUnbind(Intent intent)
	{
		onDisconnected();

		return true;
	}

	protected IBinder getBinder()
	{
		return null;
	}

	protected void onConnected()
	{
	}

	protected void onDisconnected()
	{
	}

}

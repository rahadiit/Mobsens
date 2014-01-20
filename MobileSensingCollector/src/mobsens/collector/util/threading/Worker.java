package mobsens.collector.util.threading;

import java.util.concurrent.Exchanger;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class Worker
{
	private Looper looper;

	private Handler handler;

	public Worker()
	{
		final Exchanger<Void> gate = new Exchanger<Void>();

		new Thread()
		{
			@Override
			public void run()
			{
				Looper.prepare();

				looper = Looper.myLooper();
				handler = new Handler(looper)
				{
					@Override
					public void handleMessage(Message msg)
					{
						if (!Worker.this.handleMessage(msg))
						{
							super.handleMessage(msg);
						}
					}
				};

				try
				{
					gate.exchange(null);
				}
				catch (InterruptedException e)
				{
					Thread.currentThread().interrupt();
				}

				Looper.loop();
			}
		}.start();

		try
		{
			gate.exchange(null);
		}
		catch (InterruptedException e)
		{
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * Handles the message, returns true if handled
	 * 
	 * @param msg
	 * @return
	 */
	protected boolean handleMessage(Message msg)
	{
		return false;
	}

	public Handler getHandler()
	{
		return handler;
	}

	public Looper getLooper()
	{
		return looper;
	}
}

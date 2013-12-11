package mobsens.collector.util.threading;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class Worker
{
	private static final int FIELD_WAIT_TIME = 10;

	private Looper looper;

	private Handler handler;

	public Worker()
	{
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

				Looper.loop();
			}
		}.start();

		while (handler == null)
		{
			try
			{
				Thread.sleep(FIELD_WAIT_TIME);
			}
			catch (InterruptedException e)
			{
				Thread.currentThread().interrupt();
			}
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

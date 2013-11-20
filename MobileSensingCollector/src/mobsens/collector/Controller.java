package mobsens.collector;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import mobsens.collector.communications.ConnectingActivity;
import mobsens.collector.drivers.messaging.LogDriver;
import mobsens.collector.drivers.messaging.LogOutput;
import mobsens.collector.drivers.messaging.UploadResponseDriver;
import mobsens.collector.drivers.messaging.UploadResponseOutput;
import mobsens.collector.intents.IntentQuitCollector;
import mobsens.collector.intents.IntentStartCollector;
import mobsens.collector.intents.IntentStopCollector;
import mobsens.collector.pipeline.Consumer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Controller extends ConnectingActivity
{
	public final Consumer<LogOutput> LOG_ENDPOINT = new Consumer<LogOutput>()
	{
		@Override
		public void consume(LogOutput item)
		{
			Log.i(item.title, item.subtitle);

			if (item.description != null)
			{
				Log.d(item.title, item.description);
			}
		}
	};
	public final Consumer<UploadResponseOutput> UPLOAD_RESPONSE_ENDPOINT = new Consumer<UploadResponseOutput>()
	{
		@Override
		public void consume(UploadResponseOutput item)
		{
			try
			{
				File file = new File(getExternalFilesDir(null), "responses/" + item.handle);

				file.getParentFile().mkdirs();

				FileOutputStream fileOutputStream = new FileOutputStream(file, true);
				PrintStream printStream = new PrintStream(fileOutputStream);

				printStream.println(item.response);

				printStream.close();
				fileOutputStream.close();

				Log.i(item.handle, "Uploaded " + item.transmitted + " bytes at " + item.endTime + ", responsefile written");
			}
			catch (IOException e)
			{
				Log.e("File error", e.getLocalizedMessage(), e);
			}

		}
	};

	private final LogDriver logDriver;

	private final UploadResponseDriver uploadResponseDriver;

	public Controller()
	{
		super(Collector.class);

		logDriver = new LogDriver(this);
		logDriver.setConsumer(LOG_ENDPOINT);

		uploadResponseDriver = new UploadResponseDriver(this);
		uploadResponseDriver.setConsumer(UPLOAD_RESPONSE_ENDPOINT);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_controller);

		logDriver.start();
		uploadResponseDriver.start();

		((Button) findViewById(R.id.controller_start)).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				IntentStartCollector.sendBroadcast(Controller.this, "Title");
			}
		});

		((Button) findViewById(R.id.controller_stop)).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				IntentStopCollector.sendBroadcast(Controller.this);
			}
		});

		((Button) findViewById(R.id.controller_quit)).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				IntentQuitCollector.sendBroadcast(Controller.this);
			}
		});
	}

	@Override
	protected void onDestroy()
	{
		logDriver.stop();
		uploadResponseDriver.stop();

		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.controller, menu);
		return true;
	}

	@Override
	protected void onConnected(IBinder service)
	{
		Log.i("Controller", "onConnected(" + service + ")");
	}

	@Override
	protected void onDisconnected()
	{
		Log.i("Controller", "onDisconnected()");
	}

}

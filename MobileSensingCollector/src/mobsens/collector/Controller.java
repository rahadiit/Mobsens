package mobsens.collector;

import java.util.Date;

import mobsens.collector.communications.ConnectingActivity;
import mobsens.collector.drivers.messaging.CollectorStatusDriver;
import mobsens.collector.drivers.messaging.ExternalizeResponseDriver;
import mobsens.collector.drivers.messaging.LogDriver;
import mobsens.collector.drivers.messaging.UploadResponseDriver;
import mobsens.collector.intents.IntentStartCollector;
import mobsens.collector.intents.IntentStopCollector;
import mobsens.collector.util.Logging;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Controller extends ConnectingActivity
{
	private final LogDriver logDriver;

	private final UploadResponseDriver uploadResponseDriver;

	private final ExternalizeResponseDriver externalizeResponseDriver;

	private final CollectorStatusDriver collectorStatusDriver;

	private CollectorIPC collectorIPC;

	private TextView textViewControllerLog;
	private TextView textViewControllerTitle;
	private Button buttonControllerStartStop;

	public Controller()
	{
		super(Collector.class);

		logDriver = new LogDriver(this)
		{
			@Override
			protected void onLog(Date time, String title, String subtitle, String description)
			{
				String text = title + "\r\n  " + subtitle;

				if (description != null)
				{
					text += "\r\n" + description.replaceAll("(?m)^", "> ");
				}
				textViewControllerLog.setText(text + "\r\n\r\n" + textViewControllerLog.getText());
			}
		};

		uploadResponseDriver = new UploadResponseDriver(this)
		{

			@Override
			protected void onUploadResponse(String handle, Date startTime, Date endTime, long transmitted, String response, Throwable exception)
			{
				Logging.log(Controller.this, endTime, "Upload complete", handle, transmitted + " bytes transmitted");
			}
		};

		externalizeResponseDriver = new ExternalizeResponseDriver(this)
		{
			@Override
			protected void onExternalizeResponse(String handle, Date startTime, Date endTime, long transmitted, Throwable exception)
			{
				Logging.log(Controller.this, endTime, "Externalize complete", handle, transmitted + " bytes transmitted");
			}
		};

		collectorStatusDriver = new CollectorStatusDriver(this)
		{

			@Override
			protected void onCollectorStatus(boolean status)
			{
				if (status)
				{
					buttonControllerStartStop.setText("Stop");
				}
				else
				{
					buttonControllerStartStop.setText("Start");
				}
			}
		};

		collectorIPC = null;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		switch (item.getItemId())
		{

		case R.id.action_settings:
			startActivity(new Intent(getApplicationContext(), Settings.class));
			return true;

		case R.id.action_tag:
			startActivity(new Intent(getApplicationContext(), Fasttag.class));
			return true;

		case R.id.action_map:
			startActivity(new Intent(getApplicationContext(), Map.class));
			return true;

		case R.id.action_stage:
			startActivity(new Intent(getApplicationContext(), Stage.class));
			return true;

		default:
			return super.onMenuItemSelected(featureId, item);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_controller);

		textViewControllerLog = (TextView) findViewById(R.id.controller_log);
		textViewControllerTitle = (TextView) findViewById(R.id.controller_title);
		buttonControllerStartStop = (Button) findViewById(R.id.controller_start_stop);

		buttonControllerStartStop.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (collectorIPC != null)
				{
					try
					{
						if (collectorIPC.isCollecting())
						{
							IntentStopCollector.sendBroadcast(Controller.this);
						}
						else
						{
							IntentStartCollector.sendBroadcast(Controller.this, textViewControllerTitle.getText().toString());
						}
					}
					catch (RemoteException e)
					{
						Logging.log(Controller.this, e);
					}
				}
			}
		});

		logDriver.start();
		uploadResponseDriver.start();
		externalizeResponseDriver.start();
		collectorStatusDriver.start();
	}

	@Override
	protected void onDestroy()
	{
		logDriver.stop();
		uploadResponseDriver.stop();
		externalizeResponseDriver.stop();
		collectorStatusDriver.stop();

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
		collectorIPC = CollectorIPC.Stub.asInterface(service);

		try
		{
			if (collectorIPC.isCollecting())
			{
				buttonControllerStartStop.setText("Stop");
			}
			else
			{
				buttonControllerStartStop.setText("Start");
			}
		}
		catch (RemoteException e)
		{
			Logging.log(this, e);
		}

		Logging.log(this, "Controller", "Connected", null);
	}

	@Override
	protected void onDisconnected()
	{
		collectorIPC = null;

		Logging.log(this, "Controller", "Disconnected", null);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);

		outState.putString(R.id.controller_log + "text", textViewControllerLog.getText().toString());
		outState.putString(R.id.controller_title + "text", textViewControllerTitle.getText().toString());
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);

		textViewControllerLog.setText(savedInstanceState.getString(R.id.controller_log + "text"));
		textViewControllerTitle.setText(savedInstanceState.getString(R.id.controller_title + "text"));
	}
}

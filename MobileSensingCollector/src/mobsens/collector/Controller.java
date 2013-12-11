package mobsens.collector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;

import mobsens.collector.communications.ConnectingActivity;
import mobsens.collector.drivers.messaging.CollectorStatusDriver;
import mobsens.collector.drivers.messaging.LogDriver;
import mobsens.collector.drivers.messaging.UploadResponseDriver;
import mobsens.collector.intents.IntentStartCollector;
import mobsens.collector.intents.IntentStopCollector;
import mobsens.collector.intents.IntentUpload;
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
import android.widget.CheckBox;
import android.widget.TextView;

public class Controller extends ConnectingActivity
{
	private final LogDriver logDriver;

	private final UploadResponseDriver uploadResponseDriver;

	private final CollectorStatusDriver collectorStatusDriver;

	private CollectorIPC collectorIPC;

	private TextView textViewControllerLog;
	private TextView textViewControllerTitle;
	private Button buttonControllerSend;
	private Button buttonControllerStartStop;
	private CheckBox checkBoxControllerLocal;
	private CheckBox checkBoxControllerWeb;

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
				try
				{
					File file = new File(getExternalFilesDir(null), "responses/" + handle + ".response");

					file.getParentFile().mkdirs();

					FileOutputStream fileOutputStream = new FileOutputStream(file, true);
					PrintStream printStream = new PrintStream(fileOutputStream);

					printStream.println(response);

					printStream.close();
					fileOutputStream.close();

					Logging.log(Controller.this, endTime, "Upload complete", handle, transmitted + " bytes transmitted\r\nresponsefile written");
				}
				catch (IOException e)
				{
					Logging.log(Controller.this, e);
				}

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
		case R.id.action_tag:
			startActivity(new Intent(getApplicationContext(), Fasttag.class));
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

		logDriver.start();

		uploadResponseDriver.start();

		collectorStatusDriver.start();

		textViewControllerLog = (TextView) findViewById(R.id.controller_log);
		textViewControllerTitle = (TextView) findViewById(R.id.controller_title);
		checkBoxControllerLocal = (CheckBox) findViewById(R.id.controller_local);
		checkBoxControllerWeb = (CheckBox) findViewById(R.id.controller_web);
		buttonControllerStartStop = (Button) findViewById(R.id.controller_start_stop);
		buttonControllerSend = (Button) findViewById(R.id.controller_send);

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

		buttonControllerSend.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				final boolean toLocal = checkBoxControllerLocal.isChecked();
				final boolean toWeb = checkBoxControllerWeb.isChecked();
				final byte[] cache;

				if (toLocal)
				{
					cache = new byte[2048];
				}
				else
				{
					cache = null;
				}

				for (File file : new File(Controller.this.getFilesDir(), "wfj").listFiles())
				{
					if (toLocal)
					{
						File copy = new File(Controller.this.getExternalFilesDir(null), "wfj/" + file.getName());

						copy.getParentFile().mkdirs();

						try
						{
							FileOutputStream fileOutputStream = new FileOutputStream(copy);
							FileInputStream fileInputStream = new FileInputStream(file);

							int read;
							while ((read = fileInputStream.read(cache)) > 0)
							{
								fileOutputStream.write(cache, 0, read);
							}

							fileInputStream.close();
							fileOutputStream.close();
						}
						catch (IOException e)
						{
							Logging.log(Controller.this, "Could not create local copy of " + file.getName(), e.getMessage(), null);
						}
					}

					Logging.log(Controller.this, "Uploading", file.getName(), null);

					if (toWeb)
					{
						IntentUpload.startService(Controller.this, file.getName(), "http://mobilesensing.west.uni-koblenz.de/users/sign_in.json", "mlukas@gmx.net", "12345678",
								"http://mobilesensing.west.uni-koblenz.de/recordings/upload", file, "application/text", "*/*", true);
					}
					else
					{
						file.delete();
					}
				}
			}
		});
	}

	@Override
	protected void onDestroy()
	{
		logDriver.stop();

		uploadResponseDriver.stop();

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
		outState.putBoolean(R.id.controller_local + "checked", checkBoxControllerLocal.isChecked());
		outState.putBoolean(R.id.controller_web + "checked", checkBoxControllerWeb.isChecked());
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);

		textViewControllerLog.setText(savedInstanceState.getString(R.id.controller_log + "text"));
		textViewControllerTitle.setText(savedInstanceState.getString(R.id.controller_title + "text"));
		checkBoxControllerLocal.setChecked(savedInstanceState.getBoolean(R.id.controller_local + "checked"));
		checkBoxControllerWeb.setChecked(savedInstanceState.getBoolean(R.id.controller_web + "checked"));
	}
}

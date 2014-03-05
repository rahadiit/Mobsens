package mobsens.collector;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import mobsens.collector.util.Logging;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class Viewer extends Activity
{
	private static final String EXTRA_FILE = "file";

	public static void showActivity(ContextWrapper contextWrapper, File file)
	{
		final Intent intent = new Intent(contextWrapper, Viewer.class);
		intent.putExtra(EXTRA_FILE, file);

		contextWrapper.startActivity(intent);
	}

	private TextView textViewViewerTitle;
	private TextView textViewViewerStartTimeValue;
	private TextView textViewViewerEndTimeValue;
	private TextView textViewViewerSizeValue;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewer);

		Serializable extra_file = getIntent().getSerializableExtra(EXTRA_FILE);
		File file = (File) extra_file;

		textViewViewerTitle = (TextView) findViewById(R.id.viewer_title);
		textViewViewerStartTimeValue = (TextView) findViewById(R.id.viewer_start_time_value);
		textViewViewerEndTimeValue = (TextView) findViewById(R.id.viewer_end_time_value);
		textViewViewerSizeValue = (TextView) findViewById(R.id.viewer_size_value);

		try
		{
			int entries = 0;

			String title = null;
			long startTime = Long.MIN_VALUE;
			long endTime = Long.MIN_VALUE;

			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine())
			{
				final JSONObject json = new JSONObject(scanner.nextLine());

				entries++;

				if (json.has("rec"))
				{
					final JSONObject rec = json.getJSONObject("rec");

					title = rec.has("title") ? rec.getString("title") : title;
					startTime = rec.has("time_start") ? rec.getLong("time_start") :startTime;
					endTime = rec.has("time_stop") ? rec.getLong("time_stop") : endTime;
				}
			}

			scanner.close();

			if (title != null) textViewViewerTitle.setText(title);
			if (startTime != Long.MIN_VALUE) textViewViewerStartTimeValue.setText(String.format("%tF %<tT", startTime));
			if (endTime != Long.MIN_VALUE) textViewViewerEndTimeValue.setText(String.format("%tF %<tT", endTime));

			textViewViewerSizeValue.setText(Integer.toString(entries));
		}
		catch (IOException e)
		{
			Logging.log(this, e);
		}
		catch (JSONException e)
		{
			Logging.log(this, e);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.viewer, menu);
		return true;
	}

}

package mobsens.collector.activities;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import mobsens.collector.R;
import mobsens.collector.intents.IntentUpload;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import static mobsens.collector.config.Constants.*;

public class RatingActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rating);

		Intent starter = getIntent();
		double totalDistance = starter.getDoubleExtra(MapActivity.EXTRA_DISTANCE, Double.NaN);
		double totalTime = starter.getDoubleExtra(MapActivity.EXTRA_TOTAL_TIME, Double.NaN);
		float maxSpeed = starter.getFloatExtra(MapActivity.EXTRA_MAX_SPEED, Float.NaN);
		float avgSpeed = starter.getFloatExtra(MapActivity.EXTRA_AVG_SPEED, Float.NaN);

		TextView textViewTotalDistance = (TextView) findViewById(R.id.rating_total_distance_value);
		TextView textViewTime = (TextView) findViewById(R.id.rating_time_value);
		TextView textViewMaxSpeed = (TextView) findViewById(R.id.rating_max_speed_value);
		TextView textViewAvgSpeed = (TextView) findViewById(R.id.rating_avg_speed_value);

		if (Double.isNaN(totalDistance))
			textViewTotalDistance.setText("--- m");
		else
			textViewTotalDistance.setText(Math.round(totalDistance) + "m");

		if (Double.isNaN(totalTime))
			textViewTime.setText("--- s");
		else
			textViewTime.setText(Math.round(totalTime) + "s");

		if (Float.isNaN(maxSpeed))
			textViewMaxSpeed.setText("--- km/h");
		else
			textViewMaxSpeed.setText(Math.round(maxSpeed) + "km/h");

		if (Float.isNaN(avgSpeed))
			textViewAvgSpeed.setText("--- km/h");
		else
			textViewAvgSpeed.setText(Math.round(avgSpeed) + "km/h");
	}

	private List<File> stageFiles()
	{
		return Arrays.asList(new File(getFilesDir(), "wfj").listFiles());
	}

	private void executeToWeb(File file)
	{
		if (file.exists())
		{
			final SharedPreferences sp = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

			IntentUpload.startService(this, file.getName(), ADDRESS_LOGIN, sp.getString(PREFERENCE_EMAIL, ""), sp.getString(PREFERENCE_PASSWORD, ""), ADDRESS_UPLOAD, file,
					"application/text", "*/*");
		}
	}

	public void submit(View v)
	{
		for (File file : stageFiles())
		{
			executeToWeb(file);
		}

		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}
}

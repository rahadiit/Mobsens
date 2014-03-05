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
import android.widget.RadioButton;

public class RatingActivity extends Activity
{

	private static final String ADDRESS_UPLOAD = "http://mobilesensing.west.uni-koblenz.de/recordings/upload";

	private static final String ADDRESS_LOGIN = "http://mobilesensing.west.uni-koblenz.de/users/sign_in.json";

	private static final String PREFERENCES_NAME = "mscprefs";

	private static final String PREFERENCE_EMAIL = "mscemail";

	private static final String PREFERENCE_PASSWORD = "mscpwd";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rating);
	}

	public void radioButton(View v)
	{

		RadioButton b = (RadioButton) findViewById(R.id.radio1);
		RadioButton c = (RadioButton) findViewById(R.id.radio0);

		if (v.getId() == R.id.radio0)
		{
			if (c.isChecked() == true)
			{
				c.setChecked(false);
				b.setChecked(true);
			}
			else
			{
				c.setChecked(true);
				b.setChecked(false);
			}
			// if((c.isChecked() != true)){
			// c.setChecked(true);
			// b.setChecked(false);
			// }
		}

		if (v.getId() == R.id.radio1)
		{
			if (b.isChecked() == true)
			{
				b.setChecked(false);
				c.setChecked(true);
			}
			else
			{
				// if((b.isChecked() != true)){
				b.setChecked(true);
				c.setChecked(false);
			}
		}

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

			IntentUpload.startService(this, file.getName(), ADDRESS_LOGIN, sp.getString(PREFERENCE_EMAIL, ""),
					sp.getString(PREFERENCE_PASSWORD, ""), ADDRESS_UPLOAD, file, "application/text", "*/*");
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

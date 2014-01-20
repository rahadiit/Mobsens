package mobsens.collector;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Settings extends Activity
{
	private static final String PREFERENCES_NAME = "mscprefs";

	private static final String PREFERENCE_EMAIL = "mscemail";

	private static final String PREFERENCE_PASSWORD = "mscpwd";

	private TextView textViewSettingsEmailValue;

	private TextView textViewSettingsPasswordValue;

	private Button buttonSettingsAccept;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		textViewSettingsEmailValue = (TextView) findViewById(R.id.settings_email_value);
		textViewSettingsPasswordValue = (TextView) findViewById(R.id.settings_password_value);
		buttonSettingsAccept = (Button) findViewById(R.id.settings_accept);

		final SharedPreferences sp = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

		textViewSettingsEmailValue.setText(sp.getString(PREFERENCE_EMAIL, ""));
		textViewSettingsPasswordValue.setText(sp.getString(PREFERENCE_PASSWORD, ""));

		buttonSettingsAccept.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				final Editor editor = sp.edit();
				editor.putString(PREFERENCE_EMAIL, textViewSettingsEmailValue.getText().toString());
				editor.putString(PREFERENCE_PASSWORD, textViewSettingsPasswordValue.getText().toString());
				editor.commit();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
}

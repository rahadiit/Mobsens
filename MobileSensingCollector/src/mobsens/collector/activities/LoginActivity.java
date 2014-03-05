package mobsens.collector.activities;

import mobsens.collector.R;
import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity
{
	private static final String PREFERENCES_NAME = "mscprefs";

	private static final String PREFERENCE_EMAIL = "mscemail";

	private static final String PREFERENCE_PASSWORD = "mscpwd";

	private static final String PREFERENCE_STARTUP = "mscsup";

	int state;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		final SharedPreferences sp = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

		EditText mail = (EditText) findViewById(R.id.settings_mail_value);
		EditText pass = (EditText) findViewById(R.id.settings_pass_value);

		mail.setText(sp.getString(PREFERENCE_EMAIL, ""));
		pass.setText(sp.getString(PREFERENCE_PASSWORD, ""));
		
		state = sp.getInt(PREFERENCE_STARTUP, R.string.intent_unspecified);
		handleState();
	}

	public void intention(View v)
	{
		switch (state)
		{
		case R.string.intent_unspecified:

			state = R.string.intent_sport;
			break;

		case R.string.intent_sport:

			state = R.string.intent_everyday;
			break;

		case R.string.intent_everyday:

			state = R.string.intent_leisure;
			break;

		case R.string.intent_leisure:

			state = R.string.intent_unspecified;
			break;
		}

		handleState();
	}

	private void handleState()
	{
		final Button b = (Button) findViewById(R.id.settings_intention_value);

		switch (state)
		{
		case R.string.intent_sport:
			b.setText(R.string.intent_sport);
			b.setBackgroundResource(R.drawable.red_enabled);
			break;

		case R.string.intent_everyday:
			b.setText(R.string.intent_everyday);
			b.setBackgroundResource(R.drawable.blue_enabled);
			break;

		case R.string.intent_leisure:

			b.setText(R.string.intent_leisure);
			b.setBackgroundResource(R.drawable.green_enabled);
			break;

		case R.string.intent_unspecified:
			b.setText(R.string.intent_unspecified);
			b.setBackgroundResource(R.drawable.gray_enabled);
			break;
		}
	}

	public void save(View v)
	{

		final SharedPreferences sp = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

		Editor ed = sp.edit();

		EditText mail = (EditText) findViewById(R.id.settings_mail_value);
		EditText pass = (EditText) findViewById(R.id.settings_pass_value);

		ed.putString(PREFERENCE_EMAIL, mail.getText().toString());
		ed.putString(PREFERENCE_PASSWORD, pass.getText().toString());
		ed.putInt(PREFERENCE_STARTUP, state);

		ed.commit();

		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}
}

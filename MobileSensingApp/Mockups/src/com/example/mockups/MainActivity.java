package com.example.mockups;



import android.R.layout;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.action_settings:
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			return true;
			
		default:
			return super.onMenuItemSelected(featureId, item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void visibilityTrue(View v) {
		
		View sport1 = findViewById(R.id.button1);
		View text1 = findViewById(R.id.textView01);
		View freizeit1 = findViewById(R.id.Button02);
		View alltag1 = findViewById(R.id.button2);
		
		if (v.getId() == R.id.Button02) {
			
				((TextView) text1).setText("Freizeit");
				
			}
		
		if (v.getId() == R.id.button1) {
			
				((TextView) text1).setText("Sport");
				
			}
		
		if (v.getId() == R.id.button2) {
			
				((TextView) text1).setText("Alltag");
				}
				
			
	}

	public void start(View v)
	{
		if (isValidLogin())
		{
			Intent intent = new Intent(this, MapActivity.class);
			startActivity(intent);
		}
		else
		{
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		}
	}

	private boolean isValidLogin()
	{
		return true;
	}

}

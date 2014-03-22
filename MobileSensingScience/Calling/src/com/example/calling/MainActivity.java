package com.example.calling;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void process(View view) {
		for (int i = 1; i < 40; i++) {
			startNewActivity("com.example.ziptest", i + "000");
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void startNewActivity(String packageName, String data) {
		Intent launchIntent = getPackageManager().getLaunchIntentForPackage(
				packageName);
		launchIntent.putExtra("data", data);
		startActivity(launchIntent);
	}
}

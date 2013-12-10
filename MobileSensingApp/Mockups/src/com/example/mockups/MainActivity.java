package com.example.mockups;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

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
	
	public void visibilityTrue(View v) {
		
		View b = findViewById(R.id.textView1);
		
		if (b.getVisibility() == View.INVISIBLE) {
			b.setVisibility(View.VISIBLE);
		}else {
			b.setVisibility(View.INVISIBLE);
		}
		
	}
	
	public void menu(View v){
		Intent intent = new Intent (this, LoginActivity.class);
		startActivity(intent);
		
	}
	
	public void start(View v){
		Intent intent = new Intent (this, MapActivity.class);
		startActivity(intent);
	}

}

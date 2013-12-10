package com.example.mockups;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MapActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
	}

	public void stop(View v){
		
		Intent intent = new Intent (this, RatingActivity.class);
		startActivity(intent);
		finish();
	}
	
}

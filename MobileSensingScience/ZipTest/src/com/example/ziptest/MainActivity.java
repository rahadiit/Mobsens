package com.example.ziptest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.ziptest.data.TestData;
import com.example.ziptest.worker.CompressService;
import com.example.ziptest.worker.CompressWorker;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i("MA","on create");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Called when button is pressed
	 * 
	 * @param view
	 */
	public void startZipWorker(View view) {

		// generating test data
		

		try {
			Log.i("MA","new Intent");
			Intent intent =new Intent(this, CompressService.class);
			//CompressService cs = new CompressService(data);
			//intent.putExtra("data", data);
			startService(intent);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(
					MainActivity.this,
					"ee "+e.toString(),
					Toast.LENGTH_SHORT).show();
		}

		//
		//
		// Toast.makeText(
		// MainActivity.this,
		// "Thread ID: "+(t.getId()+ " NAME: "+t.getName()+" is dead"),
		// Toast.LENGTH_SHORT).show();

	}

}
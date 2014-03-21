package com.example.ziptest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.example.ziptest.data.TestData;
import com.example.ziptest.worker.CompressWorker;

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

	/**
	 * Called when button is pressed
	 * 
	 * @param view
	 */
	public void startZipWorker(View view) {
		// generating test data
		TestData td = new TestData();
		byte[] data = td.get_bytes(10000, 0.4);

		// create compress worker and bind test data
		CompressWorker zw = new CompressWorker();
		zw.setData(data);

		// run the worker
		Thread t;
		t = new Thread(zw);
		t.start();

		// log information about thread
		Log.d("getId", Long.toString(t.getId()));
		Log.d("getName", t.getName());
	}

}
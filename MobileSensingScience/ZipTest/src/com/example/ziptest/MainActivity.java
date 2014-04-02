package com.example.ziptest;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.ziptest.data.TestData;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i("MA", "on create");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Bundle extra = getIntent().getExtras();
		startZip(extra.getString("data"));
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

		// try {
		// Log.i("MA","new Intent");
		// Intent intent =new Intent(this, CompressService.class);
		// //CompressService cs = new CompressService(data);
		// //intent.putExtra("data", data);
		// startService(intent);
		//
		//
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// Toast.makeText(
		// MainActivity.this,
		// "ee "+e.toString(),
		// Toast.LENGTH_SHORT).show();
		// }

		//
		//
		// Toast.makeText(
		// MainActivity.this,
		// "Thread ID: "+(t.getId()+ " NAME: "+t.getName()+" is dead"),
		// Toast.LENGTH_SHORT).show();
	}

	public void startZip(String dataString) {

		long start = new Date().getTime();
		
		Log.i("logging", "startingZip " + new Date().getTime());
		Log.i("ZipTest", dataString);

		int size = Integer.parseInt(dataString);
		Log.i("CS", "Started");

		byte[] compressed_data;

		// output stream
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		// compress stream
		GZIPOutputStream gos;

		try {
			TestData td = new TestData();
			byte[] data = td.get_bytes(size, 0.4);

			// bind output stream to compress stream
			gos = new GZIPOutputStream(os);

			// write data to compress stream
			// gos.write(intent.getByteArrayExtra("data"));
			gos.write(data);

			Log.i("CS", data.length + " arrayLength");

			// close streams
			gos.close();
			os.close();

			// get compressed data
			compressed_data = os.toByteArray();

			// log size of compressed data
			Log.i("CS",
					"compressed data size "
							+ Integer.toString(compressed_data.length));

			writeFile(new ByteArrayInputStream(compressed_data), (size / 1000)
					+ ".gz");
			
			//
			// FileOutputStream fos = openFileOutput("test",
			// Context.MODE_PRIVATE);
			// fos.write(os.toByteArray());
			// fos.close();

			Toast.makeText(
					MainActivity.this,
					"compressed data size "
							+ Integer.toString(compressed_data.length),

					Toast.LENGTH_SHORT).show();

		} catch (IOException e) {
			Log.i("CS", "exception");
			e.printStackTrace();

		}
		Log.i("ziptest", "finished in "+(new Date().getTime()-start)+"ms");

		// Activity finished ok, return the data
		Log.i("logging", "finishZip " + new Date().getTime());
		finish();
	}

	public void writeFile(InputStream is, String fileName) {

		File writeFile = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath(), fileName);
		
		try {
			BufferedOutputStream logOut = new BufferedOutputStream(
					new FileOutputStream(writeFile));
			byte[] buffer = new byte[20480];
			for (int ln = is.read(buffer); ln != -1; ln = is.read(buffer)) {
				logOut.write(buffer, 0, ln);
			}
			logOut.close();
			is.close();

		} catch (Exception e) {
			Log.i("Zip WriteFile", e.toString());
			// Toast.makeText(UMLogger.this,
			// "Failed to write log to sdcard" + e.toString(),
			// Toast.LENGTH_SHORT).show();
		}
	}

}

/*
 * dead code
 * 
 * @Override public void finish() { // Prepare data intent Intent data = new
 * Intent(); data.putExtra("value",10000); // Activity finished ok, return the
 * data setResult(RESULT_OK, data); Log.i("ZipTest",
 * "result "+data.getIntExtra("value",-1)); super.finish(); }
 */

//
// // // log information about thread
// // Log.d("getId", Long.toString(t.getId()));
// // Log.d("getName", t.getName());
//
// if (stopSelfResult(startId)) {
// return 0;
// }

// Intent data = new Intent();


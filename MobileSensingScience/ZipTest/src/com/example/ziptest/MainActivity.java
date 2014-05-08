package com.example.ziptest;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.concurrent.ExecutionException;
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
		// Bundle extra = getIntent().getExtras();
		// startZip(extra.getString("data"),extra.getString("entropy"));

		for (int deflater = 1; deflater <= 9; deflater++) {
			for (int entropy = 1; entropy <= 8; entropy++) {
				
//				File file = new File(Environment.getExternalStorageDirectory()
//				.getAbsolutePath()+"/testfiles/zipping/"+deflater+"-deflater/"+entropy+"-entropy/");
//				file.mkdirs();
//				
				for (int fileMB = 16; fileMB <= 16; fileMB=fileMB*2) {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					int fileSize = fileMB * 1024 * 1024;
					// startNewActivity("com.example.ziptest", fileSize + "", j
					// + "");
					ZipTask zt = new ZipTask();
					try {
						zt.execute(fileSize, entropy , deflater).get();
					} catch (Exception e1) {
						Log.w("zip", e1.getMessage());
					}
					// startZip(fileSize+"", j+"");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
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

}

/*
 * dead code
 * 
 * @Override public void finish() { // Prepare data intent Intent data = new
 * Intent(); data.putExtra("value",10000); // Activity finished ok, return the
 * data setResult(RESULT_OK, data); Log.i("ZipTest",
 * "result "+data.getIntExtra("value",-1)); super.finish(); }
 * 
 * 
 * // writeFile(new ByteArrayInputStream(compressed_data), (size / 1000) // +
 * ".gz");
 * 
 * // writeFile(new ByteArrayInputStream(data), (size / 1000) // +
 * "_"+entropy+"_"+"entropy.data");
 * 
 * // // FileOutputStream fos = openFileOutput("test", // Context.MODE_PRIVATE);
 * // fos.write(os.toByteArray()); // fos.close();
 * 
 * // Toast.makeText( // MainActivity.this, // "compressed data size " // +
 * Integer.toString(compressed_data.length), // // Toast.LENGTH_SHORT).show();
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


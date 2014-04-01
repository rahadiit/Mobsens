package com.example.sendtest;

import java.io.File;
import java.util.Date;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.AsyncTask.Status;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Bundle extra = getIntent().getExtras();
		// sendData(extra.getString("data"));
		doIt();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void doIt() {

		for (int i = 0; i < 40; i++) {
			String filename = i + "000.gz";
			Toast.makeText(MainActivity.this, "sending " + filename,
					Toast.LENGTH_SHORT).show();
			
		}

	}

	private void sendData(String filePath) {

		// web272f1:Dzw8R1H3
		File sdcard = Environment.getExternalStorageDirectory();
		File file = new File(sdcard, filePath);

		Log.i("ftp",
				"startSending" + new Date().getTime() + " "
						+ file.getAbsolutePath());

		if (file.exists()) {
			FtpUrlUpload ftpUpload = new FtpUrlUpload();
			ftpUpload.execute(file);

			while (ftpUpload.getStatus() != Status.FINISHED) {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					Log.e("ftp",
							"exception while sleeping. zzzz " + e.getMessage());
				}
				Log.i("ftp", "still not finished uploading " + file.getName());
			}

			Log.i("ftp",
					"file uploaded" + new Date().getTime() + " "
							+ file.getAbsolutePath());
		}
	}

}

package com.example.sendtest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

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
		// sendData("41943_0entropy.data");
		// sendData("20971_0entropy.data");
		// sendData("10485_0entropy.data");
		// sendData("5242_0entropy.data");

		// sendData("41943_1entropy.data");
		// sendData("20971_1entropy.data");
		for (int i = 0; i < 3; i++) {
			sendData("16777_1entropy.data");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sendData("16777_0entropy.data");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			sendData("8388_1entropy.data");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			sendData("8388_0entropy.data");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			sendData("4194_1entropy.data");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			sendData("4194_0entropy.data");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			sendData("2097_1entropy.data");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sendData("2097_0entropy.data");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			sendData("1048_1entropy.data");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			sendData("1048_0entropy.data");

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// for (int i = 1; i < 10; i++) {
		// String filename = i + "000.gz";
		// Toast.makeText(MainActivity.this, "sending " + filename,
		// Toast.LENGTH_SHORT).show();
		// sendData(filename);
		//
		// }
	}

	private void sendData(String filePath) {

		// web272f1:Dzw8R1H3
		File sdcard = Environment.getExternalStorageDirectory();
		File file = new File(sdcard, filePath);

		if (file.exists()) {
			appendLog("startSending " + new Date().getTime() + " "
					+ file.getAbsolutePath());
			Log.i("http", "startSending");

			long start = new Date().getTime();
			FtpUrlUpload ftpUpload = new FtpUrlUpload();

			try {
				ftpUpload.execute(file).get();

				Log.i("file", "" + file.length());

				// HttpUpload hu = new HttpUpload();
				// hu.execute(file).get();

			} catch (Exception e1) {
				// for(StackTraceElement st:e1.getStackTrace()){
				// Log.w("http3 st",st.toString());
				// }
				Log.w("http3", e1.toString());
			}
			double time = ((double) new Date().getTime() - start) / 1000;
			double fileKb = ((double) file.length() / 1024);
			double kbS = fileKb / time;

			appendLog("finishedSending " + new Date().getTime() + " "
					+ file.getAbsolutePath() + kbS + "kb/s");

			appendLog("upload " + file.getName() + " successful in " + time
					+ "s" + " thats " + kbS + "kb/s");

			Log.i("http", "finishedSending " + file.length());
			Log.i("http", "upload " + file.getName() + " successful in " + time
					+ "s" + " thats " + kbS + "kb/s");

		} else {
			Log.i("sendT", "file not found");
		}
	}

	public void appendLog(String text) {
		/*
		 * File writeFile = new File(Environment .getExternalStorageDirectory()
		 * .getAbsolutePath(), "PowerTrace" + System.currentTimeMillis() +
		 * ".log");
		 */
		File logFile = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath(), "LogCat.log");
		if (!logFile.exists()) {
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			// BufferedWriter for performance, true to set append to file flag
			BufferedWriter buf = new BufferedWriter(new FileWriter(logFile,
					true));
			buf.append(text);
			buf.newLine();
			buf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

/*
 * dead code
 */

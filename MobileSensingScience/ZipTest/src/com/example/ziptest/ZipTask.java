package com.example.ziptest;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.zip.Deflater;
import java.util.zip.GZIPOutputStream;

import com.example.ziptest.data.TestData;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

public class ZipTask extends AsyncTask<Integer, Void, Void> {

	public void startZip(int fileSize, int entropy, int _deflater) {

		final int deflater = _deflater;
		byte[] buffer = new byte[1024];

		Log.i("logging", "startingZip " + new Date().getTime());
		Log.i("ZipTest", "" + fileSize);

		Log.i("CS", "Started");

		byte[] compressed_data;

		// compress stream
		GZIPOutputStream gos;

		try {
			FileInputStream in = new FileInputStream(new File(Environment
					.getExternalStorageDirectory().getAbsolutePath()
					+ "/testfiles/"
					+ entropy + "/", (fileSize / 1000) + "_entropy.data"));

			appendLog("startZip " + new Date().getTime() + " " + fileSize + "_"
					+ entropy);

			
			File zippedFile=new File(
					Environment.getExternalStorageDirectory().getAbsolutePath()
					+ "/testfiles/zipping/" + deflater + "-deflater/" + entropy
					+ "-entropy/", (fileSize / 1000) + ".gzip");
			
			
			// bind output stream to compress stream
			gos = new GZIPOutputStream(new FileOutputStream(zippedFile)) {
				{
					this.def.setLevel(deflater);
				}
			};

			int len;
			while ((len = in.read(buffer)) > 0) {
				gos.write(buffer, 0, len);
			}

			in.close();
			gos.finish();
			gos.close();

			 appendLog("finishedZip " + new Date().getTime() + " " + fileSize +
			 "_"
			 + entropy + "_" +deflater+ "_"+""+zippedFile.length()/1024+"kb");
			 
			 Log.i("logging", zippedFile.getAbsolutePath());

		} catch (Exception e) {
			Log.i("CS", "exception");
			e.printStackTrace();

		}

		// Activity finished ok, return the data
		Log.i("logging", "finishZip " + new Date().getTime());
	}

	public void writeFile(InputStream is, String entropy, String fileName) {

		File writeFile = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/testfiles/" + entropy + "/", fileName);

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

	@Override
	protected Void doInBackground(Integer... params) {
		startZip(params[0], params[1], params[2]);
		return null;
	}

	public void appendLog(String text) {
		/*
		 * File writeFile = new File(Environment .getExternalStorageDirectory()
		 * .getAbsolutePath(), "PowerTrace" + System.currentTimeMillis() +
		 * ".log");
		 */

		File logFile = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath(), "ZipTest.log");
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
 * dead code //ByteArrayInputStream is = new ByteArrayInputStream(data);
 * 
 * 
 * double[] entr = new double[9]; entr[0] = 0; entr[1] = 0.0171286550767266;
 * entr[2] = 0.0416926902736567; entr[3] = 0.0724497922261490; entr[4] =
 * 0.110027864438360; entr[5] = 0.156142171816185; entr[6] = 0.214501744859829;
 * entr[7] = 0.294926193599964; entr[8] = 0.5;
 * 
 * TestData td = new TestData(); byte[] data = td.get_bytes(fileSize,
 * entr[entropy]);
 * 
 * 
 * // writeFile(is, entropy+"" ,+(fileSize / 1000) + // "_"+"entropy.data");
 * 
 * // ByteArrayOutputStream os = new ByteArrayOutputStream(); // gos = new
 * GZIPOutputStream(os) { // { // this.def.setLevel(Deflater.BEST_COMPRESSION);
 * // } // };
 */

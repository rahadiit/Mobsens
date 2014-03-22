package com.example.ziptest.worker;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.zip.GZIPOutputStream;

import com.example.ziptest.data.TestData;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class CompressService extends Service {

	@Override
	public void onCreate() {
		// The service is being created
		Log.i("CS", "create");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// handleCommand(intent);
		Log.i("CS", "Started");

		byte[] compressed_data;

		// output stream
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		// compress stream
		GZIPOutputStream gos;

		try {
			TestData td = new TestData();
			byte[] data = td.get_bytes(500000, 0.4);
			
			// bind output stream to compress stream
			gos = new GZIPOutputStream(os);

			// write data to compress stream
			//gos.write(intent.getByteArrayExtra("data"));
			gos.write(data);
			
			Log.i("CS", intent.getByteArrayExtra("data").length+" arrayLength");

			// close streams
			gos.close();
			os.close();

			// get compressed data
			compressed_data = os.toByteArray();
			
			// log size of compressed data
			Log.d("compressed data size",
					Integer.toString(compressed_data.length));
			
			FileOutputStream fos = openFileOutput("test", Context.MODE_PRIVATE);
			fos.write(os.toByteArray());
			fos.close();
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.i("CS", "thread dead");
		//
		// // // log information about thread
		// // Log.d("getId", Long.toString(t.getId()));
		// // Log.d("getName", t.getName());
		//
		// if (stopSelfResult(startId)) {
		// return 0;
		// }
		stopSelf(startId);
		return startId;
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.i("CS", "onbind");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// All clients have unbound with unbindService()
		return true;
	}

	@Override
	public void onRebind(Intent intent) {
		// A client is binding to the service with bindService(),
		// after onUnbind() has already been called
	}

	@Override
	public void onDestroy() {
		// The service is no longer used and is being destroyed
		Log.i("CS", "destroy");
		stopSelf();
	}

}

package com.example.ziptest.worker;

import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPOutputStream;
import java.io.IOException;

import com.example.ziptest.MainActivity;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

/**
 * Provides Worker to compress data
 */
public class CompressWorker extends Worker {
	/**
	 * Compress Data
	 */
	
	@Override
	public void run() {
		// compressed data
		byte[] compressed_data;

		// output stream
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		
		// compress stream
		GZIPOutputStream gos;
		
		try {
			// bind output stream to compress stream
			gos = new GZIPOutputStream(os);
			
			// write data to compress stream
			gos.write(this.getData());
			
			// close streams
			gos.close();
			os.close();
			
			// get compressed data
			compressed_data = os.toByteArray();
			
			// log size of compressed data
			Log.d("compressed data size", Integer.toString(compressed_data.length));
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

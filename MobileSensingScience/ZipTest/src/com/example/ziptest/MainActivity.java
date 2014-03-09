package com.example.ziptest;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.zip.GZIPOutputStream;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;

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

	// FileOutputStream stream = new FileOutputStream("test");
	// DataOutputStream ps = new DataOutputStream(stream);
	// ps.writeInt(100000000);
	// ps.close();
	// stream.close();

	public void sendWrite(View view) {
		try {
			FileOutputStream fos = openFileOutput("test", Context.MODE_PRIVATE);
			String string = "hello world! ";
			String bla = "";
			String blub = "";
			//for (int i = 0; i < 21; i++)
			//	string += string;
			
			Random r = new Random();
			
			for(int i=0;i<10000;i++){
				
				string += (char)r.nextInt() + (char)r.nextInt(300)+ (char)r.nextInt(100);
				bla+=(char)r.nextInt() + (char)r.nextInt(300)+ (char)r.nextInt(100);
				blub+=(char)r.nextInt() + (char)r.nextInt(300)+ (char)r.nextInt(100);
			}
			
			for(int i=0;i<20;i++){
				fos.write(string.getBytes());
				fos.write(bla.getBytes());
				fos.write(blub.getBytes());
			}
			
		
			
			fos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendZip(View view) {
		//http://www.mkyong.com/java/how-to-compress-a-file-in-gzip-format/
		String gzipFile = "text.gz";
		try {
			byte[] buffer = new byte[1024];
			
			GZIPOutputStream gzos = new GZIPOutputStream(openFileOutput(gzipFile, Context.MODE_PRIVATE));
			
			File sdcard = Environment.getExternalStorageDirectory();

			//Get the text file
			File file = new File(sdcard,"text.txt");
			
			Log.d("TEXT PLAIN SIZE",(Math.round(file.length() * 100.0 / 1024.0) / 100.0)
						+ "kb ");
			
			//FileInputStream in = 	openFileInput("test");
			
			FileInputStream in = new FileInputStream(file);
			
			long timestamp = System.currentTimeMillis();
			int len;
	        while ((len = in.read(buffer)) > 0) {
	        	gzos.write(buffer, 0, len);
	        }
	        
	        Log.d("TIME",""+(System.currentTimeMillis()-timestamp)+" ms");
			
	        in.close();
	    	gzos.finish();
	    	gzos.close();
	    	
	    	
	    	
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void sendRead(View view) {

		try {
			FileInputStream fis = openFileInput("test");
			// FileInputStream instream = new FileInputStream("test");
			// DataInputStream in = new DataInputStream(fis);
			InputStreamReader inputStreamReader = new InputStreamReader(fis);
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			StringBuilder sb = new StringBuilder();
			String line = "";

			for (File f : getFilesDir().listFiles()) {

				sb.append("File " + f.getName() + "  "
						+ (Math.round(f.length() * 100.0 / 1024.0) / 100.0)
						+ "kb ");
			}

			// while ((line = bufferedReader.readLine()) != null) {
			// sb.append(line);
			// }

			line = sb.toString();

			Log.d("BLaBLA", line);
			// Log.d("MainActivity", in.readInt() + "");
			// in.close();
			fis.close();
			// instream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
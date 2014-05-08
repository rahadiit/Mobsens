package com.example.sendtest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class HttpUpload extends AsyncTask<File, Void, Void> {
	private File file;

	public static void postData(byte[] data) {

		URI address = null;

		try {
			address = new URI("http", null,
					"mobilesensing.west.uni-koblenz.de", 8080, "/", "", "");

		} catch (URISyntaxException e1) {
			e1.printStackTrace();
			Log.w("http1", e1.getMessage());
		}

		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(address);

		try {
			HttpEntity entity = new ByteArrayEntity(data);
			httppost.setEntity(entity);
			/* HttpResponse response = */
			httpclient.execute(httppost);
		} catch (Exception e) {
			e.printStackTrace();
			Log.w("http2", e.getMessage());
		}
	}

	@Override
	protected Void doInBackground(File... params) {

		File file = params[0];

		byte[] data = null;
		try {
			data = IOUtils.toByteArray(new InputStreamReader(
					new FileInputStream(file)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (data != null)
			postData(data);
		return null;
	}
}
	package com.example.sendtest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.net.ftp.FTPClient;

import android.os.AsyncTask;
import android.util.Log;

public class FtpUrlUpload extends AsyncTask<File, Void, Void> {

	private static final int BUFFER_SIZE = 4096;

	private File file;

	public void upload() {
		String ftpUrl = "ftp://%s:%s@%s/%s";
		String host = "k-e-h.de";
		String user = "web272f1";
		String pass = "Dzw8R1H3";
//		String host = "192.168.2.106";
//		String user = "MobileSensing";
//		String pass = "MS2014";
		
		String uploadPath = "";

		ftpUrl = String.format(ftpUrl, user, pass, host, uploadPath);
		Log.i("ftp", "Upload URL: " + ftpUrl +file.getName());
		double fileKb=((double)file.length()/1024);
		Log.i("ftp", "fileSize: " +fileKb +"kb");
		long start = new Date().getTime();
		try {
			FTPClient con = new FTPClient();
			con.setBufferSize(1048576);
			
			con.connect(host);
			if (con.login(user, pass)){
				FileInputStream inputStream = new FileInputStream(file);
				boolean result = con.storeFile(file.getName(),inputStream);
				inputStream.close();
				
				if(result){
					double time = ((double)new Date().getTime()-start)/1000;
					double kbS= fileKb/time;
					Log.i("ftp", "upload "+file.getName()+" successful in "+time+"s");
					Log.i("ftp", "thats "+kbS+"kb/s");
				}
				
			}
			try
            {
                con.logout();
                con.disconnect();
            }
            catch (IOException e)
            {
                Log.i("ftp",e.getMessage());
            }
			
//			URL url = new URL(ftpUrl);
//			URLConnection conn = url.openConnection();
//			OutputStream outputStream = conn.getOutputStream();
//			FileInputStream inputStream = new FileInputStream(file);
//
//			byte[] buffer = new byte[BUFFER_SIZE];
//			int bytesRead = -1;
//			while ((bytesRead = inputStream.read(buffer)) != -1) {
//				outputStream.write(buffer, 0, bytesRead);
//			}
//
//			inputStream.close();
//			outputStream.close();
//
//			Log.i("ftp", "File uploaded");
		} catch (Exception ex) {
			Log.i("http ", ex.getMessage());
		}
	}

	@Override
	protected Void doInBackground(File... arg0) {
		file=arg0[0];
		upload();
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result){
		
	}
}
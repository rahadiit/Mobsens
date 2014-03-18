package com.example.ziptest.worker;
/**
 * Provides a runnable Worker with data to process
 */
public class Worker implements Runnable {
	
	// data to be processed by run
	private byte[] data;
	
	/**
	 * Get Data
	 * @return data
	 */
	public byte[] getData() {
		return data;
	}

	/**
	 * Set data
	 * @param data
	 */
	public void setData(byte[] data) {
		this.data = data;
	}

	/**
	 * Process Data
	 */
	@Override
	public void run() {
	}
}

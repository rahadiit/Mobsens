package com.example.sendtest;

public class TestData {

	/**
	 * Compiles 8 bits to a byte
	 * @param arr array of 8 bits
	 * @return byte
	 * @throws IllegalArgumentException
	 */
	private byte bits_to_byte(boolean[] arr) throws IllegalArgumentException {
		// One byte needs exactly 8 bits
		if (arr.length != 8)
			throw new IllegalArgumentException();
		
		// set all bits to 0
		byte result = 0;
		
		// going through all 8 bits
		for (boolean b : arr) {
			// shift
			result <<= 1;
			
			// if bit is set in the array then set in in resulting byte
			if (b)
				result |= 1;
		}
		
		// return the byte
		return result;
	}

	/**
	 * Generate single byte with entropy as a function of random variable p
	 * @param p random variable
	 * @return random byte
	 * @throws IllegalArgumentException
	 */
	private byte get_byte(double p) throws IllegalArgumentException {
		// random variable needs to be between 0 and 1
		if (p < 0 || p > 1)
			throw new IllegalArgumentException();
		
		// array of 8 bits
		boolean[] bits = new boolean[8];
		
		// generate 8 random bits in dependence of p
		for (int i = 0; i <= 7; i++) {
			// compare random variable to random number between 0 and 1
			if (Math.random() > p) {
				// random value is bigger than p
				bits[i] = true;
			} else {
				// random value is smaller or equal to p
				bits[i] = false;
			}
		}
		// convert bit array to byte and return it
		return bits_to_byte(bits);
	}

	/**
	 * Generate array of random bytes with entropy as a function of random variable p
	 * @param size size of resulting array
	 * @param p random variable
	 * @return array of bytes
	 * @throws IllegalArgumentException
	 */
	public byte[] get_bytes(int size, double p) throws IllegalArgumentException {
		// random variable needs to be between 0 and 1
		if (p < 0 || p > 1)
			throw new IllegalArgumentException();
		
		// create array with given size
		byte result[] = new byte[size];
		
		// compute bytes of array
		for (int i = 0; i < size; i++) {
			result[i] = get_byte(p);
		}
		
		// return random array
		return result;
	}
}

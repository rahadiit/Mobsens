package MobileSensors;

import java.util.ArrayList;

import MobileSensors.Helpers.DataWindowBuilder;

class WindowTest {

	public static void main(String[] args) {
		
		ArrayList<Integer> values = new ArrayList<Integer>();
		
		for (int i=0; i < 10; i++) {
			
			values.add(i);
			
		}
		
		System.out.println(values);
		
		ArrayList<ArrayList<Integer>> windows = (new DataWindowBuilder<Integer>()).buildWindows(values, 5, 1);
		
		for (ArrayList<Integer> window : windows) {
			
			System.out.println(window);
			
		}
		

	}

}

package MobileSensors.Helpers;

import java.util.ArrayList;

public class DataWindowBuilder<T> {
	
	public ArrayList<ArrayList<T>> buildWindows (
			ArrayList<T> values,
			int windowWidth,
			int windowDelta) {
		
		ArrayList<ArrayList<T>> result = new ArrayList<ArrayList<T>>();
		
		windowWidth = windowWidth > 1 ? windowWidth : 1;
		windowDelta = windowDelta > 1 ? windowDelta : 1;
		
		int i = 0;
		
		while (i <= values.size() - windowWidth) {
			
			ArrayList<T> window = new ArrayList<T>();
			
			int j = i;
			
			while (j - i < windowWidth) {
				
				window.add(values.get(j));
				
				j++;
				
			}
			
			result.add(window);
			
			i += windowDelta;
			
		}
		
		return result;
		
	}
	
}
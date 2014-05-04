package MobileSensors.Test.Output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;

import MobileSensors.Deprecated.Accelerometer;
import MobileSensors.Deprecated.Annotation;
import MobileSensors.Deprecated.Location;
import MobileSensors.Deprecated.Timeable;

public class CSVFiles {

	public static <T extends Timeable> void splitAndWriteArrayList(
			ArrayList<T> data, ArrayList<Annotation> annotations,
			File eventFolder, Class<T> type) {

		System.out.println("dataS "+data.size());
		
		if (!checkAnnotations(annotations)) {
			System.err.println("corrupt annotations");
		}

		for (ArrayList<T> list : splitAnnotations(data, annotations)) {

			writeToFile(list, eventFolder, type);

		}

	}

	private static boolean checkAnnotations(ArrayList<Annotation> annotations) {

		// false=stop true=start
		boolean state = false;
		boolean result = true;

		for (Annotation annotation : annotations) {
			if (!state && annotation.getTag().toLowerCase().contains("start")) {
				result = false;
				state = true;
			} else if (state
					&& (annotation.getTag().toLowerCase().contains("ende") || annotation
							.getTag().toLowerCase().contains("stop"))) {
				result = true;
				state = false;
			} else {
				result = false;
				break;
			}
		}

		// falls die letzte annotation "start" ist und kein "ende" mehr kommt
		if (state == true)
			return false;
		return result;
	}

	private static <T extends Timeable> ArrayList<ArrayList<T>> splitAnnotations(
			ArrayList<T> data, ArrayList<Annotation> annotations) {

		ArrayList<ArrayList<T>> result = new ArrayList<>();

		if (annotations.size() % 2 == 0) {

			// faengt mit start an, muss also immer nur von 0-1, 2-3, 4-5, usw
			// ausschneiden
			for (int i = 0; i < annotations.size() - 1; i++) {

				ArrayList<T> list = (ArrayList<T>) Timeable.window(data,
						annotations.get(i).getTime(), annotations.get(++i)
								.getTime());

				result.add(list);
			}

		} else {
			throw new IllegalArgumentException(
					"annotation size must be mod2==0");
		}

		int sizes = 0;
		for (ArrayList<T> list : result) {
			sizes += list.size();
		}

		System.out.println("gesamt Messwerte: " + data.size()
				+ ", ausgeschnittene Messwerte: " + sizes);

		return result;
	}

	public static <T extends Timeable> void writeToFile(ArrayList<T> data,
			File folder, Class<T> type) {
		//dodge/record_id/accelerometer.csv
		if (folder.isDirectory()) {

			
			
			if (!data.isEmpty()) {
				File directory = new File(folder.toString() + "/"+"record_"+
						+ data.get(0).getTime());
				
				if(!directory.exists()){
					directory.mkdirs();
				}
				
				String filename="output.csv";
				if (type == Accelerometer.class){
					filename="accelerometer.csv";}
				else if(type == Location.class){
					filename="locaiton.csv";
				}
				
				
				File file = new File(directory.getAbsolutePath().toString()+"/"+filename);
				
				if (!file.exists()) {

					// erstellt neue Datei in einem Ordner mit eindeutigem Namen
					try (BufferedWriter bw = new BufferedWriter(new FileWriter(
							file))) {

						if (type == Accelerometer.class) {
							bw.write("time, x, y, z");
							bw.newLine();

							for (T t : data) {
								Accelerometer accelerometer = (Accelerometer) t;
								bw.write(accelerometer.getTime() + ","
										+ accelerometer.getX() + ","
										+ accelerometer.getY() + ","
										+ accelerometer.getZ());
								bw.newLine();
							}
						}
						
						if (type == Location.class) {
							bw.write("time, speed");
							bw.newLine();

							for (T t : data) {
								Location loc = (Location) t;
								bw.write(loc.getTime() + ","
										+loc.getSpeed());
								bw.newLine();
							}
						}
						

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			throw new IllegalArgumentException();
		}

	}

}

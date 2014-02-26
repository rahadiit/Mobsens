package MobileSensors.Test.Output;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import MobileSensors.Deprecated.Accelerometer;
import MobileSensors.Storage.Events.EventType;
import MobileSensors.Test.Data.SensorE;
import MobileSensors.Test.Input.CSV;

public class ARFFalt {

	public static void readFromDirectory(String inputPath, File outputFile,
			EventType evType, boolean isEvent, SensorE sensor) {

		if (new File(inputPath).isDirectory()) {
			List<File> files = (List<File>) FileUtils.listFiles(new File(
					inputPath), TrueFileFilter.INSTANCE,
					TrueFileFilter.INSTANCE);
			for (File file : files) {
				readFromFile(file, outputFile, evType, isEvent, sensor);
			}
		}

	}

	public static void readFromFile(File filePath, File outputFile,
			EventType evType, boolean isEvent, SensorE sensor) {
		try {
			String inputCSV = FileUtils.readFileToString(filePath);
			writeFromString(inputCSV, outputFile, evType, isEvent);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void writeFromString(String input, File ouputFile,
			EventType evType, boolean isEvent) {

		ArrayList<Accelerometer> accelerometer = CSV.csvToSensor(input,
				Accelerometer.class);

	}

}

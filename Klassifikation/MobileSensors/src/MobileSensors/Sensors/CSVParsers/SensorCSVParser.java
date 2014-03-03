package MobileSensors.Sensors.CSVParsers;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import MobileSensors.Sensors.Sensor;

public interface SensorCSVParser<T extends Sensor> {

	public ArrayList<T> parse(Reader reader) throws IOException;
	
}

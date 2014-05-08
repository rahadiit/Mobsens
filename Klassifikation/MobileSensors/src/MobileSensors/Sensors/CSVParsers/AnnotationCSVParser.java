package MobileSensors.Sensors.CSVParsers;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import MobileSensors.Sensors.Annotation;

public class AnnotationCSVParser implements SensorCSVParser<Annotation> {

	public final static String HEADER_TIME = "time";
	public final static String HEADER_VALUE = "value";
	
	@Override
	public ArrayList<Annotation> parse(Reader reader) throws IOException {
		
		CSVFormat format = CSVFormat.DEFAULT
				.withHeader(
						AnnotationCSVParser.HEADER_TIME,
						AnnotationCSVParser.HEADER_VALUE)
				.withSkipHeaderRecord(true);
		
		
		@SuppressWarnings("resource")
		CSVParser parser = new CSVParser(reader, format);
		
		ArrayList<Annotation> result = new ArrayList<Annotation>();
		
		for (CSVRecord record : parser) {
			
			long time = Long.parseLong(record.get(AnnotationCSVParser.HEADER_TIME));
			String value  = record.get(AnnotationCSVParser.HEADER_VALUE);
			
			result.add(new Annotation(time, value));
			
		}
		
		return result;
		
	}

}
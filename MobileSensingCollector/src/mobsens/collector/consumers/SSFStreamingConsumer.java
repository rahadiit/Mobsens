package mobsens.collector.consumers;

import mobsens.collector.drivers.annotations.AnnotationOutput;
import mobsens.collector.drivers.connectivity.ConnectivityOutput;
import mobsens.collector.drivers.locations.LocationOutput;
import mobsens.collector.drivers.sensors.SensorOutput;

import org.json.JSONObject;

import android.content.ContextWrapper;
import android.hardware.Sensor;

public class SSFStreamingConsumer extends CSVStreamingConsumer<Object>
{
	public final String identifier;

	public SSFStreamingConsumer(ContextWrapper contextWrapper, String identifier)
	{
		super(contextWrapper, ",", System.getProperty("line.separator"));

		this.identifier = identifier;
	}

	@Override
	protected void write(Object item)
	{
		if (item instanceof AnnotationOutput)
		{
			final AnnotationOutput annotationOutput = (AnnotationOutput) item;

			rowStart();
			field("TAG");
			field(annotationOutput.time.getTime());
			field(identifier);
			field(JSONObject.quote(annotationOutput.value));
			rowEnd();

			// return new Object[] { "TAG", annotationOutput.time.getTime(),
			// deviceId, escapeString(annotationOutput.value) };
		}
		else if (item instanceof ConnectivityOutput)
		{
			// final ConnectivityOutput connectivityOutput =
			// (ConnectivityOutput) item;

			// TODO Implement
			// return null;
		}
		else if (item instanceof LocationOutput)
		{
			final LocationOutput locationOutput = (LocationOutput) item;

			rowStart();
			field("GPS");
			field(locationOutput.time.getTime());
			field(identifier);
			field(locationOutput.latitude + " " + locationOutput.longitude + (locationOutput.altitude == null ? " 0.0" : " " + locationOutput.altitude));
			rowEnd();
		}
		else if (item instanceof SensorOutput)
		{
			final SensorOutput sensorOutput = (SensorOutput) item;

			rowStart();
			switch (sensorOutput.sensor)
			{
			case Sensor.TYPE_ACCELEROMETER:
				field("ACC");
				break;

			case Sensor.TYPE_GYROSCOPE:
				field("GYR");
				break;

			case Sensor.TYPE_MAGNETIC_FIELD:
				field("MAG");
				break;

			case Sensor.TYPE_LINEAR_ACCELERATION:
				field("LAC");
				break;

			case Sensor.TYPE_GRAVITY:
				field("GRA");
				break;
			}

			field(sensorOutput.time.getTime());
			field(identifier);
			field(sensorOutput.values[0]);
			field(sensorOutput.values[1]);
			field(sensorOutput.values[2]);
			rowEnd();
		}
	}
}

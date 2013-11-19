package mobsens.collector.consumers;

import org.json.JSONObject;

import mobsens.collector.drivers.annotations.AnnotationOutput;
import mobsens.collector.drivers.connectivity.ConnectivityOutput;
import mobsens.collector.drivers.locations.LocationOutput;
import mobsens.collector.drivers.sensors.SensorOutput;
import android.content.ContextWrapper;
import android.hardware.Sensor;
import android.util.Log;

public class SSFStreamingConsumer extends CSVStreamingConsumer<Object>
{
	public final String deviceId;

	public SSFStreamingConsumer(ContextWrapper contextWrapper, String folder, String deviceId)
	{
		super(contextWrapper, folder, ',');

		this.deviceId = deviceId;
	}

	protected String escapeString(String s)
	{
		// TODO: Vielleicht nicht so ganz korrekt
		return JSONObject.quote(s);
	}

	@Override
	protected Object[] fieldsOf(Object item)
	{
		if (item instanceof AnnotationOutput)
		{
			final AnnotationOutput annotationOutput = (AnnotationOutput) item;

			return new Object[] { "TAG", annotationOutput.time.getTime(), deviceId, escapeString(annotationOutput.value) };
		}
		else if (item instanceof ConnectivityOutput)
		{
			// final ConnectivityOutput connectivityOutput =
			// (ConnectivityOutput) item;

			// TODO Implement
			return null;
		}
		else if (item instanceof LocationOutput)
		{
			final LocationOutput locationOutput = (LocationOutput) item;

			return new Object[] {
					"GPS",
					locationOutput.time.getTime(),
					deviceId,
					Double.toString(locationOutput.latitude) + " " + Double.toString(locationOutput.longitude) + " "
							+ (locationOutput.altitude != null ? Double.toString(locationOutput.altitude) : "0.0") };

		}
		else if (item instanceof SensorOutput)
		{
			final SensorOutput sensorOutput = (SensorOutput) item;

			final String sid;
			switch (sensorOutput.sensor)
			{
			case Sensor.TYPE_ACCELEROMETER:
				sid = "ACC";
				break;

			case Sensor.TYPE_GYROSCOPE:
				sid = "GYR";
				break;

			case Sensor.TYPE_MAGNETIC_FIELD:
				sid = "MAG";
				break;

			case Sensor.TYPE_LINEAR_ACCELERATION:
				sid = "LAC";
				break;

			case Sensor.TYPE_GRAVITY:
				sid = "GRA";
				break;

			default:
				return null;
			}

			return new Object[] { sid, sensorOutput.time.getTime(), deviceId,
					Double.toString(sensorOutput.values[0]) + " " + Double.toString(sensorOutput.values[1]) + " " + Double.toString(sensorOutput.values[2]) };
		}
		else
		{
			Log.d("SSF Streaming", "Unhandled item type of " + item);

			return null;
		}
	}
}

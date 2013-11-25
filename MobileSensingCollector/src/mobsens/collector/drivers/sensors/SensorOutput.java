package mobsens.collector.drivers.sensors;

import java.util.Arrays;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONStringer;

import android.hardware.Sensor;
import mobsens.collector.wfj.basics.BasicWFJ;

/**
 * Verkapselung der Sensordaten
 * 
 * @author Pizza
 * 
 */
public class SensorOutput extends BasicWFJ
{
	/**
	 * Zeit
	 */
	public final Date time;

	/**
	 * Genauigkeit
	 */
	public final int accuracy;

	/**
	 * Quellsensor, Werte korrespondieren zu den in {@link Sensor} definierten Types
	 */
	public final int sensor;

	/**
	 * Werte
	 */
	public final float[] values;

	public SensorOutput(Date time, int accuracy, int sensor, float[] values)
	{
		this.time = time;
		this.accuracy = accuracy;
		this.sensor = sensor;
		this.values = values;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + accuracy;
		result = prime * result + sensor;
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + Arrays.hashCode(values);
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		SensorOutput other = (SensorOutput) obj;
		if (accuracy != other.accuracy) return false;
		if (sensor != other.sensor) return false;
		if (time == null)
		{
			if (other.time != null) return false;
		}
		else if (!time.equals(other.time)) return false;
		if (!Arrays.equals(values, other.values)) return false;
		return true;
	}

	@Override
	protected void generateTo(JSONStringer jsonStringer) throws JSONException
	{
		jsonStringer.object();
		jsonStringer.key("sensor");
		jsonStringer.object();

		jsonStringer.key("time");
		jsonStringer.value(time.getTime());

		jsonStringer.key("sensor");
		jsonStringer.value(sensor);

		jsonStringer.key("values");
		jsonStringer.array();
		for (float value : values)
		{
			jsonStringer.value(value);
		}
		jsonStringer.endArray();

		jsonStringer.endObject();
		jsonStringer.endObject();
	}

	@Override
	public String toString()
	{
		return "SensorOutput [time=" + time + ", accuracy=" + accuracy + ", sensor=" + sensor + ", values=" + Arrays.toString(values) + "]";
	}
}

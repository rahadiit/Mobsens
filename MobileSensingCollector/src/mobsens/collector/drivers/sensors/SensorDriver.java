package mobsens.collector.drivers.sensors;

import java.util.Date;

import mobsens.collector.pipeline.BasicMultiGenerator;
import mobsens.collector.pipeline.Driver;
import android.content.Context;
import android.content.ContextWrapper;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class SensorDriver extends BasicMultiGenerator<SensorOutput> implements Driver
{
	public final SensorEventListener SENSOR_EVENT_ENDPOINT = new SensorEventListener()
	{
		@Override
		public void onSensorChanged(SensorEvent event)
		{
			if (hasConsumers())
			{
				// Felder erstellen
				final Date time = new Date(System.currentTimeMillis() + (event.timestamp - System.nanoTime()) / 1000000L);
				final int accuracy = event.accuracy;
				final float[] values = event.values;

				// Item erstellen
				final SensorOutput item = new SensorOutput(time, accuracy, event.sensor.getType(), values);

				// Item senden
				offer(item);
			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy)
		{

		}
	};

	/**
	 * Context, der die Services enthält
	 */
	public final ContextWrapper contextWrapper;

	/**
	 * Typ des Sensors
	 */
	public final int type;

	/**
	 * Abtastrate des Sensors
	 */
	public final int rate;

	public SensorDriver(ContextWrapper contextWrapper, int type, int rate)
	{
		this.contextWrapper = contextWrapper;
		this.type = type;
		this.rate = rate;
	}

	@Override
	public void start()
	{
		SensorManager sm = (SensorManager) contextWrapper.getSystemService(Context.SENSOR_SERVICE);

		sm.registerListener(SENSOR_EVENT_ENDPOINT, sm.getDefaultSensor(type), rate);
	}

	@Override
	public void stop()
	{
		SensorManager sm = (SensorManager) contextWrapper.getSystemService(Context.SENSOR_SERVICE);

		sm.unregisterListener(SENSOR_EVENT_ENDPOINT);
	}

}

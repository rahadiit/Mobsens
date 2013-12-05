package mobsens.collector.metrication;

import java.util.HashSet;
import java.util.Set;

public abstract class BasicMeter implements Meter
{
	private final Set<MeterListener> listeners;

	public BasicMeter()
	{
		listeners = new HashSet<MeterListener>();
	}

	@Override
	public boolean addMeterableListener(MeterListener listener)
	{
		return listeners.add(listener);
	}

	@Override
	public boolean removeMeterableListener(MeterListener listener)
	{
		return listeners.remove(listener);
	}

	public void updated(int... fields)
	{
		for (MeterListener listener : listeners)
		{
			listener.receiveUpdate(this, fields);
		}
	}
}

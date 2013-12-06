package mobsens.collector.util;

import java.util.Iterator;

import mobsens.collector.metrication.Meter;

public class Meters
{
	public static Iterable<Integer> fields(final Meter meter)
	{
		return new Iterable<Integer>()
		{
			@Override
			public Iterator<Integer> iterator()
			{
				return new Iterator<Integer>()
				{
					private int pcc = Meter.FIELD_INIT;

					private int pcn = meter.getNextField(pcc);

					@Override
					public void remove()
					{
						throw new UnsupportedOperationException();
					}

					@Override
					public Integer next()
					{
						// Position verschieben und nächsten Wert berechnen
						pcc = pcn;
						pcn = meter.getNextField(pcc);

						return pcc;
					}

					@Override
					public boolean hasNext()
					{
						return pcn != Meter.FIELD_NONE;
					}
				};
			}
		};
	}

}

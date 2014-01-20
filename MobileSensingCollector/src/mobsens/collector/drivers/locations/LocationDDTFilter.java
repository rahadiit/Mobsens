package mobsens.collector.drivers.locations;

import java.util.Date;

import mobsens.collector.pipeline.BasicGenerator;
import mobsens.collector.pipeline.Consumer;
import mobsens.collector.util.Calculations;

public class LocationDDTFilter extends BasicGenerator<LocationOutput> implements Consumer<LocationOutput>
{
	private Date lastTime;

	private double lastLon;

	private double lastLat;

	private final double maxDDT;

	public LocationDDTFilter(double maxDDT)
	{
		this.maxDDT = maxDDT;
	}

	@Override
	public void consume(LocationOutput item)
	{
		if (!hasConsumer()) return;

		try
		{
			if (lastTime != null)
			{
				final double dt = (item.time.getTime() - lastTime.getTime()) / 1000.0;
				final double dx = Calculations.haversineDistance(lastLon, lastLat, item.longitude, item.latitude);

				if (Math.abs(dx / dt) <= maxDDT)
				{
					offer(item);

				}
			}
		}
		finally
		{
			lastTime = item.time;
			lastLon = item.longitude;
			lastLat = item.latitude;
		}
	}
}

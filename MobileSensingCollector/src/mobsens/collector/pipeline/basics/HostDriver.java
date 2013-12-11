package mobsens.collector.pipeline.basics;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import mobsens.collector.pipeline.Driver;

public class HostDriver implements Driver
{
	private final Set<Driver> drivers;

	public HostDriver()
	{
		drivers = new HashSet<Driver>();
	}

	public boolean hasDrivers()
	{
		return !drivers.isEmpty();
	}

	public Iterable<Driver> getDrivers()
	{
		return Collections.unmodifiableSet(drivers);
	}

	public void addDriver(Driver driver)
	{
		drivers.add(driver);
	}

	public void removeDriver(Driver driver)
	{
		drivers.remove(driver);
	}

	@Override
	public void start()
	{
		for (Driver driver : drivers)
		{
			driver.start();
		}
	}

	@Override
	public void stop()
	{
		for (Driver driver : drivers)
		{
			driver.stop();
		}
	}

}

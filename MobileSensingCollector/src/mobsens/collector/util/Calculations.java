package mobsens.collector.util;

public class Calculations
{
	public static final int msFromFrequency(double frequency)
	{
		if (frequency == 0.0) return Integer.MAX_VALUE;

		return (int) Math.round(1000.0 / frequency);
	}
}

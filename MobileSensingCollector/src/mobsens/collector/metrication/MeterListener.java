package mobsens.collector.metrication;

public interface MeterListener
{
	public void receiveUpdate(Meter meterable, int[] fields);
}

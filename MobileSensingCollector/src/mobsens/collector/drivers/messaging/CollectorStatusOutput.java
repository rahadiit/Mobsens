package mobsens.collector.drivers.messaging;

public class CollectorStatusOutput
{
	public final boolean status;

	public CollectorStatusOutput(boolean status)
	{
		this.status = status;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (status ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		CollectorStatusOutput other = (CollectorStatusOutput) obj;
		if (status != other.status) return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "CollectorStatusOutput [status=" + status + "]";
	}
}

package mobsens.collector.drivers.messaging;

public class CollectionCompleteOutput
{
	public final String location;

	public CollectionCompleteOutput(String location)
	{
		this.location = location;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		CollectionCompleteOutput other = (CollectionCompleteOutput) obj;
		if (location == null)
		{
			if (other.location != null) return false;
		}
		else if (!location.equals(other.location)) return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "CollectionCompleteOutput [location=" + location + "]";
	}
	
	
}

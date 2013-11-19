package mobsens.collector.drivers.annotations;

import java.util.Date;

public class AnnotationOutput
{
	/**
	 * Zeit
	 */
	public final Date time;

	/**
	 * Inhalt
	 */
	public final String value;

	public AnnotationOutput(Date time, String value)
	{
		this.time = time;
		this.value = value;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		AnnotationOutput other = (AnnotationOutput) obj;
		if (time == null)
		{
			if (other.time != null) return false;
		}
		else if (!time.equals(other.time)) return false;
		if (value == null)
		{
			if (other.value != null) return false;
		}
		else if (!value.equals(other.value)) return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "AnnotationOutput [time=" + time + ", value=" + value + "]";
	}
}

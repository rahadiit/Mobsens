package mobsens.collector.drivers.messaging;

import java.util.Date;

public class LogOutput
{
	/**
	 * Zeit
	 */
	public final Date time;

	/**
	 * Titel
	 */
	public final String title;

	/**
	 * Untertitel
	 */
	public final String subtitle;

	/**
	 * BeschreibungF
	 */
	public final String description;

	public LogOutput(Date time, String title, String subtitle, String description)
	{
		this.time = time;
		this.title = title;
		this.subtitle = subtitle;
		this.description = description;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((subtitle == null) ? 0 : subtitle.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		LogOutput other = (LogOutput) obj;
		if (description == null)
		{
			if (other.description != null) return false;
		}
		else if (!description.equals(other.description)) return false;
		if (subtitle == null)
		{
			if (other.subtitle != null) return false;
		}
		else if (!subtitle.equals(other.subtitle)) return false;
		if (time == null)
		{
			if (other.time != null) return false;
		}
		else if (!time.equals(other.time)) return false;
		if (title == null)
		{
			if (other.title != null) return false;
		}
		else if (!title.equals(other.title)) return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "LogOutput [time=" + time + ", title=" + title + ", subtitle=" + subtitle + ", description=" + description + "]";
	}

}

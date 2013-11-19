package mobsens.collector.drivers.messaging;

import java.util.Date;

public class UploadResponseOutput
{
	public final String handle;

	public final Date startTime;

	public final Date endTime;

	public final long transmitted;

	public final String response;

	public final Throwable exception;

	public UploadResponseOutput(String handle, Date startTime, Date endTime, long transmitted, String response, Throwable exception)
	{
		this.handle = handle;
		this.startTime = startTime;
		this.endTime = endTime;
		this.transmitted = transmitted;
		this.response = response;
		this.exception = exception;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((exception == null) ? 0 : exception.hashCode());
		result = prime * result + ((handle == null) ? 0 : handle.hashCode());
		result = prime * result + ((response == null) ? 0 : response.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + (int) (transmitted ^ (transmitted >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		UploadResponseOutput other = (UploadResponseOutput) obj;
		if (endTime == null)
		{
			if (other.endTime != null) return false;
		}
		else if (!endTime.equals(other.endTime)) return false;
		if (exception == null)
		{
			if (other.exception != null) return false;
		}
		else if (!exception.equals(other.exception)) return false;
		if (handle == null)
		{
			if (other.handle != null) return false;
		}
		else if (!handle.equals(other.handle)) return false;
		if (response == null)
		{
			if (other.response != null) return false;
		}
		else if (!response.equals(other.response)) return false;
		if (startTime == null)
		{
			if (other.startTime != null) return false;
		}
		else if (!startTime.equals(other.startTime)) return false;
		if (transmitted != other.transmitted) return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "UploadResponseOutput [handle=" + handle + ", startTime=" + startTime + ", endTime=" + endTime + ", transmitted=" + transmitted + ", response=" + response + ", exception="
				+ exception + "]";
	}
}

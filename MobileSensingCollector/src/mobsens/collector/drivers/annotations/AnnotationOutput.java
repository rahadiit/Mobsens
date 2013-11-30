package mobsens.collector.drivers.annotations;

import java.util.Date;

import mobsens.collector.wfj.basics.BasicWFJ;

import org.json.JSONException;
import org.json.JSONStringer;

public class AnnotationOutput extends BasicWFJ
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
	protected void generateTo(JSONStringer jsonStringer) throws JSONException
	{
		jsonStringer.object();
		jsonStringer.key("annotation");
		jsonStringer.object();

		jsonStringer.key("time");
		jsonStringer.value(time.getTime());

		jsonStringer.key("value");
		jsonStringer.value(value);

		jsonStringer.endObject();
		jsonStringer.endObject();
	}
}

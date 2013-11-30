package mobsens.collector.wfj.basics;

import mobsens.collector.wfj.WFJ;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public abstract class BasicWFJ implements WFJ
{
	public static enum ErrorStrategy
	{
		RUNTIME_EXCEPTION, NULL
	}

	public final ErrorStrategy errorStrategy;

	public BasicWFJ(ErrorStrategy errorStrategy)
	{
		this.errorStrategy = errorStrategy;
	}

	public BasicWFJ()
	{
		this(ErrorStrategy.NULL);
	}

	@Override
	public String generate()
	{
		final JSONStringer jsonStringer = new JSONStringer();

		try
		{
			generateTo(jsonStringer);

			return jsonStringer.toString();
		}
		catch (JSONException e)
		{
			switch (errorStrategy)
			{
			case NULL:
				return null;
			case RUNTIME_EXCEPTION:
				throw new RuntimeException(e);
			default:
				throw new IllegalStateException("Invalid error strategy");
			}
		}
	}

	protected abstract void generateTo(JSONStringer jsonStringer) throws JSONException;

	@Override
	public String toString()
	{
		JSONStringer stringer = new JSONStringer();

		try
		{
			generateTo(stringer);
		}
		catch (JSONException e)
		{
			return "{\"error\":" + JSONObject.quote(e.getMessage()) + "}";
		}

		return stringer.toString();
	}
}

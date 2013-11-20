package mobsens.collector.wfj.basics;

import org.json.JSONException;
import org.json.JSONStringer;

import mobsens.collector.wfj.WFJ;

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
}

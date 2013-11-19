package mobsens.collector.consumers;

import java.io.PrintStream;

import android.content.ContextWrapper;

public abstract class CSVStreamingConsumer<Item> extends PrintStreamingConsumer<Item>
{
	public final String fieldSeparator;

	public final String rowSeparator;

	private PrintStream printStream;

	private boolean freshRow;

	public CSVStreamingConsumer(ContextWrapper contextWrapper, String fieldSeparator, String rowSeparator)
	{
		super(contextWrapper);

		this.fieldSeparator = fieldSeparator;
		this.rowSeparator = rowSeparator;

		printStream = null;
		freshRow = true;
	}

	@Override
	protected void redirectPrint(PrintStream printStream)
	{
		this.printStream = printStream;
	}

	protected final void rowStart()
	{
		assert freshRow : "nonfinalized row";
	
		freshRow = true;
	}

	protected final void field(boolean value)
	{
		if (!freshRow)
		{
			printStream.print(fieldSeparator);
		}

		printStream.print(value);

		freshRow = false;
	}

	protected final void field(char value)
	{
		if (!freshRow)
		{
			printStream.print(fieldSeparator);
		}

		printStream.print(value);

		freshRow = false;
	}

	protected final void field(char[] value)
	{
		if (!freshRow)
		{
			printStream.print(fieldSeparator);
		}

		printStream.print(value);

		freshRow = false;
	}

	protected final void field(double value)
	{
		if (!freshRow)
		{
			printStream.print(fieldSeparator);
		}

		printStream.print(value);

		freshRow = false;
	}

	protected final void field(float value)
	{
		if (!freshRow)
		{
			printStream.print(fieldSeparator);
		}

		printStream.print(value);

		freshRow = false;
	}

	protected final void field(int value)
	{
		if (!freshRow)
		{
			printStream.print(fieldSeparator);
		}

		printStream.print(value);

		freshRow = false;
	}

	protected final void field(long value)
	{
		if (!freshRow)
		{
			printStream.print(fieldSeparator);
		}

		printStream.print(value);

		freshRow = false;
	}

	protected final void field(Object value)
	{
		if (!freshRow)
		{
			printStream.print(fieldSeparator);
		}

		printStream.print(value);

		freshRow = false;
	}

	protected final void field(String value)
	{
		if (!freshRow)
		{
			printStream.print(fieldSeparator);
		}

		printStream.print(value);

		freshRow = false;
	}

	protected final void rowEnd()
	{
		printStream.print(rowSeparator);
		freshRow = true;
	}

}

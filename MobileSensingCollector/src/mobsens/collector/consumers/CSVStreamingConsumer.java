package mobsens.collector.consumers;

import java.io.PrintStream;

import android.content.ContextWrapper;

public class CSVStreamingConsumer<Item> extends FileStreamingConsumer<Item>
{
	public final char separator;

	public CSVStreamingConsumer(ContextWrapper contextWrapper, String folder, char separator)
	{
		super(contextWrapper, folder);

		this.separator = separator;
	}

	@Override
	protected void printOneItem(PrintStream printStream, Item item)
	{
		final Object[] fieldsOf = fieldsOf(item);

		if (fieldsOf != null)
		{
			boolean separate = false;
			for (Object field : fieldsOf)
			{
				if (separate)
				{
					printStream.print(separator);
				}

				printStream.print(field);

				separate = true;
			}

			printStream.println();
		}
	}

	protected Object[] fieldsOf(Item item)
	{
		return new Object[0];
	}

}

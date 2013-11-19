package mobsens.collector.consumers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import android.content.ContextWrapper;
import mobsens.collector.pipeline.Consumer;

public class FileStreamingConsumer<Item> implements Consumer<Item>
{
	public final ContextWrapper contextWrapper;

	public final String folder;

	public FileStreamingConsumer(ContextWrapper contextWrapper, String folder)
	{
		this.contextWrapper = contextWrapper;
		this.folder = folder;
	}

	private String location;

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public File[] streamed()
	{
		return new File(contextWrapper.getFilesDir(), folder).listFiles();
	}

	@Override
	public void consume(Item item)
	{
		try
		{
			final File file = new File(contextWrapper.getFilesDir(), folder + "/" + location);

			final FileOutputStream fileOutputStream = new FileOutputStream(file, true);
			final PrintStream printStream = new PrintStream(fileOutputStream);

			printOneItem(printStream, item);

			printStream.close();
			fileOutputStream.close();
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	protected void printOneItem(PrintStream printStream, Item item)
	{
		printStream.println(item);
	}
}

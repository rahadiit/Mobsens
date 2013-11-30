package mobsens.collector.consumers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import mobsens.collector.pipeline.Consumer;
import android.content.ContextWrapper;

public abstract class FileStreamingConsumer<Item> implements Consumer<Item>
{
	public final ContextWrapper contextWrapper;

	public FileStreamingConsumer(ContextWrapper contextWrapper)
	{
		this.contextWrapper = contextWrapper;
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

	@Override
	public void consume(Item item)
	{
		try
		{
			final File file = new File(contextWrapper.getFilesDir(), location);

			// Parent erstellen
			file.getParentFile().mkdirs();

			final FileOutputStream fileOutputStream = new FileOutputStream(file, true);
			redirect(fileOutputStream);

			if (file.length() == 0)
			{
				init(item);
			}

			write(item);

			fileOutputStream.close();
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	protected abstract void redirect(OutputStream stream);

	/**
	 * Wird aufgerufen, wenn die Datei vor dem Schreiben des angegebenen Items
	 * leer war
	 * 
	 * @param itemAfter
	 *            Das folgende Item
	 */
	protected void init(Item itemAfter)
	{
		// Nichts tun
	}

	protected abstract void write(Item item);
}

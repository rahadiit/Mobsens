package mobsens.collector.consumers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import mobsens.collector.pipeline.Consumer;
import mobsens.collector.pipeline.Driver;
import android.content.ContextWrapper;

public abstract class FileStreamingConsumer<Item> implements Consumer<Item>, Driver
{
	public final ContextWrapper contextWrapper;

	private FileOutputStream fileOutputStream;

	public FileStreamingConsumer(ContextWrapper contextWrapper)
	{
		this.contextWrapper = contextWrapper;

		fileOutputStream = null;
	}

	private String location;

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		if (fileOutputStream != null)
		{
			stop();

			this.location = location;

			start();
		}
		else
		{
			this.location = location;
		}
	}

	protected abstract void redirect(FileOutputStream fileOutputStream);

	@Override
	public void stop()
	{
		if (fileOutputStream == null) throw new IllegalStateException("Trying to stop a stopped receiver");

		try
		{
			fileOutputStream.close();

			fileOutputStream = null;
		}
		catch (IOException e)
		{
			throw new RuntimeException("Error stopping receiver", e);
		}
	}

	@Override
	public void start()
	{
		if (fileOutputStream != null) throw new IllegalStateException("Trying to start a running receiver");

		try
		{
			// File holen
			final File file = new File(contextWrapper.getFilesDir(), location);

			// Ordner, der die File enthählt, erstellen
			file.getParentFile().mkdirs();

			// Append-Öffnen
			fileOutputStream = new FileOutputStream(file, true);

			redirect(fileOutputStream);
		}
		catch (FileNotFoundException e)
		{
			throw new RuntimeException("Error starting receiver", e);
		}
	}
}

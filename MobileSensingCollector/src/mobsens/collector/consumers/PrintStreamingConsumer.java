package mobsens.collector.consumers;

import java.io.FileOutputStream;
import java.io.PrintStream;

import android.content.ContextWrapper;

public abstract class PrintStreamingConsumer<Item> extends FileStreamingConsumer<Item>
{
	public PrintStreamingConsumer(ContextWrapper contextWrapper)
	{
		super(contextWrapper);
	}

	@Override
	protected void redirect(FileOutputStream fileOutputStream)
	{
		redirect(new PrintStream(fileOutputStream));
	}

	protected abstract void redirect(PrintStream printStream);

}

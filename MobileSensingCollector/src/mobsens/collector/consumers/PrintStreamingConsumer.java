package mobsens.collector.consumers;

import java.io.OutputStream;
import java.io.PrintStream;

import android.content.ContextWrapper;

public abstract class PrintStreamingConsumer<Item> extends FileStreamingConsumer<Item>
{
	public PrintStreamingConsumer(ContextWrapper contextWrapper)
	{
		super(contextWrapper);
	}

	@Override
	protected void redirect(OutputStream stream)
	{
		redirectPrint(new PrintStream(stream));
	}

	protected abstract void redirectPrint(PrintStream printStream);

}

package mobsens.collector.consumers;

import java.io.PrintStream;

import mobsens.collector.wfj.WFJ;
import android.content.ContextWrapper;

public class WFJStreamingConsumer extends PrintStreamingConsumer<WFJ>
{
	private PrintStream printStream;

	public WFJStreamingConsumer(ContextWrapper contextWrapper)
	{
		super(contextWrapper);

		printStream = null;
	}

	@Override
	protected void redirect(PrintStream printStream)
	{
		this.printStream = printStream;
	}

	@Override
	public void consume(WFJ item)
	{
		printStream.println(item.generate());
	}
}

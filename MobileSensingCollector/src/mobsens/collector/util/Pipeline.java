package mobsens.collector.util;

import mobsens.collector.pipeline.Consumer;
import mobsens.collector.pipeline.Generator;

public class Pipeline
{
	public static <Prefix, Infix, T extends Consumer<Prefix> & Generator<Infix>> Consumer<Prefix> with(T prefix, Consumer<? super Infix> c)
	{
		prefix.setConsumer(c);
		
		return prefix;
	}
}
